package com.softron.masterdata.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.admin.service.AdminService;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.dto.ClientDto;
import com.softron.masterdata.repository.ClientRepository;
import com.softron.quality.dto.GraphDataDto;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.schema.admin.repository.UserRepository;
import com.softron.utils.LocalDateTimeConversion;
import com.softron.utils.SentezIntegration;

@Service
public class ClientService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AdminService adminService;

	@Autowired
	OrganizationRepository organizationRepository;

	@Value("${spring.datasource2.jdbcUrl}")
	private String jdbcUrl;

	@Value("${spring.datasource2.username}")
	private String username;

	@Value("${spring.datasource2.password}")
	private String password;

	String root = "Client";

	public Response create(ClientDto clientDto, Long orgId) {
		try {
			Client client = modelMapper.map(clientDto, Client.class);
			Optional<UserEntity> userEntity = userRepository.findById(clientDto.getUser().getUserName());
			client.setUser(userEntity.get());
			Optional<Organization> organaization = organizationRepository.findById(orgId);
			client.setOrganization(organaization.get());
			clientRepository.save(client);

			BeanUtils.copyProperties(client, clientDto);

			// clientDto = modelMapper.map(client, ClientDto.class);

			return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, clientDto,
					String.format("%s created successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll(Long orgId) {
		try {
			List<Organization> orgList = new ArrayList<Organization>();
			List<Organization> organizationDetail = organizationRepository.findAllById(orgId);
			orgList.addAll(organizationDetail);

			adminService.getOrganizationList(orgId, orgList);

			List<String> orgNames = new ArrayList<String>();
			List<Long> orgIds = new ArrayList<Long>();

			for (Organization org : orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}

			List<Client> clients = clientRepository.findAllByActiveTrueAndOrganizationIdIn(orgIds);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(clients), clients.size(),
					String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			Client client = clientRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			// ClientDto clientDto = modelMapper.map(client, ClientDto.class);
			ClientDto clientDto = new ClientDto();
			modelMapper.map(client, clientDto);
			// BeanUtils.copyProperties(client, clientDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, clientDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getClientsTime() {
		try {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			System.out.println("*********************************************************");
			System.out.println(dtf.format(now));
			System.out.println("*********************************************************");
			
			String time = LocalDateTimeConversion.getStringFromLocalDateTime(now, "yyyy-MM-dd'T'HH:mm:ss");

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, time,
					String.format("%s current time retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getClientDashBoard(String startDate, String endDate) {
		try {

			GraphDataDto clientDashBoardInfo = SentezIntegration.api4(jdbcUrl, username, password, startDate, endDate);

			String qualityInfoSql = "SELECT t_total.Total_check,t_pass.QC_pass,t_defect.Defect_qty\r\n" + "FROM\r\n"
					+ "(\r\n" + "	SELECT COUNT(*) AS Total_check\r\n" + "	FROM quality_transaction\r\n"
					+ "	WHERE created_at BETWEEN :startDate AND :endDate\r\n" + ") AS t_total\r\n" + "LEFT JOIN \r\n"
					+ "(\r\n" + "	SELECT COUNT(*) AS QC_pass\r\n" + "	FROM quality_transaction\r\n"
					+ "	WHERE  check_output='ok' and created_at BETWEEN :startDate AND :endDate\r\n"
					+ ") AS t_pass ON 1=1\r\n" + "LEFT JOIN \r\n" + "(\r\n" + "	SELECT COUNT(*) AS Defect_qty\r\n"
					+ "	FROM quality_transaction, quality_defect\r\n"
					+ "	WHERE quality_defect.qualitytransaction_id = quality_transaction.id and quality_transaction.created_at BETWEEN :startDate AND :endDate\r\n"
					+ "\r\n" + ") AS t_defect ON 1=1";

			Query qualityInfoSqlResult = entityManager.createNativeQuery(qualityInfoSql);
			qualityInfoSqlResult.setParameter("startDate", startDate);
			qualityInfoSqlResult.setParameter("endDate", endDate);

			Object[] result = (Object[]) qualityInfoSqlResult.getSingleResult();

			clientDashBoardInfo.setTotalcheck(result[0] == null ? "0" : result[0].toString());
			clientDashBoardInfo.setQcPass(result[1] == null ? "0" : result[1].toString());
			clientDashBoardInfo.setDefectQuantity(result[2] == null ? "0" : result[2].toString());

			Double Dhu = 0.0;
			if (result[0] != null && result[2] != null) {
				if (Double.parseDouble(result[0].toString()) > 0) {
					Dhu = (Double.parseDouble(result[2].toString())) / (Double.parseDouble(result[0].toString()));
					Dhu = Double.parseDouble(new DecimalFormat(".##").format(Dhu));
				}
			}

			clientDashBoardInfo.setDhu(Dhu.toString());

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, clientDashBoardInfo,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getClientChart(String startDate, String endDate) {
		try {

			List<GraphDataDto> clientChartInfoList = SentezIntegration.api5(jdbcUrl, username, password, startDate,
					endDate);

			String qualityInfoSql = "SELECT date_format( created_at, '%Y-%m' ) AS Q_date , COUNT(*) AS QC_pass\r\n"
					+ "FROM quality_transaction\r\n"
					+ "WHERE  check_output='ok' and created_at BETWEEN :startDate AND :endDate\r\n" + "GROUP BY Q_date";

			Query qualityInfoSqlResult = entityManager.createNativeQuery(qualityInfoSql);
			qualityInfoSqlResult.setParameter("startDate", startDate);
			qualityInfoSqlResult.setParameter("endDate", endDate);

			List<Object[]> getQualityInfoobjects = qualityInfoSqlResult.getResultList();

			for (Object[] getQualityInfo : getQualityInfoobjects) {
				GraphDataDto getQualityInfoRowResult = new GraphDataDto();
				getQualityInfoRowResult.setTime(getQualityInfo[0] == null ? "0" : getQualityInfo[0].toString());
				getQualityInfoRowResult.setType("QC Pass");
				getQualityInfoRowResult.setValue(getQualityInfo[1] == null ? "0" : getQualityInfo[1].toString());

				clientChartInfoList.add(getQualityInfoRowResult);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, clientChartInfoList,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response delete(Long id) {
		try {
			Client client = clientRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			client.setActive(false);
			clientRepository.save(client);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response update(Long id, ClientDto clientDto) {
		try {
			Client client = clientRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			modelMapper.map(clientDto, client);
			clientRepository.save(client);

			modelMapper.map(client, clientDto);
			// BeanUtils.copyProperties(client, clientDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, clientDto,
					String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	private List<ClientDto> getResponseDtoList(List<Client> clients) {
		List<ClientDto> responseDtoList = new ArrayList<>();

		clients.forEach(client -> {
			ClientDto clientDto = new ClientDto();
			modelMapper.map(client, clientDto);
			// BeanUtils.copyProperties(client, clientDto);
			responseDtoList.add(clientDto);
		});
		return responseDtoList;
	}

}

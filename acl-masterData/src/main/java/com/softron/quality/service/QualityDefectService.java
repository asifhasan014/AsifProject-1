package com.softron.quality.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.admin.service.AdminService;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.dto.CompanyDto;
import com.softron.masterdata.repository.DefectRepository;
import com.softron.masterdata.repository.OperationRepository;
import com.softron.quality.dto.GraphDataDto;
import com.softron.quality.dto.QualityDefectDto;
import com.softron.quality.repository.QualityDefectRepository;
import com.softron.quality.repository.QualityTransactionRepository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.schema.qualitymodule.entity.QualityDefect;
import com.softron.schema.qualitymodule.entity.QualityTransaction;
import com.softron.schema.qualitymodule.entity.QualityType;

@Service
public class QualityDefectService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	QualityDefectRepository qualityDefectRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	QualityTransactionRepository qualityTransactionRepository;

	@Autowired
	OperationRepository operationRepository;

	@Autowired
	DefectRepository defectRepository;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Autowired
	AdminService adminService;
	

	String root = "QualityDefect";

	public Response create(QualityDefectDto qualityDefectDto) {
		try {

		QualityDefect qualityDefect = new QualityDefect();
		modelMapper.map(qualityDefectDto, qualityDefect);
        
		if(qualityDefect.getQualityTransaction()!=null) {
		Optional<QualityTransaction> qualityTransaction = qualityTransactionRepository
				.findById(qualityDefectDto.getQualityTransaction().getId());
		qualityDefect.setQualityTransaction(qualityTransaction.get());
		}
	
		if(qualityDefect.getOperation()!=null) {
		Optional<Operation> operation = operationRepository.findById(qualityDefectDto.getOperation().getId());
		qualityDefect.setOperation(operation.get());
		}
		
        if(qualityDefect.getDefect()!=null) {
		Optional<Defect> defect = defectRepository.findById(qualityDefectDto.getDefect().getId());
		qualityDefect.setDefect(defect.get());
        }
        
        if(qualityDefect.getOrganization()!=null) {
    		Optional<Organization> organization = organizationRepository.findById(qualityDefectDto.getOrganization().getId());
    		qualityDefect.setOrganization(organization.get());
        }

		qualityDefectRepository.save(qualityDefect);
		
		qualityDefectSetNull(qualityDefect);

		modelMapper.map(qualityDefect, qualityDefectDto);

		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, qualityDefectDto,
				String.format("%s created successfully", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll() {
		try {
		List<QualityDefect> qualityDefect = qualityDefectRepository.findAllByActiveTrue();
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(qualityDefect), qualityDefect.size(),String.format("All %ses", root));
	
		}
		catch (Exception e) {
		return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		 }
		}

	public Response get(Long id) {
		try {
			QualityDefect qualityDefect = qualityDefectRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			QualityDefectDto qualityDefectDto = new QualityDefectDto();
			qualityDefectSetNull(qualityDefect);
			modelMapper.map(qualityDefect, qualityDefectDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, qualityDefectDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	public Response getTopThreeQualityDefects(String startDate, String endDate) {
		try {
			QualityDefectDto qualityDefectDto = new QualityDefectDto();
			
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date startDateDateType = null;
			Date endDateDateType = null;
			try {
				startDateDateType = formatter1.parse(startDate);
				endDateDateType = formatter1.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			String sql="Select defect_name.name,defect_quantity.quantity\r\n" + 
					"from\r\n" + 
					"(\r\n" + 
					"    SELECT defect_id , COUNT(*) AS quantity\r\n" + 
					"    FROM quality_defect WHERE created_at BETWEEN :startDateDateType AND :endDateDateType\r\n" + 
					"    GROUP BY defect_id ORDER BY quantity DESC LIMIT 3\r\n" + 
					") as defect_quantity\r\n" + 
					"LEFT JOIN \r\n" + 
					"(select id,name from defect ) as defect_name ON defect_quantity.defect_id=defect_name.id";
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("startDateDateType", startDateDateType);
			query.setParameter("endDateDateType", endDateDateType);
			
			List<Object[]> object = query.getResultList();
			
			List<GraphDataDto> topThreeDefectList = new ArrayList<GraphDataDto>();
			
			for(Object[] obj:object) {
				GraphDataDto graphDataDto = new GraphDataDto();
				
				graphDataDto.setDefectName(obj[0].toString());
				graphDataDto.setValue(obj[1].toString());
				
				topThreeDefectList.add(graphDataDto);
			
			}
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, topThreeDefectList,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	
	public Response getTopQualityDefects(Long orgId, String startDate, String endDate) {
		try {
			QualityDefectDto qualityDefectDto = new QualityDefectDto();
			
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date startDateDateType = null;
			Date endDateDateType = null;
			try {
				startDateDateType = formatter1.parse(startDate);
				endDateDateType = formatter1.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			List<Organization> orgList = new ArrayList<Organization>();
			List<Organization> organizationDetail = organizationRepository.findAllById(orgId);
			orgList.addAll(organizationDetail);
			
			adminService.getOrganizationList(orgId, orgList);
			
			List<String> orgNames = new ArrayList<String>();
			List<Long> orgIds = new ArrayList<Long>();
			
			for(Organization org: orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}
			
			
			String organization = StringUtils.join(orgIds, ',');
			
			String sql = "SELECT defect_name.name,defect_quantity.quantity\r\n" + 
					"FROM(SELECT quality_transaction.org_id,quality_defect.defect_id,COUNT(*) AS quantity \r\n" + 
					"FROM quality_defect,quality_transaction\r\n" + 
					"WHERE quality_defect.created_at BETWEEN :startDateDateType AND :endDateDateType AND quality_transaction.id = quality_defect.qualitytransaction_id and quality_transaction.org_id in ("+organization+")\r\n" + 
					"GROUP BY defect_id,quality_transaction.org_id\r\n" + 
					"ORDER BY quantity DESC LIMIT 5) AS defect_quantity\r\n" + 
					"LEFT JOIN(SELECT id,NAME\r\n" + 
					"FROM defect) AS defect_name\r\n" + 
					"ON defect_quantity.defect_id = defect_name.id";
			
	
			
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("startDateDateType", startDateDateType);
			query.setParameter("endDateDateType", endDateDateType);
//			query.setParameter("startDate", startDate);
//			query.setParameter("endDate", endDate);
			
			List<Object[]> object = query.getResultList();
			
			List<GraphDataDto> topThreeDefectList = new ArrayList<GraphDataDto>();
			
			for(Object[] obj:object) {
				GraphDataDto graphDataDto = new GraphDataDto();
				
				graphDataDto.setDefectName(obj[0] !=null? obj[0].toString():"");
				graphDataDto.setValue(obj[1] !=null? obj[1].toString():"0");
				
				topThreeDefectList.add(graphDataDto);
			
			}
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, topThreeDefectList,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response delete(Long id) {

		try {
			QualityDefect qualityDefect = qualityDefectRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			qualityDefect.setActive(false);
			qualityDefectRepository.save(qualityDefect);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response update(Long id, QualityDefectDto qualityDefectDto) {
		try {
			QualityDefect qualityDefect = qualityDefectRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			Optional<Defect> defect = defectRepository.findById(qualityDefectDto.getDefect().getId());
			Optional<Operation> operation = operationRepository.findById(qualityDefectDto.getOperation().getId());
			Optional<QualityTransaction> qualityTransaction = qualityTransactionRepository.findById(qualityDefectDto.getQualityTransaction().getId());

			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

			PropertyMap<QualityDefectDto, QualityDefect> skipModifiedFieldsMap = new PropertyMap<QualityDefectDto, QualityDefect>() {
				protected void configure() {
					skip().setDefect(null);
					skip().setOperation(null);
					skip().setQualityTransaction(null);
				}
			};
			this.modelMapper.addMappings(skipModifiedFieldsMap);

			qualityDefectSetNull(qualityDefect);
			
			modelMapper.map(qualityDefectDto, qualityDefect);

			qualityDefect.setDefect(defect.get());
			qualityDefect.setOperation(operation.get());
			qualityDefect.setQualityTransaction(qualityTransaction.get());
			
			qualityDefectRepository.save(qualityDefect);
			modelMapper.map(qualityDefect, qualityDefectDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, qualityDefectDto,
					String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	private List<QualityDefectDto> getResponseDtoList(List<QualityDefect> qualityDefects) {
		List<QualityDefectDto> responseDtoList = new ArrayList<>();
		qualityDefects.forEach(qualityDefect -> {
			QualityDefectDto qualityDefectDto = new QualityDefectDto();
			qualityDefectSetNull(qualityDefect);
			modelMapper.map(qualityDefect, qualityDefectDto);
			responseDtoList.add(qualityDefectDto);
		});
		return responseDtoList;
	}
	
	private void qualityDefectSetNull(QualityDefect qualityDefect) {
		if(qualityDefect.getDefect() != null) {
			qualityDefect.getDefect().setQualityDefect(null);
			qualityDefect.getDefect().setWorkProcess(null);
		}
		if(qualityDefect.getOperation() != null) {
			qualityDefect.getOperation().setOperationBreakDown(null);
			qualityDefect.getOperation().setQualityDefect(null);
			qualityDefect.getOperation().setQualityTransaction(null);
			qualityDefect.getOperation().setWorkProcess(null);
		}
		
		if(qualityDefect.getQualityTransaction() != null) {
			qualityDefect.getQualityTransaction().setEmployee(null);
			qualityDefect.getQualityTransaction().setOperation(null);
			qualityDefect.getQualityTransaction().setQualityDefect(null);
			qualityDefect.getQualityTransaction().setQualityType(null);
//			qualityDefect.getQualityTransaction().setSection(null);
			qualityDefect.getQualityTransaction().setStyle(null);
			qualityDefect.getQualityTransaction().setWorkProcess(null);	
		}
	}
	
}

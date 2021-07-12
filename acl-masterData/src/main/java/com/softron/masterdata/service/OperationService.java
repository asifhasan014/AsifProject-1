package com.softron.masterdata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.admin.service.AdminService;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.utils.SentezIntegration;
import com.softron.masterdata.dto.ClientDto;
import com.softron.masterdata.dto.CompanyDto;
import com.softron.masterdata.dto.DefectDto;
import com.softron.masterdata.dto.EmployeeDto;
import com.softron.masterdata.dto.OperationDto;
import com.softron.masterdata.repository.CompanyRepository;
import com.softron.masterdata.repository.OperationRepository;
import com.softron.masterdata.repository.WorkProcessRepository;

@Service
public class OperationService {
	@Autowired
	OperationRepository operationRepository;

	@Autowired
	WorkProcessRepository workProcessRepository;
	
	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Autowired
	AdminService adminService;
	
	@Value("${spring.datasource2.jdbcUrl}")
	private String jdbcUrl;

	@Value("${spring.datasource2.username}")
	private String username;

	@Value("${spring.datasource2.password}")
	private String password;

	String root = "Operation";

	public Response create(OperationDto operationDto, Long orgId) {
		try {
		Operation operation = modelMapper.map(operationDto, Operation.class);
		
		if(operationDto.getWorkProcess() != null) {
		Optional<WorkProcess> workProcess = workProcessRepository.findById(operationDto.getWorkProcess().getId());
		operation.setWorkProcess(workProcess.get());
		}
		
		Optional<Organization> organaization = organizationRepository.findById(orgId);
		operation.setOrganization(organaization.get());
	
		operationRepository.save(operation);
		
		Organization org = adminService.getOrgAsCompany(operation.getOrganization().getId());
		
		SentezIntegration.operationCreation(jdbcUrl, username, password, operation,org.getId());

		BeanUtils.copyProperties(operation, operationDto);
		
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, operationDto,
				String.format("%s created successfully", root));
		}
		catch (Exception e) {
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
			
			for(Organization org: orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}
			
		List<Operation> operation = operationRepository.findAllByActiveTrueAndOrganizationIdIn(orgIds);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(operation), operation.size(),
				String.format("All %ses", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			Operation operation = operationRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			//ClientDto clientDto = modelMapper.map(client, ClientDto.class);
			OperationDto operationDto = new OperationDto();
			modelMapper.map(operation, operationDto);
			//BeanUtils.copyProperties(client, clientDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, operationDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response delete(Long id) {
		try {
			Operation operation = operationRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			operation.setActive(false);
			operationRepository.save(operation);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response update(Long id, OperationDto operationDto) {
		   try {
			  Operation operation = operationRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
		      Optional<WorkProcess> workProcess = workProcessRepository.findById(operationDto.getWorkProcess().getId());
		      modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

		      PropertyMap<OperationDto, Operation> skipModifiedFieldsMap = new PropertyMap<OperationDto, Operation>() {
		         protected void configure() {
		            skip().setWorkProcess(null);
		         }
		      };
		      this.modelMapper.addMappings(skipModifiedFieldsMap);

		      modelMapper.map(operationDto,operation);

		      operation.setWorkProcess(workProcess.get());
		      operationRepository.save(operation);
		      modelMapper.map(operation, operationDto);
		     
		      return ResponseUtils.getSuccessResponse(HttpStatus.OK, operationDto, String.format("%s updated successfully", root));
		   } catch (NoRecordExistsException e) {
		      return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		   } catch (Exception e) {
		      return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		   }
		}

	private List<OperationDto> getResponseDtoList(List<Operation> operations) {
		List<OperationDto> responseDtoList = new ArrayList<>();
		operations.forEach(operation -> {
			OperationDto operationDto = new OperationDto();
			modelMapper.map(operation,operationDto);
			responseDtoList.add(operationDto);
		});
		return responseDtoList;
	}
}

package com.softron.masterdata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.admin.service.AdminService;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Customer;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.entity.masterdata.Section;
import com.softron.schema.admin.entity.masterdata.WorkProcess;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.masterdata.dto.ClientDto;
import com.softron.masterdata.dto.CustomerDto;
import com.softron.masterdata.dto.OperationDto;
import com.softron.masterdata.dto.WorkProcessDto;
import com.softron.masterdata.repository.CompanyRepository;
import com.softron.masterdata.repository.WorkProcessRepository;

@Service
public class WorkProcessService {
	@Autowired
	WorkProcessRepository workProcessRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Autowired
	AdminService adminService;

	String root = "WorkProcess";

	public Response create(WorkProcessDto workProcessDto,Long orgId) {
		try {
		WorkProcess workProcess = modelMapper.map(workProcessDto, WorkProcess.class);
		Optional<Organization> organaization = organizationRepository.findById(orgId);
		workProcess.setOrganization(organaization.get());
		workProcessRepository.save(workProcess);
		
		BeanUtils.copyProperties(workProcess, workProcessDto);
		
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, workProcessDto,
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
			
		List<WorkProcess> workProcess = workProcessRepository.findAllByActiveTrueAndOrganizationIdIn(orgIds);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(workProcess), workProcess.size(),
				String.format("All %ses", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			WorkProcess workProcess = workProcessRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			
			WorkProcessDto workProcessDto = new WorkProcessDto();
			modelMapper.map(workProcess, workProcessDto);
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, workProcessDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response delete(Long id) {
		try {
			WorkProcess workProcess = workProcessRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			workProcess.setActive(false);
			workProcessRepository.save(workProcess);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response update(Long id, WorkProcessDto workProcessDto) {
		try {
			WorkProcess workProcess = workProcessRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			modelMapper.map(workProcessDto, workProcess);
			workProcessRepository.save(workProcess);

			modelMapper.map(workProcess,workProcessDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, workProcessDto,
					String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	private List<WorkProcessDto> getResponseDtoList(List<WorkProcess> workProces) {
		List<WorkProcessDto> responseDtoList = new ArrayList<>();
		workProces.forEach(workProcess -> {
			WorkProcessDto workProcessDto = new WorkProcessDto();
			modelMapper.map(workProcess,workProcessDto);
			responseDtoList.add(workProcessDto);
		});
		return responseDtoList;
	}
}

package com.softron.masterdata.service;

import java.util.ArrayList;
import java.util.List;

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
import com.softron.masterdata.dto.MachineTypeDto;
import com.softron.masterdata.dto.OperationDto;
import com.softron.masterdata.repository.MachineTypeRepository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.MachineType;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.repository.OrganizationRepository;

@Service
public class MachineTypeService {

	@Autowired
	MachineTypeRepository machineTypeRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	OrganizationRepository organizationRepository;

	String root = "MachineType";

	public Response create(MachineTypeDto machineTypeDto) {
		try {
		MachineType machineType = modelMapper.map(machineTypeDto, MachineType.class);
		machineTypeRepository.save(machineType);
		
		BeanUtils.copyProperties(machineType, machineTypeDto);
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, machineTypeDto,
				String.format("%s created successfully", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll() {
		try {
			
		List<MachineType> machineType = machineTypeRepository.findAllByActiveTrue();
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(machineType), machineType.size(),
				String.format("All %ses", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			MachineType machineType = machineTypeRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			
			MachineTypeDto machineTypeDto= new MachineTypeDto();
			modelMapper.map(machineType, machineTypeDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, machineTypeDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response delete(Long id) {
		try {
			MachineType machineType = machineTypeRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			machineType.setActive(false);
			machineTypeRepository.save(machineType);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response update(Long id, MachineTypeDto machineTypeDto) {
		try {
			MachineType machineType = machineTypeRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			modelMapper.map(machineTypeDto, machineType);
			machineTypeRepository.save(machineType);

			modelMapper.map(machineType, machineTypeDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, machineTypeDto,
					String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	private List<MachineTypeDto> getResponseDtoList(List<MachineType> machineTypes) {
		List<MachineTypeDto> responseDtoList = new ArrayList<>();
		machineTypes.forEach(machineType -> {
			MachineTypeDto machineTypeDto = new MachineTypeDto();
			modelMapper.map(machineType, machineTypeDto);
			responseDtoList.add(machineTypeDto);
		});
		return responseDtoList;
	}

}

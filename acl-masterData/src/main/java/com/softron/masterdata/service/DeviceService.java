package com.softron.masterdata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.dto.ClientDto;
import com.softron.masterdata.dto.DepartmentDto;
import com.softron.masterdata.dto.DeviceDto;
import com.softron.masterdata.repository.ClientRepository;
import com.softron.masterdata.repository.DeviceRepository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Department;
import com.softron.schema.admin.entity.masterdata.Device;
import com.softron.schema.admin.repository.OrganizationRepository;

@Service
public class DeviceService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	DeviceRepository deviceRepository;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	String root = "Device";
	
	public Response create(DeviceDto deviceDto,Long orgId) {
		try {
		Device device = modelMapper.map(deviceDto, Device.class);
		Optional<Organization> organaization = organizationRepository.findById(orgId);
		device.setOrganization(organaization.get());
		deviceRepository.save(device);
		
		BeanUtils.copyProperties(device, deviceDto);
		
		deviceDto = modelMapper.map(device, DeviceDto.class);
		
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, deviceDto,
				String.format("%s created successfully", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	public Response getAll() {
		try {
			List<Device> devices = deviceRepository.findAllByActiveTrue();
		
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(devices), devices.size(),
					String.format("All %ses", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	public Response get(Long id) {
		try {
			Device device = deviceRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			
			DeviceDto deviceDto = new DeviceDto();
			modelMapper.map(device, deviceDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, deviceDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	public Response delete(Long id) {
		try {
			Device device = deviceRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			
			device.setActive(false);
			deviceRepository.save(device);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	public Response update(Long id, DeviceDto deviceDto) {
		try {
			Device device = deviceRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			modelMapper.map(deviceDto, device);
			deviceRepository.save(device);

			modelMapper.map(device, deviceDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, deviceDto,
					String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	private List<DeviceDto> getResponseDtoList(List<Device> devices) {
		List<DeviceDto> responseDtoList = new ArrayList<>();
		devices.forEach(device -> {
			DeviceDto deviceDto = new DeviceDto();
			modelMapper.map(device, deviceDto);
			responseDtoList.add(deviceDto);
		});
		return responseDtoList;
	}
}

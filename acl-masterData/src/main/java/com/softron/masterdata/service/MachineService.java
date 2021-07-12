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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.dto.MachineDto;
import com.softron.masterdata.repository.DeviceRepository;
import com.softron.masterdata.repository.MachineRepository;
import com.softron.masterdata.repository.MachineTypeRepository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Department;
import com.softron.schema.admin.entity.masterdata.Device;
import com.softron.schema.admin.entity.masterdata.Machine;
import com.softron.schema.admin.entity.masterdata.MachineType;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.utils.SentezIntegration;

@Service
public class MachineService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	MachineRepository machineRepository;
	
	@Autowired
	MachineTypeRepository machineTypeRepository;
	
	@Autowired
	DeviceRepository deviceRepository;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Value("${spring.datasource2.jdbcUrl}")
	public String jdbcUrl;

	@Value("${spring.datasource2.username}")
	public String username;

	@Value("${spring.datasource2.password}")
	public String password;
	
	String root = "Machine";

	public Response create(MachineDto machineDto,Long orgId) {
		try {
		Machine machine = modelMapper.map(machineDto, Machine.class);
		if(orgId != null){
			Optional<Organization> organaization = organizationRepository.findById(orgId);
			machine.setOrganization(organaization.get());
		}
		if(machine.getMachineType() != null) {
			Optional<MachineType> machineType = machineTypeRepository.findById(machineDto.getMachineType().getId());
			machine.setMachineType(machineType.get());
		}
		
		if(machine.getDevice() != null){
			Optional<Device> device = deviceRepository.findById(machineDto.getDevice().getId());
			machine.setDevice(device.get());
		}
		machineRepository.save(machine);
		
		SentezIntegration.machineCreation(jdbcUrl, username, password, machine);
		BeanUtils.copyProperties(machine, machineDto);
		
		machineDto = modelMapper.map(machine, MachineDto.class);
		
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, machineDto,
				String.format("%s created successfully", root));
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	public Response getAll() {
		try {
		List<Machine> machinces = machineRepository.findAllByActiveTrue();
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(machinces), machinces.size(),
				String.format("All %ses", root));
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	public Response get(Long id) {
		try {
			Machine machine = machineRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			//ClientDto clientDto = modelMapper.map(client, ClientDto.class);
			MachineDto machineDto = new MachineDto();
			modelMapper.map(machine, machineDto);
			//BeanUtils.copyProperties(client, clientDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, machineDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	public Response delete(Long id) {
		try {
			Machine machine = machineRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			machine.setActive(false);
			machineRepository.save(machine);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	public Response update(Long id, MachineDto machineDto) {
		try {
			Machine machine = machineRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			machine.setCode(machineDto.getCode());
			machine.setDescription(machineDto.getDescription());
			if(machineDto.getMachineType() != null) {
				Optional<MachineType> machineType = machineTypeRepository.findById(machineDto.getMachineType().getId());
				machine.setMachineType(machineType.get());
			}
			
			if(machineDto.getDevice() != null){
				Optional<Device> device = deviceRepository.findById(machineDto.getDevice().getId());
				machine.setDevice(device.get());
			}
			
//			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
//			modelMapper.map(machineDto, machine);
			machineRepository.save(machine);

			//modelMapper.map(machine, machineDto);
			BeanUtils.copyProperties(machine, machineDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, machineDto,String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	private List<MachineDto> getResponseDtoList(List<Machine> machines) {
		List<MachineDto> responseDtoList = new ArrayList<>();
		
		machines.forEach(machine -> {
			MachineDto machineDto = new MachineDto();
			modelMapper.map(machine, machineDto);
			responseDtoList.add(machineDto);
		});
		return responseDtoList;
	}
}

package com.softron.masterdata.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.admin.service.AdminService;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.dto.EmployeeDto;
import com.softron.masterdata.dto.OperationBreakDownDto;
import com.softron.masterdata.dto.OperationMachineDto;
import com.softron.masterdata.dto.OrderEntityDto;
import com.softron.masterdata.repository.EmployeeRepository;
import com.softron.masterdata.repository.MachineTypeRepository;
import com.softron.masterdata.repository.OperationBreakDownRepository;
import com.softron.masterdata.repository.OperationMachineRepository;
import com.softron.masterdata.repository.OrderEntityRepository;
import com.softron.masterdata.repository.SectionRepository;
import com.softron.quality.service.QualityTransactionService;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Employee;
import com.softron.schema.admin.entity.masterdata.OperationBreakDown;
import com.softron.schema.admin.entity.masterdata.OperationMachine;
import com.softron.schema.admin.entity.masterdata.OrderEntity;
import com.softron.schema.admin.repository.OrganizationRepository;

@Service
public class OperationMachineService {

	private static final CrudRepository<Employee, Long> orderEntityRepository = null;

	private static final int ArrayList = 0;

	@Autowired
	OperationMachineRepository operationMachineRepository;

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	SectionRepository sectionRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	OperationBreakDownRepository operationBreakDownRepository;

	@Autowired
	MachineTypeRepository machineTypeRepository;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Autowired
	OrderEntityRepository orderEntityRepository2;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	QualityTransactionService qualityTransactionService;

	String root = "Operation Machine";

	public Response create(List<OperationMachineDto> operationMachineDtoList) {
		try {
			
		List<OperationMachineDto> OperationMachineDtoResponceList = new ArrayList<OperationMachineDto>();
		for(OperationMachineDto operationMachineDto : operationMachineDtoList) {	
			
		OperationMachine operationMachine = new OperationMachine();
		//modelMapper.map(operationMachineDto, operationMachine);
//		BeanUtils.copyProperties(operationMachine, operationMachineDto);
		BeanUtils.copyProperties(operationMachineDto, operationMachine);
		operationMachine.setEmployee(new Employee());
		if(operationMachineDto.getEmployee() != null) {
			operationMachine.getEmployee().setId(operationMachineDto.getEmployee().getId());
		}
		
		operationMachine.setOperationBreakDown(new OperationBreakDown());
		if(operationMachineDto.getOperationBreakDown()!=null) {
			operationMachine.getOperationBreakDown().setId(operationMachineDto.getOperationBreakDown().getId());
		}
		
		operationMachine.setOrganization(new Organization());
		if(operationMachineDto.getOrganization() != null) {
			operationMachine.getOrganization().setId(operationMachineDto.getOrganization().getId());
		
		}
		
//		operationMachine.setOrderEntity(new OrderEntity()); previous code(19052020)
//		if(operationMachineDto.getOrderEntity() != null){
//			operationMachine.getOrderEntity().setId(operationMachineDto.getOrderEntity().getId());
//		}
		
		operationMachine.setOrderEntity(new OrderEntity());
		if(operationMachineDto.getOrderEntityId() != null){
			operationMachine.getOrderEntity().setId(operationMachineDto.getOrderEntityId());
		}
			
		//BeanUtils.copyProperties(operationMachine.getEmployee(), operationMachineDto.getEmployee());

//		if(operationMachine.getEmployee()!=null) {
//			Optional<Employee> employee = employeeRepository.findById(operationMachineDto.getEmployee().getId());
//			operationMachine.setEmployee(employee.get());
//		}
//		
//		if(operationMachine.getOperationBreakDown()!=null) {
//			Optional<OperationBreakDown> operationBreakDown = operationBreakDownRepository.findById(operationMachineDto.getOperationBreakDown().getId());
//			operationMachine.setOperationBreakDown(operationBreakDown.get());
//		}
//		
//		if(operationMachine.getOrganization() != null) {
//			Optional<Organization> organization = organizationRepository.findById(operationMachineDto.getOrganization().getId());
//			operationMachine.setOrganization(organization.get());
//		}
//		
//		if(operationMachine.getOrderEntity() != null){
//			Optional<OrderEntity> orderEntity = orderEntityRepository2.findById(operationMachineDto.getOrderEntity().getId());
//			operationMachine.setOrderEntity(orderEntity.get());
//		}
		
		operationMachineRepository.save(operationMachine);
		
		BeanUtils.copyProperties(operationMachine, operationMachineDto);
		OperationMachineDtoResponceList.add(operationMachineDto);
		
		}

		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, OperationMachineDtoResponceList,OperationMachineDtoResponceList.size(),
				String.format("%s created successfully", root));
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll() {
		try {
			
		List<OperationMachine> operationMachines = operationMachineRepository.findAllByActiveTrue();

		return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoListConversion(operationMachines),
				operationMachines.size(), String.format("All %ses", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	public Response getAllOperationMachine() {
		try {
	
		List<OperationMachine> operationMachines = operationMachineRepository.findAllByActiveTrue();
		
		List<OperationMachineDto> operationMachineDtoList = getResponseDtoList(operationMachines);
		List<OperationMachineDto> operationMachineDto = new ArrayList<>();
		for (OperationMachineDto opmc : operationMachineDtoList) {
			
			if(opmc.getOrderEntity() != null && opmc.getOperationBreakDown().getOperation() != null && opmc.getOrganization() != null){
				//opmc.setTLSstatus(qualityTransactionService.getStatus(opmc, opmc.getOrderEntity().getId(), opmc.getOperationBreakDown().getOperation().getId(), opmc.getOrganization().getId()));
				operationMachineDto.add(opmc);
			}
		}

		return ResponseUtils.getSuccessResponse(HttpStatus.OK, operationMachineDto,
				operationMachineDto.size(), String.format("All %ses", root));
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			OperationMachine operationMachine = operationMachineRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			OperationMachineDto operationMachineDto = new OperationMachineDto();
			modelMapper.map(operationMachine, operationMachineDto);
			operationMachineDto.getOperationBreakDown().getStyle().setOperationBreakDown(null);
			// BeanUtils.copyProperties(company, companyDto);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, operationMachineDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response delete(Long id) {
		try {
			OperationMachine operationMachine = operationMachineRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			operationMachine.setActive(false);
			operationMachineRepository.save(operationMachine);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	public Response deleteByList(List<Long> IdList) {
		try {
			for(Long id : IdList) {
				OperationMachine operationMachine = operationMachineRepository.findById(id).orElseThrow(
						() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
				operationMachine.setActive(false);
				operationMachineRepository.save(operationMachine);
			}
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response update(Long id, OperationMachineDto operationMachineDto) {
		try {
			OperationMachine operationMachine = operationMachineRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			
			if(operationMachineDto.getEmployee()!=null) {
			Employee employeeValue = new Employee();
			BeanUtils.copyProperties(operationMachineDto.getEmployee(), employeeValue);
			operationMachine.setEmployee(employeeValue);
		}
		
		if(operationMachineDto.getOperationBreakDown()!=null) {
			
			OperationBreakDown operationBreakDownValue = new OperationBreakDown();
			BeanUtils.copyProperties(operationMachineDto.getOperationBreakDown(), operationBreakDownValue);
			operationMachine.setOperationBreakDown(operationBreakDownValue);
			
		}
		
		if(operationMachineDto.getOrganization() != null) {
			
			Organization organizationValue = new Organization();
			BeanUtils.copyProperties(operationMachineDto.getOrganization(), organizationValue);
			operationMachine.setOrganization(organizationValue);
		}
		
		if(operationMachineDto.getOrderEntity() != null){
			OrderEntity orderEntityValue = new OrderEntity();
			BeanUtils.copyProperties(operationMachineDto.getOrderEntity(), orderEntityValue);
			operationMachine.setOrderEntity(orderEntityValue);
			
		}
		operationMachineRepository.save(operationMachine);
		modelMapper.map(operationMachine, operationMachineDto);
			
//			Optional<Employee> employee = employeeRepository.findById(operationMachineDto.getEmployee().getId());
//			
//			Optional<OperationBreakDown> operationBreakDown = operationBreakDownRepository
//					.findById(operationMachineDto.getOperationBreakDown().getId());
//			Optional<Organization> organization = organizationRepository.findById(operationMachineDto.getOrganization().getId());
//			Optional<OrderEntity> orderEntity = orderEntityRepository2.findById(operationMachineDto.getOrderEntity().getId());
//			
//			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
//
//			PropertyMap<OperationMachineDto, OperationMachine> skipModifiedFieldsMap = new PropertyMap<OperationMachineDto, OperationMachine>() {
//				protected void configure() {
//					skip().setEmployee(null);
//					skip().setOperationBreakDown(null);
//					skip().setOrderEntity(null);
//					
//				}
//			};
//			this.modelMapper.addMappings(skipModifiedFieldsMap);
//
//			modelMapper.map(operationMachineDto, operationMachine);
//
//			operationMachine.setEmployee(employee.get());
//			operationMachine.setOperationBreakDown(operationBreakDown.get());
//			
//			operationMachine.setOrganization(organization.get());
//			operationMachine.setOrderEntity(orderEntity.get());
//			operationMachineRepository.save(operationMachine);
//			modelMapper.map(operationMachine, operationMachineDto);
//			operationMachineDto.getOperationBreakDown().getStyle().setOperationBreakDown(null);
//			operationMachineDto.setOrderEntity(null);
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, operationMachineDto,
					String.format("%s updated successfully", root));
			
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	private List<OperationMachineDto> getResponseDtoList(List<OperationMachine> operationMachines) {
		List<OperationMachineDto> responseDtoList = new ArrayList<>();
		operationMachines.forEach(operationMachine -> {
			OperationMachineDto operationMachineDto = new OperationMachineDto();
			modelMapper.map(operationMachine, operationMachineDto);
			//BeanUtils.copyProperties(operationMachine, operationMachineDto);

			responseDtoList.add(operationMachineDto);
		});
		return responseDtoList;
	}
	
	private List<OperationMachineDto> getResponseDtoListConversion(List<OperationMachine> operationMachines) {
		List<OperationMachineDto> responseDtoList = new ArrayList<>();
		operationMachines.forEach(operationMachine -> {
			OperationMachineDto operationMachineDto = new OperationMachineDto();
			
			BeanUtils.copyProperties(operationMachine, operationMachineDto);
			
			if(operationMachine.getOperationBreakDown()!=null) {
				
				OperationBreakDownDto operationBreakDownDto = new OperationBreakDownDto();
				BeanUtils.copyProperties(operationMachine.getOperationBreakDown(), operationBreakDownDto);
				operationMachineDto.setOperationBreakDown(operationBreakDownDto);
			}
			if(operationMachine.getEmployee()!=null) {
				
				EmployeeDto employeeDto = new EmployeeDto();
				BeanUtils.copyProperties(operationMachine.getEmployee(), employeeDto);
				operationMachineDto.setEmployee(employeeDto);
			}
			if(operationMachine.getOrderEntity()!=null) {
				
				OrderEntityDto orderEntityDto = new OrderEntityDto();
				BeanUtils.copyProperties(operationMachine.getOrderEntity(), orderEntityDto);
				operationMachineDto.setOrderEntity(orderEntityDto);
				operationMachineDto.setOrderEntityId(orderEntityDto.getId());
			}
			
//			BeanUtils.copyProperties(operationMachine, operationMachineDto);

			responseDtoList.add(operationMachineDto);
		});
		return responseDtoList;
	}

}

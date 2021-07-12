package com.softron.masterdata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.softron.masterdata.dto.OperationBreakDownDto;
import com.softron.masterdata.dto.OperationDto;
import com.softron.masterdata.dto.StyleDto;
import com.softron.masterdata.repository.MachineTypeRepository;
import com.softron.masterdata.repository.OperationBreakDownRepository;
import com.softron.masterdata.repository.OperationMachineRepository;
import com.softron.masterdata.repository.OperationRepository;
import com.softron.masterdata.repository.StyleRepository;
import com.softron.schema.admin.entity.masterdata.MachineType;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.entity.masterdata.OperationBreakDown;
import com.softron.schema.admin.entity.masterdata.OperationMachine;
import com.softron.schema.admin.entity.masterdata.Style;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.utils.SentezIntegration;

@Service
public class OperationBreakDownService extends Sentez {

	@Autowired
	OperationBreakDownRepository operationBreakDownRepository;

	@Autowired
	OperationMachineRepository operationMachineRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	StyleRepository styleRepository;

	@Autowired
	OperationRepository operationRepository;

	@Autowired
	MachineTypeRepository machineTypeRepository;

	@Autowired
	AdminService adminService;

	@Autowired
	OrganizationRepository organizationRepository;

	String root = "OperationBreakDown";

	public Response create(OperationBreakDownDto operationBreakDownDto) {
		try {
			OperationBreakDown operationBreakDown = modelMapper.map(operationBreakDownDto, OperationBreakDown.class);
			Optional<Style> style = styleRepository.findById(operationBreakDownDto.getStyle().getId());
			operationBreakDown.setStyle(style.get());
			Optional<Operation> operation = operationRepository.findById(operationBreakDownDto.getOperation().getId());
			operationBreakDown.setOperation(operation.get());

			Optional<MachineType> machineType = machineTypeRepository
					.findById(operationBreakDownDto.getMachineType().getId());
			operationBreakDown.setMachineType(machineType.get());
			operationBreakDownRepository.save(operationBreakDown);

			SentezIntegration.erpInventoryWorkStudyCreation(jdbcUrl, username, password, operationBreakDown);

			float machineQuantity = operationBreakDownDto.getMachineQuantity();
			List<OperationMachine> operationMachineList = new ArrayList();
			for (int i = 0; i < machineQuantity; i++) {
				OperationMachine operationMachine = new OperationMachine();
				operationMachine.setOperationBreakDown(operationBreakDown);
				operationMachineRepository.save(operationMachine);
				operationMachineList.add(operationMachine);
			}
			operationBreakDown.setOperationMachine(operationMachineList);
			BeanUtils.copyProperties(operationBreakDown, operationBreakDownDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, operationBreakDownDto,
					String.format("%s created successfully", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getAll() {
		try {

			List<OperationBreakDown> operationBreakDownMainList = new ArrayList<OperationBreakDown>();
			List<OperationBreakDown> operationBreakDown = operationBreakDownRepository.findAllByActiveTrue();
			for (OperationBreakDown operationBreakDowneach : operationBreakDown) {
				List<OperationMachine> operationMachineList = operationMachineRepository
						.findAllByOperationBreakDownAndActiveTrue(operationBreakDowneach);
				operationBreakDowneach.setOperationMachine(operationMachineList);
				operationBreakDownMainList.add(operationBreakDowneach);
			}
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtosList(operationBreakDownMainList),
					operationBreakDown.size(), String.format("All %ses", root));
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			OperationBreakDown operationBreakDown = operationBreakDownRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			OperationBreakDownDto operationBreakDownDto = new OperationBreakDownDto();
			List<OperationMachine> operationMachineList = operationMachineRepository
					.findAllByOperationBreakDownAndActiveTrue(operationBreakDown);
			operationBreakDown.setOperationMachine(operationMachineList);

			modelMapper.map(operationBreakDown, operationBreakDownDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, operationBreakDownDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response delete(Long id) {
		try {
			OperationBreakDown operationBreakDown = operationBreakDownRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			operationBreakDown.setActive(false);
			operationBreakDownRepository.save(operationBreakDown);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response update(Long id, OperationBreakDownDto operationBreakDownDto) {
		try {
			OperationBreakDown operationBreakDown = operationBreakDownRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			Optional<Style> style = styleRepository.findById(operationBreakDownDto.getStyle().getId());
			Optional<Operation> operation = operationRepository.findById(operationBreakDownDto.getOperation().getId());
			Optional<MachineType> machineType = machineTypeRepository
					.findById(operationBreakDownDto.getMachineType().getId());
			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

			PropertyMap<OperationBreakDownDto, OperationBreakDown> skipModifiedFieldsMap = new PropertyMap<OperationBreakDownDto, OperationBreakDown>() {
				protected void configure() {
					skip().setStyle(null);
					skip().setOperation(null);
					skip().setMachineType(null);
				}
			};
			this.modelMapper.addMappings(skipModifiedFieldsMap);

			modelMapper.map(operationBreakDownDto, operationBreakDown);

			// company.setId(id);

			operationBreakDown.setStyle(style.get());
			operationBreakDown.setOperation(operation.get());
			operationBreakDown.setMachineType(machineType.get());
			operationBreakDownRepository.save(operationBreakDown);

			SentezIntegration.erpInventoryWorkStudyUpdate(jdbcUrl, username, password, operationBreakDown);

			modelMapper.map(operationBreakDown, operationBreakDownDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, operationBreakDownDto,
					String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response getOperationBreakDownByStyleId(Long StyleId, Long org_id) {
		try {
			List<OperationBreakDown> operationBreakDownMainList = new ArrayList<OperationBreakDown>();
			List<OperationBreakDown> operationBreakDownList = operationBreakDownRepository
					.findAllByStyleIdAndActiveTrue(StyleId);
			List<OperationBreakDownDto> operationBreakDownDtoList = new ArrayList<OperationBreakDownDto>();
			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

			for (OperationBreakDown operationBreakDown : operationBreakDownList) {

				List<OperationMachine> operationMachineList = operationMachineRepository
						.findAllByOperationBreakDownAndActiveTrue(operationBreakDown);
				operationBreakDown.setOperationMachine(operationMachineList);
				operationBreakDownMainList.add(operationBreakDown);

			}

			for (OperationBreakDown operationBreakDownMain : operationBreakDownMainList) {
				OperationBreakDownDto operationBreakDownDto = new OperationBreakDownDto();
				List<OperationMachine> operationMachineLocal = new ArrayList<OperationMachine>();
				for (OperationMachine operationMachine : operationBreakDownMain.getOperationMachine()) {
					if (operationMachine.getOrganization() != null) {
						if (operationMachine.getOrganization().getId().equals(org_id)) {
							operationMachineLocal.add(operationMachine);
						}
					}
				}
				operationBreakDownMain.setOperationMachine(operationMachineLocal);
				modelMapper.map(operationBreakDownMain, operationBreakDownDto);
				operationBreakDownDtoList.add(operationBreakDownDto);
			}

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, operationBreakDownDtoList,
					operationBreakDownList.size(), String.format("All %ses", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	private List<OperationBreakDownDto> getResponseDtosList(List<OperationBreakDown> operationBreakDowns) {
		List<OperationBreakDownDto> responseDtoList = new ArrayList<>();
		operationBreakDowns.forEach(operationBreakDown -> {
			OperationBreakDownDto operationBreakDownDto = new OperationBreakDownDto();
			operationBreakDown.setMachineType(null);
			operationBreakDown.setOperationMachine(null);
			operationBreakDown.getOperation().setOrganization(null);

			if (operationBreakDown.getStyle() != null) {
				Style style = new Style();
				StyleDto styleDto = new StyleDto();
				style.setId(operationBreakDown.getStyle().getId());
				style.setName((operationBreakDown.getStyle().getName()));
				BeanUtils.copyProperties(style, styleDto);
				operationBreakDownDto.setStyle(styleDto);
			}
			if (operationBreakDown.getOperation() != null) {
				Operation operation = new Operation();
				OperationDto operationDto = new OperationDto();
				operation.setId(operationBreakDown.getOperation().getId());
				operation.setName(operationBreakDown.getOperation().getName());
				BeanUtils.copyProperties(operation, operationDto);
				operationBreakDownDto.setOperation(operationDto);
			}

			BeanUtils.copyProperties(operationBreakDown, operationBreakDownDto);

			responseDtoList.add(operationBreakDownDto);
		});
		return responseDtoList;
	}

}

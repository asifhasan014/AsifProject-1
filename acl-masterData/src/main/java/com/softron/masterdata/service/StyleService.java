package com.softron.masterdata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.softron.masterdata.dto.ItemDto;
import com.softron.masterdata.dto.MachineTypeDto;
import com.softron.masterdata.dto.OperationBreakDownDto;
import com.softron.masterdata.dto.OperationDto;
import com.softron.masterdata.dto.StyleDto;
import com.softron.masterdata.repository.CompanyRepository;
import com.softron.masterdata.repository.ItemRepository;
import com.softron.masterdata.repository.MachineTypeRepository;
import com.softron.masterdata.repository.OperationBreakDownRepository;
import com.softron.masterdata.repository.OperationRepository;
import com.softron.masterdata.repository.StyleRepository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Item;
import com.softron.schema.admin.entity.masterdata.MachineType;
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.entity.masterdata.OperationBreakDown;
import com.softron.schema.admin.entity.masterdata.Style;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.utils.SentezIntegration;

@Service
public class StyleService {

	@Autowired
	StyleRepository styleRepository;
	
	@Autowired
	OperationBreakDownRepository operationBreakDownRepository;
	
	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	MachineTypeRepository machineTypeRepository;
	
	@Autowired
	OperationRepository operationRepository;
	
	@Value("${spring.datasource2.jdbcUrl}")
	private String jdbcUrl;

	@Value("${spring.datasource2.username}")
	private String username;

	@Value("${spring.datasource2.password}")
	private String password;


	String root = "Style";

	public Response create(StyleDto styleDto, Long orgId) {
		
		try {
			Style style = saveData(null, styleDto, orgId);
		
		Organization org = adminService.getOrgAsCompany(style.getOrganization().getId());
		
		SentezIntegration.StyleCreationAsInventory(jdbcUrl, username, password, style,org.getId());
		
		//BeanUtils.copyProperties(style, styleDto);
		StyleDto styleDtoResponse = getResponseStyleDto(style);
		
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, styleDtoResponse, String.format("%s created successfully", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Style saveData(Long id, StyleDto styleDto, Long orgId) {

		Style style = new Style();

		if (id != null) {
			style = styleRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			styleDto.setId(id);
			BeanUtils.copyProperties(styleDto, style);
		}else{
			BeanUtils.copyProperties(styleDto, style);
		}

		

		List<OperationBreakDown> operationBreakDownList = new ArrayList<OperationBreakDown>();

		if (styleDto.getOperationBreakDown() != null) {
			for (OperationBreakDownDto operationBreakDownDto : styleDto.getOperationBreakDown()) {

				OperationBreakDown operationBreakDown = new OperationBreakDown();
//				BeanUtils.copyProperties(operationBreakDownDto, operationBreakDown);
				if (operationBreakDownDto.getId() != null) {
					operationBreakDown = operationBreakDownRepository.findById(operationBreakDownDto.getId())
							.orElseThrow(() -> new NoRecordExistsException(String
									.format("%s doesn't exist for id %s", root, operationBreakDownDto.getId())));
					BeanUtils.copyProperties(operationBreakDownDto, operationBreakDown);
					
					operationBreakDown.setStyle(style);
					if(operationBreakDownDto.getMachineType() != null){
						Optional<MachineType> machineType = machineTypeRepository.findById(operationBreakDownDto.getMachineType().getId());
						MachineType machineType2 = machineType.get();
						//BeanUtils.copyProperties(operationBreakDownDto.getMachineType(), machineType2);
						operationBreakDown.setMachineType(machineType2);
					}
					
					if(operationBreakDownDto.getOperation() != null){
						Optional<Operation> operation = operationRepository.findById(operationBreakDownDto.getOperation().getId());
						Operation operation2 = operation.get();
						//BeanUtils.copyProperties(operationBreakDownDto.getOperation(), operation2);
						operationBreakDown.setOperation(operation2);
						
					}
					
					operationBreakDownList.add(operationBreakDown);
					
				}else{
					BeanUtils.copyProperties(operationBreakDownDto, operationBreakDown);
					operationBreakDown.setStyle(style);
					if(operationBreakDownDto.getMachineType() != null){
						Optional<MachineType> machineType = machineTypeRepository.findById(operationBreakDownDto.getMachineType().getId());
						MachineType machineType2 = machineType.get();
						//BeanUtils.copyProperties(operationBreakDownDto.getMachineType(), machineType2);
						operationBreakDown.setMachineType(machineType2);
					}
					
					if(operationBreakDownDto.getOperation() != null){
						Optional<Operation> operation = operationRepository.findById(operationBreakDownDto.getOperation().getId());
						Operation operation2 = operation.get();
						//BeanUtils.copyProperties(operationBreakDownDto.getOperation(), operation2);
						operationBreakDown.setOperation(operation2);
						
					}
					
					operationBreakDownList.add(operationBreakDown);
				}
			}
		}
		style.setOperationBreakDown(operationBreakDownList);

		if(orgId != null) {
			Optional<Organization> organaization = organizationRepository.findById(orgId);
			style.setOrganization(organaization.get());
		}

		if(styleDto.getItem()!=null) {
			Optional<Item> item = itemRepository.findById(styleDto.getItem().getId());
			style.setItem(item.get());
		}

		styleRepository.save(style);
		return style;
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
			
		List<Style> styleMainList = new ArrayList<Style>();
		List<Style> style = styleRepository.findAllByActiveTrueAndOrganizationIdIn(orgIds);
		
		for(Style styleMain : style) {
			List<OperationBreakDown>operationBreakDownList = operationBreakDownRepository.findAllByStyleAndActiveTrue(styleMain);
			styleMain.setOperationBreakDown(operationBreakDownList);
			styleMainList.add(styleMain);
		}
		
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtosList(styleMainList), style.size(),String.format("All %ses", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			Style style = styleRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			StyleDto styleDto = new StyleDto();
			
			ItemDto itemDto = new ItemDto();
			itemDto.setId(style.getItem().getId());
			
			List<OperationBreakDown>operationBreakDownList = operationBreakDownRepository.findAllByStyleAndActiveTrue(style);
			style.setOperationBreakDown(operationBreakDownList);
			modelMapper.map(style, styleDto);
			styleDto.setItem(itemDto);
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, styleDto, String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response delete(Long id) {
		try {
			Style style = styleRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			style.setActive(false);
			styleRepository.save(style);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null, String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response update(Long id, StyleDto styleDto,Long orgId) {
		try {
			Style style = saveData(id, styleDto, orgId);

			//BeanUtils.copyProperties(style, styleDto);
			StyleDto styleDtoResponse = getResponseStyleDto(style);
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, styleDtoResponse,
					String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
		
	}

//	private List<StyleDto> getResponseDtoList(List<Style> styles){
//		List<StyleDto> responseDtoList = new ArrayList<>();
//		styles.forEach(style -> {
//			StyleDto styleDto = new StyleDto();
//			modelMapper.map(style, styleDto);
//			
//			responseDtoList.add(styleDto);
//		});
//		return responseDtoList;
//	}
	
	private List<StyleDto> getResponseDtosList(List<Style> styles){
		List<StyleDto> responseDtoList = new ArrayList<>();
		styles.forEach(style -> {
			StyleDto styleDto = new StyleDto();
			
			style.setOperationBreakDown(null);
			BeanUtils.copyProperties(style, styleDto);
			
			responseDtoList.add(styleDto);
		});
		return responseDtoList;
	}

	private StyleDto getResponseStyleDto(Style styles){
		
		StyleDto styleDto = new StyleDto();
		
		BeanUtils.copyProperties(styles, styleDto);
			
		if(styles.getOrganization() != null){
			Organization organization = new Organization();
			BeanUtils.copyProperties(styles.getOrganization(), organization);
			styleDto.setOrganization(organization);
		}
		
		if(styles.getItem() != null){
			ItemDto itemDto = new ItemDto();
			BeanUtils.copyProperties(styles.getItem(), itemDto);
			styleDto.setItem(itemDto);
		}
		
		if(styles.getOperationBreakDown() != null){
			List<OperationBreakDownDto> operationBreakDownDtoList = new ArrayList<OperationBreakDownDto>();
			for(OperationBreakDown operationBreakDown : styles.getOperationBreakDown()){
				OperationBreakDownDto operationBreakDownDto = new OperationBreakDownDto();
				BeanUtils.copyProperties(operationBreakDown, operationBreakDownDto);
				if(operationBreakDown.getMachineType() != null){
					MachineTypeDto machineTypeDto = new MachineTypeDto();
					BeanUtils.copyProperties(operationBreakDown.getMachineType(), machineTypeDto);
					operationBreakDownDto.setMachineType(machineTypeDto);
				}
				
				if(operationBreakDown.getOperation() != null){
					OperationDto operationDto = new OperationDto();
					BeanUtils.copyProperties(operationBreakDown.getOperation(), operationDto);
					operationBreakDownDto.setOperation(operationDto);
				}
				
				operationBreakDownDto.setSmv(operationBreakDown.getSmv());
				
				operationBreakDownDtoList.add(operationBreakDownDto);
			}
			styleDto.setOperationBreakDown(operationBreakDownDtoList);
		}
		return styleDto;
	}
	
}

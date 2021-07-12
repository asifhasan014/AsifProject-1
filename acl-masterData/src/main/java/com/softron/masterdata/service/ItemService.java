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
import com.softron.masterdata.dto.ClientDto;
import com.softron.masterdata.dto.ItemDto;
import com.softron.masterdata.repository.ClientRepository;
import com.softron.masterdata.repository.ItemRepository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Item;
import com.softron.schema.admin.repository.OrganizationRepository;

@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Autowired
	AdminService adminService;

	String root = "Item";
	
	public Response create(ItemDto itemDto, Long orgId) {
		try {
		Item item = new Item();
				
		BeanUtils.copyProperties(itemDto, item);
		Optional<Organization> organaization = organizationRepository.findById(orgId);
		item.setOrganization(organaization.get());
		
		itemRepository.save(item);
		
		BeanUtils.copyProperties(item, itemDto);
		
		Organization org = new Organization();
		org.setId(item.getOrganization().getId());
		org.setName(item.getOrganization().getName());
		org.setActive(true);
		itemDto.setOrganization(org);
		
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, itemDto,
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

			for (Organization org : orgList) {
				orgIds.add(org.getId());
				orgNames.add(org.getName());
			}
			
		List<Item> items = itemRepository.findAllByActiveTrueAndOrganizationIdIn(orgIds);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(items), items.size(),
				String.format("All %ses", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	public Response get(Long id) {
		try {
			Item item = itemRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			
			ItemDto itemDto = new ItemDto();
			BeanUtils.copyProperties(item, itemDto);
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, itemDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	public Response delete(Long id) {
		try {
			Item item = itemRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			item.setActive(false);
			itemRepository.save(item);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	public Response update(Long id, ItemDto itemDto) {
		try {
			Item item = itemRepository.findById(id).orElseThrow(() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			modelMapper.map(itemDto, item);
			itemRepository.save(item);

			BeanUtils.copyProperties(item, itemDto);
			//BeanUtils.copyProperties(client, clientDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, itemDto,String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	private List<ItemDto> getResponseDtoList(List<Item> items) {
		List<ItemDto> responseDtoList = new ArrayList<>();
		
		items.forEach(item -> {
			ItemDto itemDto = new ItemDto();
			BeanUtils.copyProperties(item, itemDto);
			
			Organization org = new Organization();
			org.setId(item.getOrganization().getId());
			org.setName(item.getOrganization().getName());
			org.setActive(true);
			itemDto.setOrganization(org);
			
			responseDtoList.add(itemDto);
		});
		return responseDtoList;
	}
	
	
}

package com.softron.masterdata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.softron.admin.service.AdminService;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.masterdata.dto.CustomerDto;
import com.softron.masterdata.repository.CustomerRepository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Customer;
import com.softron.schema.admin.repository.OrganizationRepository;
import com.softron.utils.SentezIntegration;


@Service
public class CustomerService {
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Value("${spring.datasource2.jdbcUrl}")
	private String jdbcUrl;

	@Value("${spring.datasource2.username}")
	private String username;

	@Value("${spring.datasource2.password}")
	private String password;

	String root = "Customer";

	public Response create(CustomerDto customerDto, Long orgId) {
		try {
		Customer customer = modelMapper.map(customerDto, Customer.class);
		Optional<Organization> organaization = organizationRepository.findById(orgId);
		customer.setOrganization(organaization.get());
		customerRepository.save(customer);
		Organization organizationAsCompany = adminService.getOrgAsCompany(customer.getOrganization().getId());
		SentezIntegration.erpCurrentAccount(jdbcUrl, username, password,customer,organizationAsCompany.getId());
		
		BeanUtils.copyProperties(customer, customerDto);
		
		return ResponseUtils.getSuccessResponse(HttpStatus.CREATED, customerDto,
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
			
		List<Customer> customers = customerRepository.findAllByActiveTrueAndOrganizationIdIn(orgIds);
		return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(customers), customers.size(),
				String.format("All %ses", root));
		}
		catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	public Response get(Long id) {
		try {
			Customer customer = customerRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			
			CustomerDto customerDto = new CustomerDto();
			modelMapper.map(customer, customerDto);
			//BeanUtils.copyProperties(customer, customerDto);
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, customerDto,
					String.format("%s retrieved Successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}
	
	public Response getCustomerByName(String name,int page,int volumeofData) {
		try {
			
			long count=0;

			count = customerRepository.countByNameAndActiveTrue(name);

			Pageable sortedByName =PageRequest.of(page, volumeofData);
			
			List<Customer> customerList = customerRepository.findAllByNameAndActiveTrue(name,sortedByName);
			
			
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, getResponseDtoList(customerList), count,
					String.format("All %ses", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response delete(Long id) {
		try {
			Customer customer = customerRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));
			customer.setActive(false);
			customerRepository.save(customer);
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, null,
					String.format("%s deleted successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}

	}

	public Response update(Long id, CustomerDto customerDto) {
		try {
			Customer customer = customerRepository.findById(id).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, id)));

			modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			modelMapper.map(customerDto, customer);
			customerRepository.save(customer);

			modelMapper.map(customer, customerDto);

			return ResponseUtils.getSuccessResponse(HttpStatus.OK, customerDto,
					String.format("%s updated successfully", root));
		} catch (NoRecordExistsException e) {
			return ResponseUtils.getFailResponse(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}

	private List<CustomerDto> getResponseDtoList(List<Customer> customers) {
		List<CustomerDto> responseDtoList = new ArrayList<>();
		
		customers.forEach(customer -> {
			
			CustomerDto customerDto = new CustomerDto();
			modelMapper.map(customer, customerDto);
			
			responseDtoList.add(customerDto);
		});
		return responseDtoList;
	}
	
}

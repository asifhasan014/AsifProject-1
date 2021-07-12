package com.softron.masterdata.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softron.common.businessobjects.Response;
import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.schema.admin.entity.masterdata.Customer;
import com.softron.security.domain.UserPrincipal;
import com.softron.masterdata.dto.CustomerDto;
import com.softron.masterdata.service.CompanyService;
import com.softron.masterdata.service.CustomerService;

@ApiController
@RequestMapping(UrlConstants.Customer.ROOT)
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.Customer.GET_ALL)
	public Response create(@Valid @RequestBody CustomerDto customerDto,final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return customerService.create(customerDto,user.getOrgId());
	}

	@GetMapping(UrlConstants.Customer.GET_ALL)
	public Response getAll(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return customerService.getAll(user.getOrgId());
	}

	@GetMapping(UrlConstants.Customer.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return customerService.get(id);
	}
	
	@GetMapping(UrlConstants.Customer.GETCUSBYNAME)
	public Response getCustomerByName(@RequestParam("name") String name,@RequestParam("page") int page,@RequestParam("volumeofData") int volumeofData) {
		return customerService.getCustomerByName(name,page,volumeofData);
	}

	@DeleteMapping(UrlConstants.Customer.GET)
	public Response delete(@PathVariable(value = "id") Long id) {

		return customerService.delete(id);
		
	}

	@PutMapping(UrlConstants.Customer.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody CustomerDto customerDto) {

		return customerService.update(id, customerDto);
	}
}

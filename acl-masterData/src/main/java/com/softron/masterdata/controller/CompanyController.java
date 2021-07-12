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
import org.springframework.web.bind.annotation.RestController;
import com.softron.common.businessobjects.Response;
import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;
import com.softron.schema.admin.entity.masterdata.Company;
import com.softron.security.domain.UserPrincipal;
import com.softron.masterdata.dto.CompanyDto;
import com.softron.masterdata.service.CompanyService;

@ApiController
@RequestMapping(UrlConstants.Company.ROOT)
public class CompanyController {

	@Autowired
	CompanyService companyService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.Company.GET_ALL)
	public Response create(@Valid @RequestBody CompanyDto companyDto) {
		return companyService.create(companyDto);
	}

	@GetMapping(UrlConstants.Company.GET_ALL)
	public Response getAll() {
		
		return companyService.getAll();
	}
	
	@GetMapping(UrlConstants.Company.GET_QUALITYTREE)
	public Response getQualityTree() {
		return companyService.getQualityTree();
	}

	@GetMapping(UrlConstants.Company.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return companyService.get(id);
	}

	@DeleteMapping(UrlConstants.Company.GET)
	public Response delete(@PathVariable(value = "id") Long id) {

		return companyService.delete(id);
	}

	@PutMapping(UrlConstants.Company.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody CompanyDto companyDto) {

		return companyService.update(id, companyDto);
	}

}

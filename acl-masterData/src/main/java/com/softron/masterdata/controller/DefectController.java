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
import com.softron.schema.admin.entity.masterdata.Client;
import com.softron.schema.admin.entity.masterdata.Customer;
import com.softron.schema.admin.entity.masterdata.Defect;
import com.softron.security.domain.UserPrincipal;
import com.softron.masterdata.dto.DefectDto;
import com.softron.masterdata.service.CustomerService;
import com.softron.masterdata.service.DefectService;

@ApiController
@RequestMapping(UrlConstants.Defect.ROOT)
public class DefectController {

	@Autowired
	DefectService defectService;
	
	@Autowired
    private UserDetailsService userDetailsService;


	@PostMapping(UrlConstants.Defect.GET_ALL)
	public Response create(@Valid @RequestBody DefectDto defectDto, final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return defectService.create(defectDto, user.getOrgId());
	}

	@GetMapping(UrlConstants.Defect.GET_ALL)
	public Response getAll(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return defectService.getAll(user.getOrgId());
	}

	@GetMapping(UrlConstants.Defect.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return defectService.get(id);
	}

	@DeleteMapping(UrlConstants.Defect.GET)
	public Response delete(@PathVariable(value = "id") Long id) {

		return defectService.delete(id);
		
	}

	@PutMapping(UrlConstants.Defect.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody DefectDto defectDto) {

		return defectService.update(id, defectDto);
	}
}

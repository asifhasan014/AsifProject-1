package com.softron.masterdata.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.softron.common.businessobjects.Response;
import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;
import com.softron.masterdata.dto.MachineDto;
import com.softron.masterdata.service.MachineService;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.Machine.ROOT)
public class MachineController {

	@Autowired
	MachineService machineService;
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@PostMapping(UrlConstants.Machine.GET_ALL)
	public Response create(@Valid @RequestBody MachineDto machineDto,final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return machineService.create(machineDto,user.getOrgId());
	}
	
	@GetMapping(UrlConstants.Machine.GET_ALL)
	public Response getAll() {
		return machineService.getAll();
	}
	
	@GetMapping(UrlConstants.Machine.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return machineService.get(id);
	}
	
	@DeleteMapping(UrlConstants.Machine.GET)
	public Response delete(@PathVariable(value = "id") Long id) {
		return machineService.delete(id);
	}
	
	@PutMapping(UrlConstants.Machine.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody MachineDto machineDto) {
		return machineService.update(id, machineDto);
	}
}

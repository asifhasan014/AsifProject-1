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
import com.softron.masterdata.dto.MachineTypeDto;
import com.softron.masterdata.service.MachineTypeService;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.MachineType.ROOT)
public class MachineTypeController {

	@Autowired
	MachineTypeService machineTypeService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.MachineType.GET_ALL)
	public Response create(@Valid @RequestBody MachineTypeDto machineTypeDto) {
		return machineTypeService.create(machineTypeDto);
	}

	@GetMapping(UrlConstants.MachineType.GET_ALL)
	public Response getAll() {
		
		return machineTypeService.getAll();
	}

	@GetMapping(UrlConstants.MachineType.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return machineTypeService.get(id);
	}

	@DeleteMapping(UrlConstants.MachineType.GET)
	public Response delete(@PathVariable(value = "id") Long id) {
		return machineTypeService.delete(id);
	}

	@PutMapping(UrlConstants.MachineType.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody MachineTypeDto machineTypeDto) {
		return machineTypeService.update(id, machineTypeDto);
	}
	
}

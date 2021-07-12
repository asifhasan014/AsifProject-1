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
import com.softron.masterdata.dto.DepartmentDto;
import com.softron.masterdata.dto.DeviceDto;
import com.softron.masterdata.service.DeviceService;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.Device.ROOT)
public class DeviceController {
	
	@Autowired
	DeviceService deviceService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.Device.GET_ALL)
	public Response create(@Valid @RequestBody DeviceDto deviceDto,final Principal principal ) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return deviceService.create(deviceDto,user.getOrgId());
	}
	
	@GetMapping(UrlConstants.Device.GET_ALL)
	public Response getAll() {
		
		return deviceService.getAll();
	}
	
	@GetMapping(UrlConstants.Device.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return deviceService.get(id);
	}
	
	@DeleteMapping(UrlConstants.Device.GET)
	public Response delete(@PathVariable(value = "id") Long id) {

		return deviceService.delete(id);
		
	}
	
	@PutMapping(UrlConstants.Device.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody DeviceDto deviceDto) {

		return deviceService.update(id, deviceDto);
	}
}

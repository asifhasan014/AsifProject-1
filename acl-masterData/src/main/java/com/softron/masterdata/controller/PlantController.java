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
import com.softron.schema.admin.entity.masterdata.Operation;
import com.softron.schema.admin.entity.masterdata.Plant;
import com.softron.security.domain.UserPrincipal;
import com.softron.masterdata.dto.PlantDto;
import com.softron.masterdata.service.OperationService;
import com.softron.masterdata.service.PlantService;

@ApiController
@RequestMapping(UrlConstants.Plant.ROOT)
public class PlantController {
	@Autowired
	PlantService plantService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.Plant.GET_ALL)
	public Response create(@Valid @RequestBody PlantDto plantDto) {
		return plantService.create(plantDto);
	}

	@GetMapping(UrlConstants.Plant.GET_ALL)
	public Response getAll() {
		
		return plantService.getAll();
	}

	@GetMapping(UrlConstants.Plant.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return plantService.get(id);
	}

	@DeleteMapping(UrlConstants.Plant.GET)
	public Response delete(@PathVariable(value = "id") Long id) {

		return plantService.delete(id);
		
	}

	@PutMapping(UrlConstants.Plant.GET)
	public Response updatePlant(@PathVariable(value = "id") Long id,
			@Valid @RequestBody PlantDto plantDto) {

		return plantService.update(id, plantDto);
	}
}

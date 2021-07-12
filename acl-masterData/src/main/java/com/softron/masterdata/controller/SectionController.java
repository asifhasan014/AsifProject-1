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
import com.softron.schema.admin.entity.masterdata.Plant;
import com.softron.schema.admin.entity.masterdata.Section;
import com.softron.security.domain.UserPrincipal;
import com.softron.masterdata.dto.SectionDto;
import com.softron.masterdata.service.PlantService;
import com.softron.masterdata.service.SectionService;

@ApiController
@RequestMapping(UrlConstants.Section.ROOT)
public class SectionController {

	@Autowired
	SectionService sectionService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.Section.GET_ALL)
	public Response create(@Valid @RequestBody SectionDto sectionDto) {
		return sectionService.create(sectionDto);
	}

	@GetMapping(UrlConstants.Section.GET_ALL)
	public Response getAll() {
		
		return sectionService.getAll();
	}

	@GetMapping(UrlConstants.Section.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return sectionService.get(id);
	}
	
	@GetMapping(UrlConstants.Section.GET_SUBSECTION)
	public Response getSubSection(@RequestParam("id") Long id) {
		return sectionService.getSubSection(id);
	}
	
	@GetMapping(UrlConstants.Section.GET_SECTION)
	public Response getSection() {
		return sectionService.getSection();
	}


	@DeleteMapping(UrlConstants.Section.GET)
	public Response delete(@PathVariable(value = "id") Long id) {

		return sectionService.delete(id);
		
	}

	@PutMapping(UrlConstants.Section.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody SectionDto sectionDto) {

		return sectionService.update(id, sectionDto);
	}
	
	@GetMapping(UrlConstants.Section.GET_ORGANIZATION_SECTION)
	public Response getOrganizationWiseSection(@RequestParam("id") Long id) {
		return sectionService.getOrganizationWiseSection(id);
	}
}

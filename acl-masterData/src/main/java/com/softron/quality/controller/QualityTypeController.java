package com.softron.quality.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.softron.quality.dto.QualityTypeDto;
import com.softron.quality.service.QualityTypeService;

@ApiController
@RequestMapping(UrlConstants.QualityType.ROOT)
public class QualityTypeController {


	@Autowired
	QualityTypeService qualityTypeService;

	@PostMapping(UrlConstants.QualityType.GET_ALL)
	public Response create(@Valid @RequestBody QualityTypeDto qualityTypeDto) {
		return qualityTypeService.create(qualityTypeDto);
	}

	@GetMapping(UrlConstants.QualityType.GET_ALL)
	public Response getAll() {
		return qualityTypeService.getAll();
	}

	@GetMapping(UrlConstants.QualityType.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return qualityTypeService.get(id);
	}

	@DeleteMapping(UrlConstants.QualityType.GET)
	public Response delete(@PathVariable(value = "id") Long id) {

		return qualityTypeService.delete(id);
	}

	@PutMapping(UrlConstants.QualityType.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody QualityTypeDto qualityTypeDto) {

		return qualityTypeService.update(id, qualityTypeDto);
	}

	
}

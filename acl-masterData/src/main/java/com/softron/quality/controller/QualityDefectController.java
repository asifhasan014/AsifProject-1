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
import org.springframework.web.bind.annotation.RequestParam;

import com.softron.common.businessobjects.Response;
import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;
import com.softron.quality.dto.QualityDefectDto;
import com.softron.quality.service.QualityDefectService;

@ApiController
@RequestMapping(UrlConstants.QualityDefect.ROOT)
public class QualityDefectController {

	@Autowired
	QualityDefectService qualityDefectService;

	@PostMapping(UrlConstants.QualityDefect.GET_ALL)
	public Response create(@RequestBody QualityDefectDto qualityDefectDto) {
		return qualityDefectService.create(qualityDefectDto);
	}

	@GetMapping(UrlConstants.QualityDefect.GET_ALL)
	public Response getAll() {
		return qualityDefectService.getAll();
	}

	@GetMapping(UrlConstants.QualityDefect.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return qualityDefectService.get(id);
	}
	
	@GetMapping(UrlConstants.QualityDefect.TOPTHREEQDEFECT)
	public Response getTopThreeQualityDefect(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) {
		return qualityDefectService.getTopThreeQualityDefects(startDate,endDate);
	}
	
	@GetMapping(UrlConstants.QualityDefect.GET_TOPQUALITYDEFECT)
	public Response getTopQualityDefect(@RequestParam("orgId") Long orgId,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) {
		return qualityDefectService.getTopQualityDefects(orgId,startDate,endDate);
	}

	@DeleteMapping(UrlConstants.QualityDefect.GET)
	public Response delete(@PathVariable(value = "id") Long id) {

		return qualityDefectService.delete(id);
	}

	@PutMapping(UrlConstants.QualityDefect.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody QualityDefectDto qualityDefectDto) {

		return qualityDefectService.update(id, qualityDefectDto);
	}
	
}

package com.softron.quality.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.softron.common.businessobjects.Response;
import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;
import com.softron.quality.dto.QualityTransactionDto;
import com.softron.quality.dto.VarienceDto;
import com.softron.quality.service.QualityTransactionService;
import com.softron.quality.service.VarienceService;

@ApiController
@RequestMapping(UrlConstants.QualityTransaction.ROOT)
public class VarienceController {

	
	@Autowired
	VarienceService varienceService;

	@PostMapping(UrlConstants.Varience.GET_ALL)
	public Response create(@Valid @RequestBody List<VarienceDto> varienceDtoList) {
		return varienceService.create(varienceDtoList);
	}
	
	@GetMapping(UrlConstants.Varience.GET_ALL)
	public Response getAll() {
		return varienceService.getAll();
	}
	
	@GetMapping(UrlConstants.Varience.GET_COLOR_BY_ORDER)
	public Response getColorListByOrder(@RequestParam("orderId") Long orderId) {
		return varienceService.getColorListByOrder(orderId);
	}
	
	@GetMapping(UrlConstants.Varience.GET_Varience_BY_ORDER_AND_COLOR)
	public Response getVarienceListByOrderAndColor(@RequestParam("orderId") Long orderId,@RequestParam("color") String color) {
		return varienceService.getVarienceListByOrderAndColor(orderId,color);
	}
	
}

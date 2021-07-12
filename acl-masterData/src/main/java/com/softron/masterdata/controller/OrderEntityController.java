package com.softron.masterdata.controller;

import java.security.Principal;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.softron.common.businessobjects.Response;
import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;
import com.softron.masterdata.dto.OrderEntityDto;
import com.softron.masterdata.service.OrderEntityService;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.OrderEntity.ROOT)
public class OrderEntityController {

	@Autowired
	OrderEntityService orderEntityService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.OrderEntity.GET_ALL)
	public Response create(@Valid @RequestBody OrderEntityDto orderEntityDto, final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return orderEntityService.create(orderEntityDto, user.getOrgId());
	}

	@GetMapping(UrlConstants.OrderEntity.GET_ALL)
	public Response getAll(final Principal principal, @RequestParam(value = "type",required = false) String type) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return orderEntityService.getAll(user.getOrgId(),type);
	}

	@GetMapping(UrlConstants.OrderEntity.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return orderEntityService.get(id);
	}
	
//	@GetMapping(UrlConstants.OrderEntity.GET_STYLE)
//	public Response getStyle(@RequestParam("customerId") Long customerId) {
//		return orderEntityService.getStyle(customerId);
//	}
	
	@GetMapping(UrlConstants.OrderEntity.GET_ORDER)
	public Response getOrderByCustomer(@RequestParam("customerId") Long customerId) {
		return orderEntityService.getOrderByCustomer(customerId);
	}

	@DeleteMapping(UrlConstants.OrderEntity.GET)
	public Response delete(@PathVariable(value = "id") Long id) {
		return orderEntityService.delete(id);
	}

	@PutMapping(UrlConstants.OrderEntity.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody OrderEntityDto orderEntityDto) {
		return orderEntityService.update(id, orderEntityDto);
	}
	
	@GetMapping(UrlConstants.OrderEntity.GET_ORDER_LIST)
	public Response getOrderList(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return orderEntityService.getOrderList(user.getOrgId());
	}
	
	@GetMapping(UrlConstants.OrderEntity.GET_ORDER_SUMMERY)
	public Response getOrderByCustomer(@RequestParam("orderId") String orderId) {
		return orderEntityService.getOrderSummery(orderId);
	}
	
	@GetMapping(UrlConstants.OrderEntity.GET_PARAMETER_SELECTION_PANEL)
	public Response getParameterSelectionPanel(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return orderEntityService.getParameterSelectionPanel(user.getOrgId());
	}

	
}

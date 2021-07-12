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
import com.softron.masterdata.dto.ItemDto;
import com.softron.masterdata.service.ItemService;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.Item.ROOT)
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@PostMapping(UrlConstants.Item.GET_ALL)
	public Response create(@Valid @RequestBody ItemDto itemDto, final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return itemService.create(itemDto,user.getOrgId());
	}

	@GetMapping(UrlConstants.Item.GET_ALL)
	public Response getAll(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return itemService.getAll(user.getOrgId());
	}

	@GetMapping(UrlConstants.Item.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return itemService.get(id);
	}
	
	@DeleteMapping(UrlConstants.Item.GET)
	public Response delete(@PathVariable(value = "id") Long id) {
		return itemService.delete(id);
	}

	@PutMapping(UrlConstants.Item.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody ItemDto itemDto) {
		return itemService.update(id, itemDto);
	}
	
}

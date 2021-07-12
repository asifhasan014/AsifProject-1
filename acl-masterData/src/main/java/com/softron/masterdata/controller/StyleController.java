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
import com.softron.masterdata.dto.StyleDto;
import com.softron.masterdata.service.StyleService;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.Style.ROOT)
public class StyleController {

	@Autowired
	StyleService styleService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.Style.GET_ALL)
	public Response create(@Valid @RequestBody StyleDto styleDto, final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return styleService.create(styleDto, user.getOrgId());
	}

	@GetMapping(UrlConstants.Style.GET_ALL)
	public Response getAll(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return styleService.getAll(user.getOrgId());
	}

	@GetMapping(UrlConstants.Style.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return styleService.get(id);
	}

	@DeleteMapping(UrlConstants.Style.GET)
	public Response delete(@PathVariable(value = "id") Long id) {
		return styleService.delete(id);
	}

	@PutMapping(UrlConstants.Style.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody StyleDto styleDto,final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return styleService.update(id, styleDto,user.getOrgId());
	}
	
}

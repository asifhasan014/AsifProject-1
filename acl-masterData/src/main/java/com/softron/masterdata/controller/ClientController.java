package com.softron.masterdata.controller;

import com.softron.common.businessobjects.Response;
import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;
import com.softron.masterdata.dto.ClientDto;
import com.softron.masterdata.service.ClientService;
import com.softron.security.domain.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import javax.validation.Valid;

@ApiController
@RequestMapping(UrlConstants.Client.ROOT)
public class ClientController {

	@Autowired
	ClientService clientService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.Client.GET_ALL)
	public Response create(@Valid @RequestBody ClientDto clientDto, final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return clientService.create(clientDto, user.getOrgId());
	}

	@GetMapping(UrlConstants.Client.GET_ALL)
	public Response getAll(final Principal principal) {
		final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
		return clientService.getAll(user.getOrgId());
	}
	
	@GetMapping(UrlConstants.Client.GET_CLIENTSTIME)
	public Response getClientsTime() {
		
		return clientService.getClientsTime();
	}

	@GetMapping(UrlConstants.Client.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return clientService.get(id);
	}
	
	@GetMapping(UrlConstants.Client.GET_CLIENTDASHBOARD)
	public Response getClientDashBoard(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) {
		return clientService.getClientDashBoard(startDate,endDate);
	}
	
	@GetMapping(UrlConstants.Client.GET_CLIENTCHART)
	public Response getClientChart(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) {
		return clientService.getClientChart(startDate,endDate);
	}

	@DeleteMapping(UrlConstants.Client.GET)
	public Response delete(@PathVariable(value = "id") Long id) {
		return clientService.delete(id);
	}

	@PutMapping(UrlConstants.Client.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody ClientDto clientDto) {
		return clientService.update(id, clientDto);
	}

}

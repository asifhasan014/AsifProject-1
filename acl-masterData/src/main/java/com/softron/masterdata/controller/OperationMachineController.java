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
import com.softron.common.businessobjects.Response;
import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;
import com.softron.masterdata.dto.OperationMachineDto;
import com.softron.masterdata.service.OperationMachineService;
import com.softron.security.domain.UserPrincipal;

@ApiController
@RequestMapping(UrlConstants.Operation.ROOT)
public class OperationMachineController {
	

	@Autowired
	OperationMachineService operationMachineService;
	
	@Autowired
    private UserDetailsService userDetailsService;

	@PostMapping(UrlConstants.OperationMachine.GET_ALL)
	public Response create(@Valid @RequestBody List<OperationMachineDto> operationMachineDtoList) {
		return operationMachineService.create(operationMachineDtoList);
	}

	@GetMapping(UrlConstants.OperationMachine.GET_ALL)
	public Response getAll() {
		
		return operationMachineService.getAll();
	}
	
	@GetMapping(UrlConstants.OperationMachine.GET_ALL_OPMC)
	public Response getAllOperationMachine() {
		
		return operationMachineService.getAllOperationMachine();
	}

	@GetMapping(UrlConstants.OperationMachine.GET)
	public Response get(@PathVariable(value = "id") Long id) {
		return operationMachineService.get(id);
	}

	@DeleteMapping(UrlConstants.OperationMachine.GET)
	public Response delete(@PathVariable(value = "id") Long id) {

		return operationMachineService.delete(id);
		
	}
	
	@DeleteMapping(UrlConstants.OperationMachine.DELETE_BY_LIST)
	public Response deleteByList(@Valid @RequestBody List<Long> IdList) {

		return operationMachineService.deleteByList(IdList);
	}

	@PutMapping(UrlConstants.OperationMachine.GET)
	public Response update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody OperationMachineDto operationMachineDto) {

		return operationMachineService.update(id, operationMachineDto);
	}

}

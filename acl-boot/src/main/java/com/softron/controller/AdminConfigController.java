package com.softron.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.softron.admin.service.AdminConfigService;
import com.softron.core.annotations.ApiController;

@ApiController
public class AdminConfigController {

	@Autowired
	private AdminConfigService adminConfigService;

    @GetMapping(value = "/api/config/admin")
    public String initAdminConfig() {
		adminConfigService.assignAllMenuPermissionToAdminRole();
        return "OK";
    }


}

package com.softron.admin.service;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.softron.schema.admin.entity.Role;
import com.softron.schema.admin.repository.MenuRepository;
import com.softron.schema.admin.repository.RoleRepository;

@Service
public class AdminConfigService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminConfigService.class);

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void assignAllMenuPermissionToAdminRole() {
		LOGGER.info("Assigning Access Control List menu permission to ADMIN ROLE...!!!");
		Optional<Role> role = roleRepository.findByNameIgnoreCase("ADMIN");
		role.ifPresent(r -> {
			if(CollectionUtils.isEmpty(r.getPermittedMenus())) {
				r.setPermittedMenus(new ArrayList<>(menuRepository.findByModuleId(r.getModuleId())));
			}
			LOGGER.info("Role: {}", r);
		});
	}
}

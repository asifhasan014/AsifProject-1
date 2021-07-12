package com.softron;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.softron.schema.admin.entity.Module;
import com.softron.schema.admin.entity.Role;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.repository.ModuleRepository;
import com.softron.schema.admin.repository.RoleRepository;
import com.softron.schema.admin.repository.UserRepository;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(InitialDataLoader.class);

	boolean alreadySetup = false;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModuleRepository moduleRepository;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOGGER.info("Initial setup started.");
		if (alreadySetup) {
			return;
		}

		// moduleRepository.deleteAll();
		// userRepository.deleteAll();

		Module admin = createModuleIfNotFound("Administration", "/administration/client", "lni-cog");
		createModuleIfNotFound("Master Data", "/master-data/client", "lni-database");
		createModuleIfNotFound("Production", "/production/dashboard", "lni-brick");
		createModuleIfNotFound("Quality", "/quality/dashboard", "lni-medall");
		createModuleIfNotFound("User Management", "/pages/dashboard", "ion-home");

		Role adminRole = createRoleIfNotFound("ADMIN", admin.getId());

		if (!userRepository.findByUserNameIgnoreCase("admin").isPresent()) {
			UserEntity user = new UserEntity();
			user.setUserName("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("s3cret"));
			user.setFullName("Intellier Limited");
			user.setEmail("admin@intellier.com");
			user.setMobile("12345678");
			user.setAssignedModules(moduleRepository.findAll());
			user.setRoles(Arrays.asList(adminRole));
			user.setEnabled(true);
			userRepository.save(user);
		}
		LOGGER.info("Initial setup done.");
		alreadySetup = true;
	}

	@Transactional
	public Role createRoleIfNotFound(String name, Long moduleId) {

		Role role = roleRepository.findByNameIgnoreCase(name).orElse(null);
		if (role == null) {
			role = new Role(name);
			role.setActive(true);
			role.setModuleId(moduleId);
			roleRepository.save(role);
		}
		return role;
	}

	@Transactional
	public Module createModuleIfNotFound(String name, String contextPath, String image) {
		Module module = moduleRepository.findByName(name);
		if (module == null) {
			module = new Module();
			module.setName(name);
			module.setContextPath(contextPath);
			module.setImage(image);
			module.setActive(true);
			moduleRepository.save(module);
		}
		return module;
	}

}

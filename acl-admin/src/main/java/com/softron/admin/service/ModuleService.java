package com.softron.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.admin.transferobjects.ModuleTO;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.service.UserMappingService;
import com.softron.common.utils.CommonsUtil;
import com.softron.schema.admin.entity.Module;
import com.softron.schema.admin.repository.ModuleRepository;
import com.softron.schema.admin.repository.UserRepository;
import com.softron.schema.bo.UserBO;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "modules")
public class ModuleService {

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMappingService userMappingService;

	public List<Module> getAllActiveModules() {
		return moduleRepository.findAllActive();
	}

	@Cacheable
	public List<Module> getAllModules() {
		return moduleRepository.findAll();
	}

	@Cacheable
	public Module getModuleById(final Long id) {
		return moduleRepository.findById(id).orElseThrow(() -> new NoRecordExistsException("Module doesn't exit"));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict
	public void deleteModuleById(final Long id) {
		moduleRepository.deleteById(id);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void save(final ModuleTO moduleTO) {
		final Module module = new Module();
		module.setName(moduleTO.getName());
		module.setIpBindingEnabled(moduleTO.isIpBindingEnabled());
		module.setMacBindingEnabled(moduleTO.isMacBindingEnabled());
		module.setIpAccessList(CommonsUtil.listToString(moduleTO.getIpAccessList()));
		module.setMacAccessList(CommonsUtil.listToString(moduleTO.getMacAccessList()));
		module.setActive(moduleTO.isActive());
		module.setRemark(moduleTO.getRemarks());
		moduleRepository.save(module);
	}

	public Map<String, List<UserBO>> getModuleUsers(final Long moduleId, final Long orgId) {
		Map<String, List<UserBO>> modules = new HashMap<>();
		modules.put("available", userRepository.findNonMappedUsersByModuleId(moduleId, orgId));
		modules.put("assigned", userRepository.findMappedUsersByModuleIdOrgId(moduleId, orgId));
		return modules;
	}

	public Long getUserOrgId(String userId) {
		return userMappingService.getUserOrgId(userId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrgUserMapping(Long moduleId, Long orgId, List<String> userIds) {
		userMappingService.saveOrgUserMapping(moduleId, orgId, userIds);
	}

}

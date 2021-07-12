package com.softron.admin.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.schema.admin.entity.Menu;
import com.softron.schema.admin.repository.MenuRepository;

@Service
@Transactional(readOnly = true)
public class MenuService {

	@Autowired
	private MenuRepository menuRepository;

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Menu menu) {
		menuRepository.save(menu);
	}

	public List<Menu> getAllActive() {
		return menuRepository.findAllActive();
	}

	public List<Menu> getAll() {
		return menuRepository.findAll();
	}

	public Set<Menu> getByModuleId(Long moduleId) {
		return menuRepository.findByModuleId(moduleId);
	}

	public Set<Menu> getUserPermittedMenuByModuleId(String userId, Long moduleId) {
		return menuRepository.findPermittedMenuByUserIdModuleId(userId, moduleId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(final Long id) {
		menuRepository.deleteById(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveMenuOrdering(final Menu menuTO) {
		menuRepository.updateMenuParent(menuTO.getId(), menuTO.getParentId(), menuTO.getSortOrder());
	}

}

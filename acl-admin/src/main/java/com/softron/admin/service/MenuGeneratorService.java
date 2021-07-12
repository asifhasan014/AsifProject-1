package com.softron.admin.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softron.common.businessobjects.MenuBO;
import com.softron.common.businessobjects.MenuJsonBO;
import com.softron.common.businessobjects.ModuleMenuBO;
import com.softron.schema.admin.entity.Menu;
import com.softron.schema.admin.entity.Module;
import com.softron.schema.admin.repository.MenuRepository;

@Service
@Transactional
public class MenuGeneratorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuGeneratorService.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private ModuleService moduleService;

	private List<Module> allModules = null;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void generate(final File file) {
		Long count = menuRepository.count();
		if (count > 0) {
			LOGGER.info("Menu table contains {} records, please truncate the table for fresh menu setup.", count);
			return;
		}
		try {
			MenuJsonBO json = objectMapper.readValue(file, MenuJsonBO.class);
			LOGGER.trace("Parsed JSON: ", json.toString());
			json.getModules().forEach(processModule());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		LOGGER.info("Menu generation task completed.");
		allModules = null;
	}

	private Consumer<ModuleMenuBO> processModule() {
		return module -> {
			final Long moduleId = getModuleId(module.getModuleName());
			module.getMenus().forEach(processMenu(0L, moduleId));
		};
	}

	private Consumer<MenuBO> processMenu(Long parentId, Long moduleId) {
		return m -> {
			Menu menu = new Menu();
			BeanUtils.copyProperties(m, menu);
			menu.setModuleId(moduleId);
			menu.setParentId(parentId);
			save(menu);
			List<MenuBO> children = m.getChildren();
			if (!CollectionUtils.isEmpty(children)) {
				children.forEach(processMenu(menu.getId(), moduleId));
			}
		};
	}

	private Long getModuleId(String moduleName) {
		if (CollectionUtils.isEmpty(allModules)) {
			allModules = moduleService.getAllActiveModules();
		}
		return allModules.stream().filter(module -> module.getName().equalsIgnoreCase(moduleName)).findAny()
				.map(m -> m.getId()).orElse(null);
	}

	private void save(Menu menu) {
		menuRepository.save(menu);
	}

}

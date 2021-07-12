package com.softron.admin.controller;

import static com.softron.core.constants.AdminApiConstants.MENU_ENDPOINT;
import static com.softron.core.constants.AdminApiConstants.MENU_ORDERING_ENDPOINT;
import static com.softron.core.constants.ApiConstants.ALL_VERB;
import static com.softron.core.constants.ApiConstants.BY_MODULE_VERB;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;
import static com.softron.core.constants.ApiConstants.LIST_VERB;
import static com.softron.core.constants.ApiConstants.TREE_VERB;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.softron.admin.service.MenuService;
import com.softron.admin.transferobjects.MenuItem;
import com.softron.admin.transferobjects.MenuTO;
import com.softron.admin.transferobjects.RawMenu;
import com.softron.admin.utils.MenuBuilder;
import com.softron.common.transferobjects.RawTree;
import com.softron.common.transferobjects.TreeTO;
import com.softron.common.utils.AuthUtil;
import com.softron.common.utils.TreeBuilder;
import com.softron.core.annotations.ApiController;
import com.softron.schema.admin.entity.Menu;

@ApiController
public class MenuController {

	@Autowired
	private MenuService menuService;

	@GetMapping(value = MENU_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
	public MenuTO getMenu(@PathVariable(ID) Long id) {
		return new MenuTO();
	}

	@GetMapping(value = MENU_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
	public List<Menu> getAllActiveMenus() {
		return menuService.getAllActive();
	}

	@GetMapping(value = MENU_ENDPOINT + ALL_VERB, produces = EXTERNAL_MEDIA_TYPE)
	public List<Menu> getAllMenus() {
		return menuService.getAll();
	}

	@PostMapping(value = MENU_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
	public ResponseEntity<Void> saveMenu(@RequestBody Menu menuTO) {
		menuService.save(menuTO);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@PutMapping(value = MENU_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
	public ResponseEntity<Void> updateMenu(@RequestBody Menu menuTO) {
		menuService.save(menuTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@GetMapping(value = MENU_ENDPOINT + TREE_VERB, produces = EXTERNAL_MEDIA_TYPE)
	public List<TreeTO> getMenuTree() {
		List<Menu> locations = menuService.getAllActive();
		List<RawTree> rawData = locations.stream().map(menuToTreeMapper()).collect(toList());
		return TreeBuilder.build(rawData);
	}

	@GetMapping(value = MENU_ENDPOINT + ID_PATH_VAR + LIST_VERB, produces = EXTERNAL_MEDIA_TYPE)
	public ResponseEntity<Set<Menu>> getMenuList(@PathVariable(ID) Long moduleId) {
		return new ResponseEntity<>(menuService.getByModuleId(moduleId), HttpStatus.OK);
	}

	@GetMapping(value = MENU_ENDPOINT + ID_PATH_VAR + TREE_VERB, produces = EXTERNAL_MEDIA_TYPE)
	public List<TreeTO> getMenuTree(@PathVariable(ID) Long moduleId) {
		Set<Menu> locations = menuService.getByModuleId(moduleId);
		List<RawTree> rawData = locations.stream().map(menuToTreeMapper()).collect(toList());
		return TreeBuilder.build(rawData);
	}

	@GetMapping(value = MENU_ENDPOINT + ID_PATH_VAR + BY_MODULE_VERB, produces = EXTERNAL_MEDIA_TYPE)
	public List<MenuItem> getPermittedModuleMenuByModuleId(@PathVariable(ID) Long moduleId) {
		Set<Menu> locations = menuService.getUserPermittedMenuByModuleId(AuthUtil.getUserName(), moduleId);
		List<RawMenu> rawData = locations.stream().map(rawMenuMapper()).collect(toList());
		return MenuBuilder.build(rawData);
	}

	@DeleteMapping(value = MENU_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
	public ResponseEntity<Void> deleteMenu(@PathVariable(ID) Long id) {
		menuService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = MENU_ORDERING_ENDPOINT)
	public ResponseEntity<Void> saveMenuOrdering(@PathVariable(ID) Long moduleId, @RequestBody Menu menuTO) {
		menuService.saveMenuOrdering(menuTO);
		return ResponseEntity.ok().build();
	}

	private Function<Menu, RawTree> menuToTreeMapper() {
		return l -> new RawTree(l.getId(), l.getTitle(), l.getParentId());
	}

	private Function<Menu, RawMenu> rawMenuMapper() {
		return l -> RawMenu.builder()
				.id(l.getId())
				.parentId(l.getParentId())
				.title(l.getTitle())
				.icon(l.getIcon())
				.home(l.isHome())
				.link(l.getLink())
				.sortOrder(l.getSortOrder())
				.hidden(l.isHidden())
				.build();
	}

}

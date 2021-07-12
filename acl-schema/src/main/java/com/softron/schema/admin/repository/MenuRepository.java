package com.softron.schema.admin.repository;

import com.softron.schema.admin.entity.Menu;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	@Query("Select m from Menu m where m.active=true")
	public List<Menu> findAllActive();

	public void deleteById(Long id);

	//@Query(value = "SELECT * FROM MODULE_MENUS WHERE ACTIVE=1 and MODULE_ID= :moduleId CONNECT BY PRIOR ID=PARENT_MENU_ID ORDER SIBLINGS BY SORT_ORDER", nativeQuery = true)
	@Query(value = "SELECT m.* FROM module_menus m LEFT JOIN module_menus m1 on m1.PARENT_MENU_ID = m.ID WHERE m.ACTIVE=true and m.MODULE_ID= :moduleId ORDER BY m.SORT_ORDER", nativeQuery = true)
	public Set<Menu> findByModuleId(@Param("moduleId") Long moduleId);

	@Query(value = "SELECT m.* FROM module_menus m LEFT JOIN module_menus m1 on m1.PARENT_MENU_ID = m.ID WHERE m.ACTIVE=true and  m.ID in "
			+ "(SELECT PERMITTED_MENUS_ID FROM user_roles_permitted_menus WHERE ROLE_ROLE_ID IN "
			+ "(SELECT ROLES_ROLE_ID FROM users_roles WHERE USER_ENTITY_USER_ID = :userId)) AND m.MODULE_ID= :moduleId ORDER BY m.SORT_ORDER", nativeQuery = true)
	/*@Query(value = "SELECT m.* FROM module_menus m WHERE m.ACTIVE=1 and m.ID in "
			+ "(SELECT PERMITTED_MENUS_ID FROM user_roles_permitted_menus WHERE ROLE_ROLE_ID IN "
			+ "(SELECT ROLES_ROLE_ID FROM users_roles WHERE USER_ENTITY_USER_ID = :userId)) AND m.MODULE_ID= :moduleId CONNECT BY PRIOR ID=PARENT_MENU_ID ORDER SIBLINGS BY SORT_ORDER", nativeQuery = true)
	*/
	public Set<Menu> findPermittedMenuByUserIdModuleId(@Param("userId") String userId, @Param("moduleId") Long moduleId);

	public Set<Menu> findByIdIn(List<Long> ids);

	@Modifying
	@Query("Update #{#entityName} m set m.parentId= :parentId, m.sortOrder= :sortOrder where m.id= :id")
	public void updateMenuParent(@Param("id") Long id, @Param("parentId") Long parentId, @Param("sortOrder") int sortOrder);

}
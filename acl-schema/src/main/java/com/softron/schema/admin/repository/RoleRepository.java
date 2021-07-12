package com.softron.schema.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.schema.admin.entity.Role;

/**
 * 
 * User RoleRepository interface for data store CRUD operations.
 *
 * @author Mozahid
 * @version 1.0
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	public Optional<Role> findByNameIgnoreCase(String name);

	@Query("Select r from Role r where r.active=true")
	public List<Role> findAllActive();
	
	public List<Role> findByModuleId(Long moduleId);

	public void deleteById(Long id);
	
	@Query("Select r from Role r where r.moduleId in :moduleIds")
    public List<Role> findByModuleIds(@Param("moduleIds") List<Long> moduleIds);

}

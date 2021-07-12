package com.softron.schema.admin.repository;

import com.softron.schema.admin.entity.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * User RolePrivilegeRepository interface for data store CRUD operations.
 *
 * @author Mozahid
 * @version 1.0
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    
    public Privilege findByName(String name);

    public void deleteById(Long id);

}

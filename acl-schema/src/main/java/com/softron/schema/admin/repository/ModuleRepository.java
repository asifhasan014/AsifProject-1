package com.softron.schema.admin.repository;

import com.softron.schema.admin.entity.Module;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    @Query("Select m from Module m where m.active=true")
    public List<Module> findAllActive();
    
    public Module findByName(String name);

    public void deleteById(Long id);

}

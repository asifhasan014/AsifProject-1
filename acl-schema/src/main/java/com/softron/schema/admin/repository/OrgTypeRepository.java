package com.softron.schema.admin.repository;

import com.softron.schema.admin.entity.OrgType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgTypeRepository extends JpaRepository<OrgType, Long> {

    @Query("Select o from OrgType o where o.active=true")
    public List<OrgType> findAllActive();

    public void deleteById(Long id);
}

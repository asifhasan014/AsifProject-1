package com.softron.schema.admin.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softron.schema.admin.entity.Organization;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query("Select org from Organization org where org.active=true")
//    @Query("Select org from Organization org where org.active=true and org.parentId IS NULL")
    public List<Organization> findAllActive();
    
    //@Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
    
    @Query("Select org from Organization org where org.active=true and  org.parentId = :parentId")//c.name = :name
    public List<Organization> findByParentId(@Param("parentId") Long parentId); 
    
    @Query("Select org from Organization org where org.active=true and  org.parentId IS NULL")//c.name = :name
    public List<Organization> findAllWithOutParentId(); 
    
    public List<Organization> findAllByParentId(Long parentId);
    
    public Optional<Organization> findById(Long parentId);
    
    public List<Organization> findAllById(Long orgId);
    
    @Query(value="Select * from organizations where active=true and id= :orgId",nativeQuery = true)
    public Organization findOrganizationById(@Param("orgId") Long orgId);


    public void deleteById(Long id);
    public Organization findByIdAndActiveTrue(Long id);
}

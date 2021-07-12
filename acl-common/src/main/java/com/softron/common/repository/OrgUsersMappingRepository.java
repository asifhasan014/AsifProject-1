package com.softron.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.common.entity.OrgUsersMapping;

@Repository
public interface OrgUsersMappingRepository extends JpaRepository<OrgUsersMapping, Long> {

    @Query("Select m.userId from #{#entityName} m where m.moduleId = :moduleId and m.orgId = :orgId")
    public List<String> findAllUserIdsByOrgId(@Param("moduleId") Long moduleId, @Param("orgId") Long orgId);
    
    @Query("Select m.orgId from #{#entityName} m where m.moduleId = :moduleId and m.userId = :userId")
    public Long findOrgIdByUserId(@Param("moduleId") Long moduleId, @Param("userId") String userId);
    
    @Query("Select m from #{#entityName} m where m.moduleId = :moduleId and m.orgId in :orgIds")
    public List<OrgUsersMapping> findAllUserIdsByOrgIdsIn(@Param("moduleId") Long moduleId, @Param("orgIds") List<Long> orgIds);
    
    @Modifying
    @Query("delete from #{#entityName} m where m.moduleId = :moduleId and m.orgId = :orgId")
    public void deleteByOrgId(@Param("moduleId") Long moduleId, @Param("orgId") Long orgId);

}

package com.softron.reporting.repository;

import com.softron.reporting.entity.ReportOrganization;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportingOrganizationRepository extends JpaRepository<ReportOrganization, Long> {

    @Query("Select org from #{#entityName} org where org.active=true")
    public List<ReportOrganization> findAllActive();

    @Query("Select org from #{#entityName} org inner join OrgUsersMapping m on org.id = m.orgId where m.moduleId = :moduleId and m.userId= :userId")
    public Optional<ReportOrganization> findByUserId(@Param("moduleId") Long moduleId, @Param("userId") String userId);

    public void deleteById(Long id);
    
    public List<ReportOrganization> findByIdIn(List<Long> ids);
}

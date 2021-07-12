package com.softron.census.schema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.census.schema.entity.CensusPeriod;

@Repository
public interface CensusPeriodRepository extends JpaRepository<CensusPeriod, Long> {

    @Query("Select c from CensusPeriod c where c.active = true")
    public List<CensusPeriod> findAllActive();

    @Query("Select c from CensusPeriod c")
    public List<CensusPeriod> findAllCensusPeriod();

    @Query("Select c from CensusPeriod c order by c.censusPeriod ASC")
    public List<CensusPeriod> findAllCensusPeriodByOrder();

    @Query("Select c from CensusPeriod c where c.censusPeriod=:name")
    public List<CensusPeriod> findDuplicateCensusPeriod(@Param("name") String name);
}

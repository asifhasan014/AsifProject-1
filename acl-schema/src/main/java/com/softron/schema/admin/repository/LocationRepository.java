package com.softron.schema.admin.repository;

import com.softron.schema.admin.entity.Location;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("Select loc from Location loc where loc.active=true")
    public List<Location> findAllActive();

    public void deleteById(Long id);

    /*
     * Oracle Native query.
     */
    @Query(value = "SELECT * FROM LOCATIONS WHERE PARENT_ID= :parentId AND ACTIVE=1 CONNECT BY PRIOR ID=PARENT_ID ORDER SIBLINGS BY SERIAL_NO", nativeQuery = true)
    public List<Location> getChildLocations(@Param("parentId") Long parentId);

}

package com.softron.schema.legal.repository;

import com.softron.schema.admin.entity.Petitioner;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionerRepository extends JpaRepository<Petitioner, Long> {

    @Query("Select p from Petitioner p where p.active=true")
    public List<Petitioner> findAllActive();

    public void deleteById(Long id);

}

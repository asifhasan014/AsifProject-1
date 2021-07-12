package com.softron.schema.legal.repository;

import com.softron.schema.admin.entity.Respondent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RespondentRepository extends JpaRepository<Respondent, Long> {

    @Query("Select r from Respondent r where r.active=true")
    public List<Respondent> findAllActive();

    public void deleteById(Long id);

}
package com.softron.schema.legal.repository;
import com.softron.schema.admin.entity.Advocate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvocateRepository extends JpaRepository<Advocate, Long> {

    @Query("Select a from Advocate a where a.active=true")
    public List<Advocate> findAllActive();

    public void deleteById(Long id);

}

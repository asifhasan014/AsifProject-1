package com.softron.quality.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.schema.qualitymodule.entity.Varience;

@Repository
public interface VarienceRepository extends JpaRepository<Varience, Long>  {
	
	@Query(value="SELECT color FROM varience WHERE orderentity_id=:orderId AND active=true",nativeQuery = true)
	public List<Object> getcolorByOrderId(@Param("orderId") Long orderId);
	
	@Query(value="SELECT * FROM varience WHERE color=:color AND orderentity_id=:orderId AND active=true",nativeQuery = true)
	public List<Varience> getVarienceListByOrderAndColor(@Param("orderId") Long orderId,@Param("color") String color);
	
	public List<Varience> findAllByActiveTrue();

}

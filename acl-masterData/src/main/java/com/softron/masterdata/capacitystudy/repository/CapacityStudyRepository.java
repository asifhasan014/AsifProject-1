package com.softron.masterdata.capacitystudy.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.masterdata.Customer;
import com.softron.schema.capacitystudy.entity.CapacityStudy;

@Repository
public interface CapacityStudyRepository extends JpaRepository<CapacityStudy, Long>{
	
//	@Query(value = "",nativeQuery = true)
//	public List<Object> getDefectiveItemList( @Param("capacityId") Long capacityId);
	@Query(value = "SELECT target FROM `capacity_study` WHERE id=:capacityStudyId",nativeQuery = true)
	public Integer findTargetById(@Param("capacityStudyId") Long capacityStudyId);
	
	public List<CapacityStudy> findAllByActiveTrue();
	
	@Query(value = "SELECT * FROM capacity_study WHERE active=true AND status='complete' and org_id IN (:orgIds)",nativeQuery = true)
	public List<CapacityStudy> findAllCsByOrgIdAndStatusIsComplete(@Param("orgIds") List<Long> orgIds);
	
	@Query(value = "SELECT * FROM capacity_study WHERE active=true AND status='partial' and user_name=:userName",nativeQuery = true)
	public List<CapacityStudy> findAllCsByUserAndStatusIsPartial(@Param("userName") String userName);
	
	@Query(value = "SELECT * FROM capacity_study WHERE active=true and org_id IN (:orgIds)",nativeQuery = true)
	public List<CapacityStudy> findAllByActiveTrueAndOrganization(@Param("orgIds") List<Long> orgIds);
	
	@Query(value = "SELECT organizations.name as performLine,customer.name as buyer,style.name as style,capacity_study.user_name as studyBy,style.id as styleId\r\n" +
			"FROM capacity_study,organizations,order_entity,customer,style  \r\n" + 
			"where  \r\n" + 
			"capacity_study.id=:capacityStudyId\r\n" + 
			"AND\r\n" + 
			"capacity_study.org_id=organizations.id  \r\n" + 
			"AND  \r\n" + 
			"capacity_study.orderentity_id=order_entity.id  \r\n" + 
			"AND  \r\n" + 
			"order_entity.customer_id=customer.id  \r\n" + 
			"AND  \r\n" + 
			"order_entity.style_id=style.id  \r\n" + 
			"AND  \r\n" + 
			"capacity_study.status='partial'",nativeQuery = true)
	public List<Object[]> findExtraInfoOfIncompleteCs(@Param("capacityStudyId") Long capacityStudyId);
	
	@Query(value = "SELECT table2.opbdcount,table1.total  \r\n" + 
			"FROM  \r\n" + 
			"(SELECT COUNT(*) as total, capacity_study.id as id  \r\n" + 
			"FROM capacity_study,order_entity,operation_break_down  \r\n" + 
			"WHERE capacity_study.id=:capacityStudyId  \r\n" + 
			"AND  \r\n" + 
			"capacity_study.orderentity_id=order_entity.id  \r\n" + 
			"AND  \r\n" + 
			"operation_break_down.style_id=order_entity.style_id  \r\n" + 
			")as table1  \r\n" + 
			"LEFT JOIN  \r\n" + 
			"(  \r\n" + 
			"SELECT COUNT(DISTINCT capacity_study_details.operationbreakdown_id) as opbdcount,capacity_study_details.capacitystudy_id as id  \r\n" + 
			"FROM capacity_study_details  \r\n" + 
			"WHERE capacity_study_details.capacitystudy_id =:capacityStudyId  \r\n" + 
			"AND  \r\n" + 
			"capacity_study_details.cycle_time>0  \r\n" + 
			") as table2  \r\n" + 
			"ON table1.id = table2.id",nativeQuery = true)
	public List<Object[]> findPercentageOfComplete(@Param("capacityStudyId") Long capacityStudyId);
	
	@Query(value = "SELECT * FROM capacity_study WHERE (user_name=:userName AND active=true AND status='partial')\r\n" + 
			"OR(shareduser_id=:userName AND active=true AND status='partial')",nativeQuery = true)
	public List<CapacityStudy> findAllCsForIncompleteList(@Param("userName") String userName);

}
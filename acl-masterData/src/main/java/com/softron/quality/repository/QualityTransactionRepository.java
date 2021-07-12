package com.softron.quality.repository;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softron.schema.qualitymodule.entity.QualityTransaction;

@Repository
public interface QualityTransactionRepository extends JpaRepository<QualityTransaction, Long> {

	public List<QualityTransaction> findAllByActiveTrue();

	@Query(value = "SELECT SUM(quality_transaction.sample_size) FROM quality_transaction,quality_type WHERE quality_type.type=1 AND quality_transaction.qualitytype_id=quality_type.id AND quality_transaction.created_at BETWEEN :startDate AND :endDate", nativeQuery = true)
	public Long totalCheck(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "SELECT SUM(sample_size) FROM quality_transaction,quality_type WHERE quality_type.type=1 AND quality_transaction.qualitytype_id=quality_type.id and quality_transaction.created_at BETWEEN :startDate AND :endDate AND check_output='ok'", nativeQuery = true)
	public Long totalQcPass(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "SELECT SUM(sample_size) FROM quality_transaction,quality_type WHERE quality_type.type=1 AND quality_transaction.qualitytype_id=quality_type.id and quality_transaction.created_at BETWEEN :startDate AND :endDate AND check_output='alter'", nativeQuery = true)
	public Long totalAlter(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "SELECT SUM(sample_size) FROM quality_transaction,quality_type WHERE quality_type.type=1 AND quality_transaction.qualitytype_id=quality_type.id and quality_transaction.created_at BETWEEN :startDate AND :endDate AND check_output='reject'", nativeQuery = true)
	public Long totalReject(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "SELECT count(*) as DHU from quality_transaction,quality_defect,quality_type where quality_type.type=1 AND quality_transaction.qualitytype_id=quality_type.id and quality_transaction.id=quality_defect.qualitytransaction_id and quality_transaction.created_at BETWEEN :startDate AND :endDate", nativeQuery = true)
	public Long dhuCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	

	@Query(value = "SELECT SUM(sample_size) FROM quality_transaction WHERE org_id=:orgId AND created_at BETWEEN :startDate AND :endDate ", nativeQuery = true)
	public Long totalCheck(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("orgId") Long orgId);

	@Query(value = "SELECT SUM(sample_size) FROM quality_transaction WHERE org_id=:orgId AND created_at BETWEEN :startDate AND :endDate AND check_output='ok'", nativeQuery = true)
	public Long totalQcPass(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("orgId") Long orgId);

	@Query(value = "SELECT SUM(sample_size) FROM quality_transaction WHERE org_id=:orgId AND created_at BETWEEN :startDate AND :endDate AND check_output='alter'", nativeQuery = true)
	public Long totalAlter(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("orgId") Long orgId);

	@Query(value = "SELECT SUM(sample_size) FROM quality_transaction WHERE org_id=:orgId AND created_at BETWEEN :startDate AND :endDate AND check_output='reject'", nativeQuery = true)
	public Long totalReject(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("orgId") Long orgId);

	@Query(value = "SELECT count(*) as DHU from quality_transaction,quality_defect where quality_transaction.id=quality_defect.qualitytransaction_id and quality_transaction.created_at BETWEEN :startDate AND :endDate AND quality_transaction.org_id=:orgId", nativeQuery = true)
	public Long dhuCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("orgId") Long orgId);

	// @Query(value = "SELECT * FROM quality_transaction,quality_type WHERE
	// quality_transaction.orderentity_id=:orderentityId AND
	// quality_transaction.operation_id=:operationId AND
	// quality_transaction.employee_id=:employeeId AND
	// quality_transaction.section_id=:subSectionId AND
	// quality_transaction.qualitytype_id=quality_type.id and quality_type.type=3
	// order by quality_transaction.created_at desc LIMIT 3",nativeQuery = true)
	// public List<QualityTransaction> getDefectiveItemList(@Param("orderentityId")
	// Long orderentityId,@Param("operationId") Long
	// operationId,@Param("employeeId") Long employeeId,@Param("subSectionId") Long
	// subSectionId);

	@Query(value = "SELECT * FROM quality_transaction,quality_type WHERE quality_transaction.orderentity_id=:orderentityId AND quality_transaction.operation_id=:operationId AND quality_transaction.employee_id=:employeeId AND quality_transaction.org_id=:orgId AND  quality_transaction.qualitytype_id=quality_type.id and quality_type.type=3  order by quality_transaction.created_at desc LIMIT 3", nativeQuery = true)
	public List<QualityTransaction> getDefectiveItemList(@Param("orderentityId") Long orderentityId,
			@Param("operationId") Long operationId, @Param("employeeId") Long employeeId, @Param("orgId") Long orgId);
	
	@Query(value = "SELECT * FROM `quality_transaction` WHERE quality_transaction.org_id =:orgId and quality_transaction.active = 1 ORDER BY quality_transaction.created_at DESC LIMIT 1", nativeQuery = true)
	public QualityTransaction findByOrganization(@Param("orgId") Long orgId);

}

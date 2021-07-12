package com.softron.schema.admin.repository;

import com.softron.schema.admin.entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long>{

	public Optional<FileUpload> findByEmployeeId(Long employeeId);

}

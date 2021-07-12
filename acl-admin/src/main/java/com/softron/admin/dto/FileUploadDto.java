package com.softron.admin.dto;

import lombok.Data;

@Data
public class FileUploadDto {

	public Long id;
	public Long employeeId;
	public String filePath;
}

package com.softron.reporting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportingConfig {

    @Value("${reporting.file.upload.path}")
    private String fileUploadPath;
    
    private final String zipFolder = "zipped/";

    public String getFileUploadPath() {
        return fileUploadPath;
    }

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }
    
    public String getZipFolder() {
    	return this.zipFolder;
    }

}

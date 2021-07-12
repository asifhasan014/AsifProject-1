package com.softron.reporting.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.softron.common.service.StorageService;
import com.softron.reporting.config.ReportingConfig;

@Service
public class ReportingFileService {

    @Autowired
    private ReportingConfig config;

    @Autowired
    private StorageService storageService;

    public String uploadFile(MultipartFile file) {
        return storageService.storeFile(config.getFileUploadPath(), file);
    }

    public Resource downloadFile(String fileName) {
        return storageService.loadFileAsResource(config.getFileUploadPath(), fileName);
    }
    
    public Resource downloadFiles(List<String> files) throws IOException {
    	return storageService.zipFiles(config.getFileUploadPath(), files, config.getZipFolder());
    }

    public void deleteFile(String fileName) {
        storageService.deleteFile(config.getFileUploadPath(), fileName);
    }

}

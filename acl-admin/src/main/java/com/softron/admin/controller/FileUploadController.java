package com.softron.admin.controller;


import com.softron.admin.service.FileUploadService;
import com.softron.common.businessobjects.Response;
import com.softron.common.utils.UrlConstants;
import com.softron.core.annotations.ApiController;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import static com.softron.admin.utils.UrlConstants.FILEUPLOAD;
import static com.softron.admin.utils.UrlConstants.GETFILE;

@ApiController
public class FileUploadController {

	@Value("${legal.file.upload.path}")
	private String FILE_DIRECTORY_PATH;
	
	@Autowired
	FileUploadService fileUploadService;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    //private static final String FILE_DIRECTORY_PATH = "E:\\";

//    @PostMapping(value = "/public/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
//    public String uploadFile(@RequestPart("file") final MultipartFile[] files) throws IOException {
//        LOGGER.info("Passing through file uploading controller...");
//        return processFiles(files);
//    }

    @PostMapping(value = FILEUPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String uploadFile(@RequestPart("file") final MultipartFile[] files) throws IOException {
        LOGGER.info("Passing through file uploading controller...");
        return processFiles(files);
    }
    
    @GetMapping(value = GETFILE)
	public Response get(@RequestParam(value = "employeeId") Long employeeId,@RequestParam(required = false) String type) {

    	if(type == null){
    		type = "test";
    		return fileUploadService.get(employeeId,type);
    	}else{
    		return fileUploadService.get(employeeId,type);
    	}
		
	}

    public String processFiles(MultipartFile[] files) throws IOException {
        final StringBuilder names = new StringBuilder("files: [");
        for (MultipartFile file : files) {
            file.transferTo(new File(FILE_DIRECTORY_PATH + file.getOriginalFilename()));
            LOGGER.info(file.getOriginalFilename());
            names.append(file.getOriginalFilename()).append(" ,");
        }
        return names.append("]").toString();
    }

}

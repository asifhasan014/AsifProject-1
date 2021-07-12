package com.softron.admin.service;

import com.softron.admin.controller.FileUploadController;
import com.softron.common.businessobjects.Response;
import com.softron.common.exceptions.NoRecordExistsException;
import com.softron.common.utils.ResponseUtils;
import com.softron.schema.admin.entity.FileUpload;
import com.softron.schema.admin.repository.FileUploadRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//import com.softron.masterdata.repository.EmployeeRepository;

@Service
public class FileUploadService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	FileUploadRepository fileUploadRepository;
	
	@Value("${legal.file.upload.path}")
	private String FILE_DIRECTORY_PATH;
	
	String root = "FileUpload";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

	public Map<String , String> processFiles(MultipartFile[] files, String folder) throws IOException {

		if(folder == null) {
			UUID uuid = UUID.randomUUID();
			folder = uuid.toString();
		} 
		
		Path dirPathObj = Paths.get(FILE_DIRECTORY_PATH + folder);
		boolean dirExists = Files.exists(dirPathObj);
		if(dirExists) {
			
		} else {
			try {
				Files.createDirectories(dirPathObj);
			} catch (IOException ioExceptionObj) {
				
			}
		}
		
		final StringBuilder names = new StringBuilder("");
		final StringBuilder thumNames = new StringBuilder("");
		
		Map<String , String> fileMap = new HashMap<String, String>();
        for (MultipartFile file : files) {
            file.transferTo(new File(FILE_DIRECTORY_PATH + folder + "/" + file.getOriginalFilename()));
            
            File destinationDir = new File(FILE_DIRECTORY_PATH + folder + "/");
            String imageName = "thum_"+file.getOriginalFilename();
            File thumnail = new File(destinationDir, imageName);
            
            Thumbnails.of(FILE_DIRECTORY_PATH + folder + "/"+file.getOriginalFilename())
                    .size(100, 100)
                    .toFile(thumnail);
            
            LOGGER.info(file.getOriginalFilename());
            names.append(folder).append("/").append(file.getOriginalFilename());
            thumNames.append(folder).append("/").append(thumnail.getName());
            
            fileMap.put("filePath", names.toString());
            fileMap.put("thumbnailPath", thumNames.toString());
        }
        return fileMap;
    }

	public Response get(Long employeeId,String type) {
		try {
			FileUpload fileUpload = fileUploadRepository.findByEmployeeId(employeeId).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, employeeId)));

//			File initialFile = new File(FILE_DIRECTORY_PATH + fileUpload.getFilePath());
//			InputStream targetStream = new FileInputStream(initialFile);
//			String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(IOUtils.toByteArray(targetStream));
			String encodeImage = "";
			String thumb = "thumbnail";
			if(type.equals(thumb)){
				File initialFile = new File(FILE_DIRECTORY_PATH + fileUpload.getThumbnailPath());
				InputStream targetStream = new FileInputStream(initialFile);
				encodeImage = Base64.getEncoder().withoutPadding().encodeToString(IOUtils.toByteArray(targetStream));
			}else{
				File initialFile = new File(FILE_DIRECTORY_PATH + fileUpload.getFilePath());
				InputStream targetStream = new FileInputStream(initialFile);
				encodeImage = Base64.getEncoder().withoutPadding().encodeToString(IOUtils.toByteArray(targetStream));
			}
			return ResponseUtils.getSuccessResponse(HttpStatus.OK, encodeImage, "Image has been retrieved successfully");

		} catch (Exception e) {
			return ResponseUtils.getFailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error occured");
		}
	}
	
	
	
	public String getEmployeeImage(Long employeeId,String type){
		try {
			FileUpload fileUpload = fileUploadRepository.findByEmployeeId(employeeId).orElseThrow(
					() -> new NoRecordExistsException(String.format("%s doesn't exist for id %s", root, employeeId)));
			String encodeImage = "";
			String thumb = "thumbnail";
			if(type.equals(thumb)){
				File initialFile = new File(FILE_DIRECTORY_PATH + fileUpload.getThumbnailPath());
				InputStream targetStream = new FileInputStream(initialFile);
				encodeImage = Base64.getEncoder().withoutPadding().encodeToString(IOUtils.toByteArray(targetStream));
				return encodeImage;
			}else{
				File initialFile = new File(FILE_DIRECTORY_PATH + fileUpload.getFilePath());
				InputStream targetStream = new FileInputStream(initialFile);
				encodeImage = Base64.getEncoder().withoutPadding().encodeToString(IOUtils.toByteArray(targetStream));
				return encodeImage;
			}
		
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}

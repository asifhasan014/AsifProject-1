package com.softron.common.service;

import com.softron.common.exceptions.ACLFileNotFoundException;
import com.softron.common.exceptions.FileStorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StorageService.class);

	private Random random = new Random();

	private static final String ZIP_EXT = ".zip";

	public String storeFile(String fileUploadPath, MultipartFile file) {

		final String fileName = random.nextInt(10) + "_" + file.getOriginalFilename();
		try {
			file.transferTo(new File(fileUploadPath + fileName));
			LOGGER.info("Saving file: {} on path: {}", fileName, fileUploadPath);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + file.getOriginalFilename() + ". Please try again!",
					ex);
		}
		return fileName;
	}

	public List<String> storeFiles(String fileUploadPath, MultipartFile[] files) {
		final List<String> filesNames = new ArrayList<>();
		try {
			for (MultipartFile file : files) {
				file.transferTo(new File(fileUploadPath + file.getOriginalFilename()));
				LOGGER.info(file.getOriginalFilename());
				filesNames.add(file.getOriginalFilename());
			}
		} catch (IOException ex) {
			throw new FileStorageException("Could not store files. Please try again!", ex);
		}
		return filesNames;
	}

	public Resource loadFileAsResource(String fileUploadPath, String fileName) {

		try {
			final Path filePath = getFilePath(fileUploadPath, fileName);
			final Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new ACLFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new ACLFileNotFoundException("File not found " + fileName, ex);
		}
	}

	public void deleteFile(String path, String fileName) {
		final Path filePath = getFilePath(path, fileName);
		try {
			Files.deleteIfExists(filePath);
		} catch (IOException ex) {
			LOGGER.error("Error while deleting file: {} on file path: {}", fileName, path);
			throw new ACLFileNotFoundException("File not found " + fileName, ex);
		}
	}

	public Path getFilePath(String fileUploadPath, String fileName) {
		final Path fileStorageLocation = Paths.get(fileUploadPath).toAbsolutePath().normalize();
		return fileStorageLocation.resolve(fileName).normalize();
	}

	public Resource zipFiles(String fileUploadPath, List<String> fileNames, String zipFolder) throws IOException {
		final Path compressedFile = getFilePath(fileUploadPath + zipFolder, UUID.randomUUID().toString() + ZIP_EXT);
		File zipFile = new File(compressedFile.toAbsolutePath().toString());
		Files.createDirectories(compressedFile.getParent());
		try (FileOutputStream fos = new FileOutputStream(zipFile)) {
			try (ZipOutputStream zipOut = new ZipOutputStream(fos)) {
				fileNames.forEach(fileName -> {
					File fileToZip = new File(getFilePath(fileUploadPath, fileName).toAbsolutePath().toString());
					try (FileInputStream fis = new FileInputStream(fileToZip)) {
						ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
						zipOut.putNextEntry(zipEntry);
						byte[] bytes = new byte[1024];
						int length;
						while ((length = fis.read(bytes)) >= 0) {
							zipOut.write(bytes, 0, length);
						}
					} catch (IOException e) {
						LOGGER.error("Error while compressing file: ", fileName);
					}
				});
			} catch (IOException ex) {
				LOGGER.error("Error while compressing file: ", ex);
			}
		} catch (FileNotFoundException ex) {
			LOGGER.error("Error while compressing file: ", ex);
		}
		try {
			return new UrlResource(compressedFile.toUri());
		} catch (MalformedURLException ex) {
			throw new ACLFileNotFoundException("File not found " + compressedFile.getFileName().toString(), ex);
		}
	}
}

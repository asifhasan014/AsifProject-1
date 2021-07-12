package com.softron.reporting.schedulers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.softron.reporting.config.ReportingConfig;

@Component
public class FileCleanUpScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileCleanUpScheduler.class);

	@Autowired
	private ReportingConfig config;

	private static final int X_MINUTES_MILLIS = 1 * 60 * 1000;

	@Scheduled(fixedDelay = 1000 * 60 * 5, initialDelay = 1000 * 30)
	public void cleanAttachmentCompressedFiles() throws IOException {
		LOGGER.debug("Attachment compress file clean up job triggered.");
		Path path = Paths.get(config.getFileUploadPath()).toAbsolutePath().normalize();
		path = path.resolve(config.getZipFolder()).normalize();
		Files.createDirectories(path);
		File zipFolder = path.toAbsolutePath().toFile();
		for (File file : zipFolder.listFiles()) {
			try {
				long now = System.currentTimeMillis();
				long lastModified = file.lastModified();
				if (lastModified < now - X_MINUTES_MILLIS) {
					LOGGER.debug("Deleting compressed file: {}", file.getName());
					Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
				}
			} catch (IOException ex) {
				LOGGER.error("Error while deleting file [{}] in FileCleanUp Job. Cause: {}", file.getName(),
						ex.getMessage());
			}
		}

	}

}

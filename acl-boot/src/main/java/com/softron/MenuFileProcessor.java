package com.softron;

import java.io.File;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.softron.admin.service.AdminConfigService;
import com.softron.admin.service.MenuGeneratorService;
import com.softron.config.ApplicationStartupConfig;

@Component
public class MenuFileProcessor implements ApplicationListener<ApplicationStartedEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuFileProcessor.class);

	@Autowired
	private ApplicationStartupConfig appStartupConfig;

	@Autowired
	private MenuGeneratorService menuGeneratorService;
	
	@Autowired
	private AdminConfigService adminConfigService;

	boolean updated = true;

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		try {
			if (!updated) {
				LOGGER.info("Starting menu file processing on application startup event.");
				if (appStartupConfig.isGenerateMenu()) {
					LOGGER.info("Reading JSON file.");
					URL url = DBScriptRunner.class.getClassLoader().getResource("json//menu.json");
					menuGeneratorService.generate(new File(url.getPath()));
					adminConfigService.assignAllMenuPermissionToAdminRole();
					updated = true;
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Error while generating menus. {}", ex.getMessage());
		}
	}

}

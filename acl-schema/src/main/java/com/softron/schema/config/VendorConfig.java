package com.softron.schema.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class VendorConfig {
	
	private static Logger LOGGER = LoggerFactory.getLogger(VendorConfig.class);

	@Value("${db.query.native.enabled}")
	private boolean nativeQueryEnabled;
	
	public boolean runNative() {
		LOGGER.trace("Native queries enabled {}", nativeQueryEnabled);
		return nativeQueryEnabled;
	}

}

package com.softron.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@ConfigurationProperties(prefix="app.startup")
@Data
public class ApplicationStartupConfig {

	boolean generateMenu;

	@Bean
	@RequestScope
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}

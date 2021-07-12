package com.softron;

import com.softron.census.configuration.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;


/**
 * Starts the application.
 *
 */

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
@Profile("dev")
public class StartApplication {

    public static void main(String[] args) {

     	SpringApplication.run(StartApplication.class, args);
    }

}


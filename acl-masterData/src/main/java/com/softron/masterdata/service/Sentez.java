package com.softron.masterdata.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


public class Sentez {


		@Value("${spring.datasource2.jdbcUrl}")
		public String jdbcUrl;
		
		//public String jbcUrl="ffhhgf";

		@Value("${spring.datasource2.username}")
		public String username;

		@Value("${spring.datasource2.password}")
		public String password;
		
	
}

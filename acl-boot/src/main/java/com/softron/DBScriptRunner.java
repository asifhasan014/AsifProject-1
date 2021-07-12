package com.softron;

import com.softron.census.configuration.ConfigProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBScriptRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(DBScriptRunner.class);

	@Autowired
	private ConfigProperties configProperties;

	private boolean loaded = true;

	@PostConstruct
	public void init() {
		if (!loaded) {
			Connection connection = null;
			PreparedStatement ps = null;

			String dbUrl = configProperties.getDbUrl();
			String dbUser = configProperties.getUser();
			String dbPass = configProperties.getDbPass();
			String dbDialect = configProperties.getDialect();

			try {
				URL url = DBScriptRunner.class.getClassLoader().getResource("patch//20190425.sql");
				File file = new File(url.getPath());
				Class.forName(dbDialect);
				connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
				if (file != null) {
					ScriptRunner sr = new ScriptRunner(connection);
					Reader reader = new BufferedReader(new FileReader(file.getPath()));
					sr.runScript(reader);

				}

			} catch (ClassNotFoundException | SQLException ex) {
				LOGGER.error("Error while executing db scripts. {}", ex);
			} catch (FileNotFoundException ex) {
				LOGGER.error("DB scripts file not found. {}", ex);
			} finally {
				try {
					if (connection != null && ps != null) {
						connection.close();
						ps.close();
					}
				} catch (SQLException ex) {
					LOGGER.error("Error while closing db connection: {}", ex);
				}
			}
		}
	}

}

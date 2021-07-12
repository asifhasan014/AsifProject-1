package com.softron.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.softron.core.domain.RequestContextData;

public class ActiveModuleScanFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveModuleScanFilter.class);

	private static final String MODULE_ID = "X-MOD-ID";

	@Autowired
	private RequestContextData requestContextData;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String moduleId = request.getHeader(MODULE_ID);
		String method = request.getMethod();
		String uri = request.getRequestURI();
		LOGGER.debug("{}: {} -> moduleId: {}", method, uri, moduleId);
		setModuleId(moduleId);
		filterChain.doFilter(request, response);
	}

	private void setModuleId(String moduleId) {
		try {
			requestContextData.setModuleId(Long.valueOf(moduleId));
		} catch (NumberFormatException ex) {
			LOGGER.trace("Module id couldn't be parsed to long: {}", moduleId);
		} catch(Exception ex) {
			LOGGER.error("Error: {}", ex);
		}
	}

}

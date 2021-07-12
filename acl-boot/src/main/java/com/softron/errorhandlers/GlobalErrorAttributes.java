package com.softron.errorhandlers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorAttributes.class);

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
        // map.put("status", HttpStatus.BAD_REQUEST); we can change the
        // http_status here.
        LOGGER.error("Error while processing request: {}", map.get("message"));
        //map.put("message", "Please contact Administrator!");
        return map;
    }
}

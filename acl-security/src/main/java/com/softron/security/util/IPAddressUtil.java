package com.softron.security.util;

import javax.servlet.http.HttpServletRequest;

public class IPAddressUtil {
	
	private static final String X_FORWARDED_FOR = "X-Forwarded-For";

    private IPAddressUtil() {

    }

    public static String getIPAddress(HttpServletRequest request) {
        final String xfHeader = request.getHeader(X_FORWARDED_FOR);
        String ip = null;
        if (xfHeader == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = xfHeader.split(",")[0];
        }
        return ip;
    }
    
    public static String getRemoteAddress(HttpServletRequest request) {
    	return request.getRemoteAddr();
    }
    
    public static String getRemoteHost(HttpServletRequest request) {
    	return request.getRemoteHost();
    }
    
    private static int getRemotePort(HttpServletRequest request) {
    	return request.getRemotePort();
    }
    
    public static String getRequestInfo(HttpServletRequest request) {
    	return new StringBuilder(X_FORWARDED_FOR)
    			.append(": ")
    			.append(getIPAddress(request))
    			.append(", Remote Address: ")
    			.append(getRemoteAddress(request))
    			.append(", Remote Host: ")
    			.append(getRemoteHost(request))
    			.append(", Port: ")
    			.append(getRemotePort(request))
    			.toString();
    }

}

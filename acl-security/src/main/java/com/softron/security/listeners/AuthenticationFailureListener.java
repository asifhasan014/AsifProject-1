package com.softron.security.listeners;

import com.softron.security.service.LoginAttemptService;
import com.softron.security.util.IPAddressUtil;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFailureListener.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginAttemptService loginAttemptService;

    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        String ip = IPAddressUtil.getIPAddress(request);
        LOGGER.error("Authentication failed from IP address: [{}]", IPAddressUtil.getRequestInfo(request));
        loginAttemptService.loginFailed(ip);
    }
}

package com.softron.security.listeners;

import com.softron.security.service.LoginAttemptService;
import com.softron.security.util.IPAddressUtil;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationSuccessEventListener.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginAttemptService loginAttemptService;

    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        String ip = IPAddressUtil.getIPAddress(request);
        LOGGER.info("A login attempt was successfull from : {}", IPAddressUtil.getRequestInfo(request));
        loginAttemptService.loginSucceeded(ip);
    }
}

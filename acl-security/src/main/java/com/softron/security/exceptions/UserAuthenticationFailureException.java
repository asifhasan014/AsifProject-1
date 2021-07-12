package com.softron.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserAuthenticationFailureException extends AuthenticationException {

    private static final long serialVersionUID = -1054420812669806127L;

    public UserAuthenticationFailureException(String message) {
        super(message);
    }

}
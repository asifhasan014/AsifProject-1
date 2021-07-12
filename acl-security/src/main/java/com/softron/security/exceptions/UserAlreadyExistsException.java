package com.softron.security.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 9005508298912270107L;

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
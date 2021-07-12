package com.softron.common.exceptions;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2410618128864372369L;

    public UserNotFoundException(String message) {
        super(message);
    }

}

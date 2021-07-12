package com.softron.common.exceptions;

public class ACLFileNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1654161494716202806L;

    public ACLFileNotFoundException(String message) {
        super(message);
    }
    
    public ACLFileNotFoundException(String message, Throwable th) {
        super(message, th);
    }

}

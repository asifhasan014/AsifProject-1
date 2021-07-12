package com.softron.common.exceptions;

public class FileStorageException extends RuntimeException {

    private static final long serialVersionUID = 7906463082055875854L;

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable th) {
        super(message, th);
    }
}

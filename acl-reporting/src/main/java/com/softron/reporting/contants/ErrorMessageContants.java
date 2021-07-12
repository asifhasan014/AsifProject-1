package com.softron.reporting.contants;

public enum ErrorMessageContants {

    REPORT_NOT_FOUND_EX("No report exists for report Id #"),

    RECORD_NOT_FOUND_EX("Record not found for #");

    private final String message;

    private ErrorMessageContants(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return this.getMessage();
    }

}

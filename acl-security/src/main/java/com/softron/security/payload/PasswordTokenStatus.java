package com.softron.security.payload;

public enum PasswordTokenStatus {

	INVALID("Password reset token invalid"), EXPIRED("Password reset token expired"), OK("OK");

	private final String status;

	private PasswordTokenStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}

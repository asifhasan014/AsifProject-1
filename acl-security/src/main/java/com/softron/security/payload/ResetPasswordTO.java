package com.softron.security.payload;

import javax.validation.constraints.NotBlank;

import com.softron.security.annotations.FieldsValueMatch;
import com.softron.security.annotations.PasswordPolicy;

import lombok.Data;

@FieldsValueMatch.List({
		@FieldsValueMatch(field = "password", fieldMatch = "verifyPassword", message = "Passwords do not match!") })
@Data
public class ResetPasswordTO {

	@NotBlank(message = "Username should not be blank.")
	private String username;

	@NotBlank(message = "Password should not be blank.")
	@PasswordPolicy
	private String password;

	@NotBlank(message = "Matching password should not be blank.")
	@PasswordPolicy
	private String verifyPassword;
	
	@NotBlank(message = "Token should not be blank.")
	private String token;

}

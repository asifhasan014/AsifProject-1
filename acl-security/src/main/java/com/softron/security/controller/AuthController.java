package com.softron.security.controller;

import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.softron.core.annotations.ApiController;
import com.softron.security.config.auth.token.JwtTokenProvider;
import com.softron.security.exceptions.UserNotFoundException;
import com.softron.security.payload.ApiResponse;
import com.softron.security.payload.JwtAuthenticationResponse;
import com.softron.security.payload.LoginRequest;
import com.softron.security.payload.PasswordTokenStatus;
import com.softron.security.payload.ResetPasswordTO;
import com.softron.security.service.PasswordResetService;
import com.softron.security.service.UserDataService;

@ApiController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserDataService userService;

	@Autowired
	private PasswordResetService passwordResetService;

	@PostMapping("/auth/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	@PostMapping("/auth/password/forgot" + ID_PATH_VAR)
	public ResponseEntity<ApiResponse> forgotPassword(HttpServletRequest request, @PathVariable(ID) String userName) {
		return ResponseEntity.ok(passwordResetService.forgotPassword(request, userName));
	}

	@PostMapping("/auth/password/validate-token")
	public ResponseEntity<ApiResponse> validateToken(@RequestParam("id") String userId,
			@RequestParam("token") String token) {
		ApiResponse response = new ApiResponse();
		PasswordTokenStatus tokenStatus = passwordResetService.validatePasswordResetToken(userId, token);
		response.addMessage(tokenStatus.getStatus());
		if (tokenStatus == PasswordTokenStatus.OK) {
			response.setSuccess(true);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/auth/password/reset")
	public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordTO resetPasswordTO) {
		ApiResponse resp = new ApiResponse(false);
		try {
			PasswordTokenStatus tokenStatus = passwordResetService
					.validatePasswordResetToken(resetPasswordTO.getUsername(), resetPasswordTO.getToken());
			if (tokenStatus == PasswordTokenStatus.OK) {
				userService.resetPassword(resetPasswordTO);
				passwordResetService.reedemToken(resetPasswordTO.getUsername(), resetPasswordTO.getToken());
				resp.setSuccess(true);
			}
			resp.addMessage(tokenStatus.getStatus());
		} catch (UserNotFoundException ex) {
			resp.addMessage(ex.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@DeleteMapping("/auth/logout")
	public ResponseEntity<?> logout() {
		return ResponseEntity.ok().build();
	}
}

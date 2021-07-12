package com.softron.security.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softron.mailer.domain.SimpleEmail;
import com.softron.mailer.exeptions.EmailException;
import com.softron.mailer.service.EmailService;
import com.softron.schema.admin.entity.PasswordResetToken;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.repository.PasswordResetTokenRepository;
import com.softron.security.exceptions.UserNotFoundException;
import com.softron.security.payload.ApiResponse;
import com.softron.security.payload.PasswordTokenStatus;

@Service
public class PasswordResetService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetService.class);

	@Autowired
	private UserDataService userDataService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordResetTokenRepository tokenRespository;

	public ApiResponse forgotPassword(HttpServletRequest request, String userId) {
		final ApiResponse response = new ApiResponse();
		try {
			UserEntity user = userDataService.getUserByNameOrEmail(userId);
			PasswordResetToken token = createToken(user);
			tokenRespository.save(token);
			String emailMessage = constructPasswordResetEmail(user.getFullName(),
					constructResetTokenUrl(getAppUrl(request), user.getUserName(), token.getToken()));
			SimpleEmail email = new SimpleEmail(Arrays.asList(user.getEmail()), "MoPA | Reset Password", emailMessage);
			emailService.sendEmail(email);
			response.setSuccess(true);
			response.addMessage(
					"Password resent link sent successfully on user email. Please check your email for more instructions.");
		} catch (UserNotFoundException ex) {
			response.addMessage(ex.getMessage());
			LOGGER.warn(ex.getMessage());
		} catch (EmailException ex) {
			response.addMessage("System unable to sent emails. Please contact support team.");
			LOGGER.error("Error while sending email: {}", ex);
		}
		return response;
	}

	public PasswordTokenStatus validatePasswordResetToken(String userId, String token) {
		return tokenRespository.findByToken(token).map(validateToken(userId)).get();
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void reedemToken(String userId, String token) {
		tokenRespository.reedemToken(userId, token);
	}

	private Function<PasswordResetToken, PasswordTokenStatus> validateToken(String userId) {
		return token -> {
			if (!token.getUser().getUserName().equals(userId)) {
				return PasswordTokenStatus.INVALID;
			}
			if (token.isTokenRedeemed()) {
				return PasswordTokenStatus.EXPIRED;
			}
			Calendar cal = Calendar.getInstance();
			if ((token.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
				return PasswordTokenStatus.EXPIRED;
			}
			return PasswordTokenStatus.OK;
		};
	}

	private String constructPasswordResetEmail(String fullName, String resetUrl) {
		StringBuilder builder = new StringBuilder();
		builder.append("Dear ").append(fullName).append(", \r\r");
		builder.append("You recently requested to reset you password for MoPA. Click the below link to reset it.");
		builder.append("\r\r");
		builder.append(resetUrl);
		builder.append("\r\r");
		builder.append(
				"If you didn't request a password reset, please ignore this email or reply to let us know. This password reset link is valid only for next 30 minutes.");
		builder.append("\r\r");
		builder.append("Thanks,\r");
		builder.append("MoPA Team");
		return builder.toString();
	}

	private String getAppUrl(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

	private String constructResetTokenUrl(String contextPath, String userId, String token) {
		return contextPath + "/#/auth/reset-password?id=" + userId + "&token=" + token;
	}

	protected PasswordResetToken createToken(final UserEntity user) {
		PasswordResetToken token = new PasswordResetToken();
		token.setToken(UUID.randomUUID().toString());
		token.setUser(user);
		token.setExpiryDate(30);
		return token;

	}

}

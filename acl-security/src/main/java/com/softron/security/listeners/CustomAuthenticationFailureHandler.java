package com.softron.security.listeners;

import com.softron.security.exceptions.UserAuthenticationFailureException;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private MessageSource messages;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception)
            throws IOException, ServletException {

        super.onAuthenticationFailure(request, response, exception);

        final Locale locale = localeResolver.resolveLocale(request);

        if ((exception instanceof SessionAuthenticationException)) {
            response.sendError(401, getMessage("multiple.session.error", locale));
        } else if ((exception instanceof BadCredentialsException)) {
            response.sendError(401, getMessage("message.badCredentials", locale));
        } else if (exception instanceof DisabledException && "No Roles Assigned to User.".equals(exception.getMessage())) {
            response.sendError(401, getMessage("auth.message.disabled", locale));
        } else if ((exception instanceof CredentialsExpiredException)) {
            response.sendError(401, getMessage("auth.message.expired", locale));
        } else if ((exception instanceof UserAuthenticationFailureException)) {
            response.sendError(401, getMessage("auth.message.blocked", locale));
        } else {
            response.sendError(500, getMessage("internal.server.error", locale));
        }
    }

    private String getMessage(String key, Locale locale) {
        return messages.getMessage(key, null, locale);
    }
}

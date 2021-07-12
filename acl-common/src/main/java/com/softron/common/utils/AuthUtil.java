package com.softron.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthUtil {

    private AuthUtil() {
    }

    public static Authentication getAuthorization() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getUserName() {
        return getAuthorization().getName();
    }

    public static boolean isAuthenticated() {
        return getAuthorization().isAuthenticated();
    }

}

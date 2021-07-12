package com.softron.security.service;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch(NullPointerException ex) {
            return Optional.of("SYSTEM");
        }
    }

}

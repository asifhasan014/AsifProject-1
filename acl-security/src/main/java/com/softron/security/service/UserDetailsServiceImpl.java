package com.softron.security.service;

import com.softron.schema.admin.entity.Privilege;
import com.softron.schema.admin.entity.Role;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.schema.admin.repository.UserRepository;
import com.softron.security.domain.UserPrincipal;
import com.softron.security.exceptions.UserAuthenticationFailureException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * UserDetailsService interface implementation.
 *
 * @author Mozahid
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private static final String ROLE_PREFIX = "ROLE_";

    /**
     * Injecting UserRepository instance.
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        final String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            LOGGER.error("User [{}] is blocked for login from IP address: [{}]", username, ip);
            throw new UserAuthenticationFailureException("Your IP address is blocked for 1 day. Please contact your Administrator.");
        }
        LOGGER.debug("User [{}] tried to login from IP address: [{}]", username, ip);
        final UserEntity user = userRepository.findByUserNameOrEmail(username.toLowerCase()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist."));
        return new UserPrincipal(user, getAuthorities(user.getRoles()));
    }

    /**
     * 
     * Method to get {@code GrantedAuthority} from user roles.
     * 
     * @param roles
     *            -Collection<Role>
     * @return Collection<? extends GrantedAuthority>
     */
    protected Collection<GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        final Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getName())));
        return authorities;
        // final Collection<Privilege> privileges =
        // roles.stream().map(Role::getPrivileges).flatMap(Collection::stream).collect(Collectors.toSet());
        // return getGrantedAuthorities(privileges);
    }

    /**
     * 
     * Method to convert Collection<Privilege> to Collection<GrantedAuthority>.
     * 
     * @param privileges
     *            - Collection<Privilege>.
     * @return Collection<GrantedAuthority>
     */
    protected Collection<GrantedAuthority> getGrantedAuthorities(final Collection<Privilege> privileges) {
        final Set<GrantedAuthority> authorities = new HashSet<>();
        privileges.forEach(privilege -> authorities.add(new SimpleGrantedAuthority(privilege.getName())));
        return authorities;
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}

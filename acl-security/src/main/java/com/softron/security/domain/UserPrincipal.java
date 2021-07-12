package com.softron.security.domain;

import com.softron.schema.admin.entity.Module;
import com.softron.schema.admin.entity.Organization;
import com.softron.schema.admin.entity.UserEntity;
import com.softron.security.payload.AssignedModuleTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 
 * Custom User object for holding user details in authentication context.
 *
 * @author Mozahid
 * @version 1.0
 */
public class UserPrincipal extends User {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -7221307130153289097L;

    private String fullName;

    private String email;

    private String govtId;

    private String mobile;
    
    private Long orgId;

    private List<AssignedModuleTO> modules;

    /**
     * User information last updated time.
     */
    private LocalDate lastUpdatedOn;

    /**
     * 
     * Constructor.
     *
     * @param username
     * @param password
     * @param enabled
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param accountNonLocked
     * @param authorities
     */
    public UserPrincipal(final String username, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
            final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    /**
     * 
     * Constructor.
     *
     * @param username
     * @param password
     * @param authorities
     * @param firstName
     * @param lastName
     * @param email
     * @param realm
     */
    public UserPrincipal(final String username, final String password, final Collection<? extends GrantedAuthority> authorities, final String fullName, final String email) {
        super(username, password, authorities);
        this.fullName = fullName;
        this.email = email;
    }

    /**
     * 
     * Constructor.
     *
     * @param user
     * @param authorities
     */
    public UserPrincipal(final UserEntity user, final Collection<? extends GrantedAuthority> authorities) {
        super(user.getUserName(), user.getPassword(), authorities);
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.govtId = user.getGovtId();
        this.mobile = user.getMobile();
        this.orgId = user.getOrganization().getId();
        this.modules = mapModules(user.getAssignedModules());
        this.lastUpdatedOn = user.getLastUpdatedOn();
    }

    private List<AssignedModuleTO> mapModules(final Collection<Module> assignedModules) {
        final List<AssignedModuleTO> mds = new ArrayList<>();
        assignedModules.stream().forEach(m -> mds.add(AssignedModuleTO.from(m.getId(), m.getName(), m.getContextPath(), m.getImage())));
        Collections.sort(mds, (o, o1)-> o.getName().compareTo(o1.getName()));
        return mds;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGovtId() {
        return govtId;
    }

    public void setGovtId(String govtId) {
        this.govtId = govtId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<AssignedModuleTO> getModules() {
        return modules;
    }

    public void setModules(List<AssignedModuleTO> modules) {
        this.modules = modules;
    }

    public LocalDate getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(LocalDate lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

   

	

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserPrincipal other = (UserPrincipal) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }

}

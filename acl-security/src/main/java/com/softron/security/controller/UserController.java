package com.softron.security.controller;

import com.softron.core.annotations.ApiController;
import com.softron.security.domain.UserPrincipal;
import com.softron.security.payload.ApiError;
import com.softron.security.payload.AssignedModuleTO;
import com.softron.security.payload.ChangePasswordTO;
import com.softron.security.payload.ResetPasswordTO;
import com.softron.security.payload.UserTO;
import com.softron.security.service.UserDataService;

import static com.softron.core.constants.AdminApiConstants.USERS_ENDPOINT;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ApiConstants.ID_PATH_VAR;

import java.security.Principal;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiController
public class UserController {

    @Autowired
    private UserDataService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping(value = USERS_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public List<UserTO> getAllUsers(final Principal principal) {
    	final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
        return userService.getAllUsers(user.getOrgId());
    }

    @GetMapping(value = USERS_ENDPOINT + "/modules", produces = EXTERNAL_MEDIA_TYPE)
    public List<AssignedModuleTO> getUserModules(final Principal principal) {
        final UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(principal.getName());
        return user.getModules();
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping(value = USERS_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserTO userTO) {
        if (userTO.isEditing()) {
            userService.updateUser(userTO);
        } else {
            userService.saveUser(userTO);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = USERS_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public UserTO getUserById(@PathVariable(ID) String userName) {
        return userService.getUser(userName);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping(value = USERS_ENDPOINT + ID_PATH_VAR, produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<Void> deleteUser(@PathVariable(ID) String id) {
        userService.deleteByUserName(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = USERS_ENDPOINT + "/change-password", produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordTO passwordRequest) {
        if (userService.changePassword(passwordRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, "Validation error.", "Current password do not match."));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping(value = USERS_ENDPOINT + "/reset-password", produces = EXTERNAL_MEDIA_TYPE)
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordTO passwordRequest) {
        userService.resetPassword(passwordRequest);
        return ResponseEntity.ok().build();
    }

}

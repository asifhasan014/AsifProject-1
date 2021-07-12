package com.softron.security.payload;

import com.softron.security.annotations.FieldsValueMatch;
import com.softron.security.annotations.PasswordPolicy;

import javax.validation.constraints.NotBlank;

@FieldsValueMatch.List(
    { @FieldsValueMatch(field = "password", fieldMatch = "verifyPassword", message = "Passwords do not match!") })
public class ChangePasswordTO {

    @NotBlank(message = "Old password should not be blank.")
    private String oldPassword;

    @NotBlank(message = "New password should not be blank.")
    @PasswordPolicy
    private String password;

    @NotBlank(message = "Matching password should not be blank.")
    @PasswordPolicy
    private String verifyPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

}

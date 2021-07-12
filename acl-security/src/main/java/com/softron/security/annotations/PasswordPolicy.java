package com.softron.security.annotations;

import com.softron.security.validators.PasswordPolicyValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(
    { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordPolicyValidator.class)
public @interface PasswordPolicy {
    
    String message() default "{password.validation.error.short.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package ua.shtaiier.phonecontacts.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidateEmailOrPhoneNumbersValidator.class)
public @interface ValidateData {

    String message() default "Invalid contact information";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    ValidationType type() default ValidationType.EMAIL;

}

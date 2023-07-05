package ua.shtaiier.phonecontacts.validation.formatValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidateEmailOrPhoneNumbersValidator implements ConstraintValidator<ValidateData, List<String>> {

    private ValidationType validationType;

    @Override
    public void initialize(ValidateData constraintAnnotation) {
        validationType = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null) {
            return true;
        }
        return switch (validationType) {
            case EMAIL -> validateEmails(values);
            case PHONE_NUMBERS -> validatePhoneNumbers(values);
        };
    }

    private boolean validateEmails(List<String> emails) {
        return emails.stream()
                .allMatch(email -> email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
    }

    private boolean validatePhoneNumbers(List<String> phoneNumbers) {
        return phoneNumbers.stream()
                .allMatch(phoneNumber -> phoneNumber.matches("^\\+?\\d+$"));
    }

}

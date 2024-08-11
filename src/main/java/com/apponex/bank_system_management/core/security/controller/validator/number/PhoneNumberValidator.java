package com.apponex.bank_system_management.core.security.controller.validator.number;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        // Initialization code if needed
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return true; // Let @NotNull handle null checks
        }
        try {
            // Assume phone number is in international format with a leading '+'
            PhoneNumber number = phoneNumberUtil.parse(phoneNumber, null);
            return phoneNumberUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }
}

package org.nbu.transport.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.TreeSet;

public class ValidCompanyNameValidator implements ConstraintValidator<ValidCompanyName, String> {

    private final Set<String> reservedWords = new TreeSet<>();

    @Override
    public void initialize(ValidCompanyName constraintAnnotation) {
        // Reserved words that cannot be used in company names
        reservedWords.addAll(Set.of("Test", "Demo", "Temp", "Fake", "Sample"));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Let @NotBlank handle null/empty validation
        }
        return reservedWords.stream().noneMatch(value::contains);
    }
}


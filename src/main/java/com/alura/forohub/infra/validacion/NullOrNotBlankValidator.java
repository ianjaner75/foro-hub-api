package com.alura.forohub.infra.validacion;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // válido si es null (no se actualiza) o si tiene texto no-blanco
        return value == null || !value.trim().isEmpty();
    }
}

package com.alura.forohub.infra.validacion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NullOrNotBlankValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NullOrNotBlank {
    String message() default "debe ser nulo o no estar en blanco";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

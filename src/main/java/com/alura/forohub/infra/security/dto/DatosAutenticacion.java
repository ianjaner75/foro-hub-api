package com.alura.forohub.infra.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacion(
        @NotBlank @Email String email,
        @NotBlank String contrasena
) {}

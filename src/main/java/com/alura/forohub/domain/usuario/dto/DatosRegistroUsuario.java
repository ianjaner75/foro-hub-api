// com.alura.forohub.domain.usuario.dto
package com.alura.forohub.domain.usuario.dto;

import jakarta.validation.constraints.*;

public record DatosRegistroUsuario(
        @NotBlank String nombre,
        @NotBlank @Email String email,
        @NotBlank String contrasena
) {}


package com.alura.forohub.domain.topico.dto;

import com.alura.forohub.domain.topico.StatusTopico;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        @NotNull Long autorId,
        @NotNull Long cursoId,
        @NotBlank String titulo,
        @NotBlank String mensaje
) {}

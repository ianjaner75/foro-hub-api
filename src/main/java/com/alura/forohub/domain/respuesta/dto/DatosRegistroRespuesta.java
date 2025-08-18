package com.alura.forohub.domain.respuesta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record DatosRegistroRespuesta(
        @NotNull Long topicoId,
        @NotBlank String mensaje
) {}

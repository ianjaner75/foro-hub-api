package com.alura.forohub.domain.respuesta.dto;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        Long autorId,
        Long topicoId
) {}
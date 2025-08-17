package com.alura.forohub.domain.topico.dto;

import com.alura.forohub.domain.topico.StatusTopico;
import com.alura.forohub.infra.validacion.NullOrNotBlank;

public record DatosActualizacionTopico(
        @NullOrNotBlank String titulo,
        @NullOrNotBlank String mensaje,
        StatusTopico status,
        Long cursoId
) {}

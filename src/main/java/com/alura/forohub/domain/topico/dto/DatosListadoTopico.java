    package com.alura.forohub.domain.topico.dto;

    import com.alura.forohub.domain.topico.StatusTopico;
    import java.time.LocalDateTime;

    public record DatosListadoTopico(
            Long id,
            String titulo,
            String mensaje,
            LocalDateTime fechaCreacion,
            StatusTopico status,
            Long autorId,
            Long cursoId
    ) {}

package com.alura.forohub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Duplicado por TÍTULO (en cualquier registro)
    boolean existsByTitulo(String titulo);

    // Duplicado por TÍTULO excluyendo un id (para update)
    boolean existsByTituloAndIdNot(String titulo, Long id);

    // Duplicado por MENSAJE (en cualquier registro)
    boolean existsByMensaje(String mensaje);

    // Duplicado por MENSAJE excluyendo un id (para update)
    boolean existsByMensajeAndIdNot(String mensaje, Long id);
}

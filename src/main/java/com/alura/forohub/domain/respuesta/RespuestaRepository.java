// com.alura.forohub.domain.respuesta.RespuestaRepository
package com.alura.forohub.domain.respuesta;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Page<Respuesta> findByTopico_Id(Long topicoId, Pageable pageable);
}

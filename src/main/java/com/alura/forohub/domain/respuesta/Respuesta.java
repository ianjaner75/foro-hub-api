// com.alura.forohub.domain.respuesta.Respuesta
package com.alura.forohub.domain.respuesta;

import com.alura.forohub.domain.topico.Topico;
import com.alura.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="respuestas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of="id")
public class Respuesta {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Lob @Column(nullable=false)
    private String mensaje;

    @Column(name="fecha_creacion", nullable=false)
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="autor_id", nullable=false)
    private Usuario autor;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="topico_id", nullable=false)
    private Topico topico;

    @PrePersist void pre() { if (fechaCreacion == null) fechaCreacion = LocalDateTime.now(); }
}

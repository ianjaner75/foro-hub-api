package com.alura.forohub.domain.topico;

import com.alura.forohub.domain.curso.Curso;
import com.alura.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    // Quitar @Lob para evitar CLOB. Dejamos TEXT en la BD.
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTopico status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @PrePersist
    void prePersist() {
        if (fechaCreacion == null) fechaCreacion = LocalDateTime.now();
        if (status == null) status = StatusTopico.ABIERTO;
    }

    public void actualizar(String nuevoTitulo, String nuevoMensaje, StatusTopico nuevoStatus, Curso nuevoCurso) {
        if (nuevoTitulo != null && !nuevoTitulo.isBlank()) this.titulo = nuevoTitulo;
        if (nuevoMensaje != null && !nuevoMensaje.isBlank()) this.mensaje = nuevoMensaje;
        if (nuevoStatus != null) this.status = nuevoStatus;
        if (nuevoCurso != null) this.curso = nuevoCurso;
    }
}

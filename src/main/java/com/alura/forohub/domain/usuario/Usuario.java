package com.alura.forohub.domain.usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = "id")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=100)
    private String nombre;

    @Column(nullable=false, unique=true, length=100)
    private String email;

    @Column(nullable=false, length=255)
    private String contrasena;
}

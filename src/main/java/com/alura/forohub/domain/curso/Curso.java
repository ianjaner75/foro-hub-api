package com.alura.forohub.domain.curso;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cursos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = "id")
public class Curso {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=100)
    private String nombre;

    @Column(nullable=false, length=100)
    private String categoria;
}

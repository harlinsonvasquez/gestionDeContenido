package com.example.gestion.de.contenido.RIWI.domain.entities;

import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "student")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50,nullable = false)
    private String name;
    @Column(length = 100,nullable = false)
    private String email;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    // relacion de muchos a uno ya que una clase puede tener muchos estudiantes
    @ManyToOne(fetch = FetchType.LAZY)//carga perezosa para que no se cargue hasta que se necesite
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private ClassEntity classEntity;
    // este metodo se ejecuta antes de que se cree el objeto y toma la hora actual y la fecha de creacion
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

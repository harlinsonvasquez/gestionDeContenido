package com.example.gestion.de.contenido.RIWI.domain.entities;

import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name="class")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(length = 50,nullable = false)
    private String name;
    @Column(nullable = false)
    @Lob
    private String description;
    @Column(nullable = false)
    private LocalDateTime createdAt;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "classEntity",cascade = CascadeType.ALL,orphanRemoval = false,fetch = FetchType.LAZY)
    private List<Student> students;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "classEntity",cascade = CascadeType.ALL,orphanRemoval = false,fetch = FetchType.LAZY)
    private List<Lesson> lessons;
}

package com.example.gestion.de.contenido.RIWI.domain.entities;

import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "lesson")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50,nullable = false)
    private String title;
    @Lob
    private String content;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "lesson",cascade = CascadeType.ALL,orphanRemoval = false,fetch = FetchType.LAZY)
    private List<Multimedia> multimedia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id",referencedColumnName = "id")
    private ClassEntity classEntity;
}

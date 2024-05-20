package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "diet_views")
public class DietView {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diet_view_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "diet_id", nullable = false)
    private Diet diet;

    @Column(nullable = false)
    private LocalDate viewDate;

}


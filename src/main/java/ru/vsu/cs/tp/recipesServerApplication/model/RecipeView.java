package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "recipe_views")
public class RecipeView {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_view_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(nullable = false)
    private LocalDate viewDate;
}

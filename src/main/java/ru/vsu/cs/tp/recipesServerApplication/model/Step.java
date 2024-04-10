package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Basic;
import lombok.Data;


@Entity
@Data
@Table(name = "steps")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private FolkRecipe folkRecipeById;

    @Basic
    @Column(nullable = false)
    private String step;

    @Basic
    @Column(nullable = false)
    private int stepNumber;
}

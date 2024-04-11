package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "recipe_ingredient_measurements")
public class RecipeIngredientMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_ingredient_measurements_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private FolkRecipe folkRecipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private String measurementUnit;
}

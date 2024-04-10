package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Basic;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @Column(nullable = false)
    private Long id;

    @Basic
    @Column(nullable = false, unique = true)
    private String name;

    @Basic
    @Column(name = "possible_units", nullable = false)
    private String possibleUnits;

    @OneToMany(mappedBy = "ingredientById")
    private Collection<RecipeIngredientMeasurement> recipeIngredientMeasurementsByIngredientId;
}

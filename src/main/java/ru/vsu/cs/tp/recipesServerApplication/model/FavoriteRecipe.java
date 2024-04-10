package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Basic;
import lombok.Data;

@Entity
@Data
@Table(name = "favourite_recipe")
public class FavoriteRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userById;

    @Basic
    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Basic
    @Column(name = "recipe_type", nullable = false)
    private String recipeType;
}

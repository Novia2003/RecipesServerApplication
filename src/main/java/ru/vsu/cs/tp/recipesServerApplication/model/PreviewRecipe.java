package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "preview_recipes")
public class PreviewRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "preview_recipes_id_seq")
    private Long id;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Column(name = "recipe_type", nullable = false)
    private RecipeType recipeType;

    @Column(name = "number_views", nullable = false)
    private Long numberViews;
}

package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_id_seq")
    private Long id;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Column(name = "recipe_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RecipeType recipeType;

    @OneToMany(mappedBy = "recipe")
    @ToString.Exclude
    private Collection<RecipeView> recipeViews;

    @OneToMany(mappedBy = "recipe")
    @ToString.Exclude
    private Collection<FavoriteRecipe> favoriteRecipes;
}

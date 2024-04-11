package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "favourite_recipes")
public class FavoriteRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favourite_recipes_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Column(name = "recipe_type", nullable = false)
    private RecipeType recipeType;
}

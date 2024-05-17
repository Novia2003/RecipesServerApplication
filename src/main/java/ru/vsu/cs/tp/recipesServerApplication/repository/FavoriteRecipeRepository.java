package ru.vsu.cs.tp.recipesServerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.tp.recipesServerApplication.model.FavoriteRecipe;

public interface FavoriteRecipeRepository extends JpaRepository<FavoriteRecipe, Long> {
    boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);
}

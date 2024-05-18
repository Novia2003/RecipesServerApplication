package ru.vsu.cs.tp.recipesServerApplication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.tp.recipesServerApplication.model.FavoriteRecipe;
import ru.vsu.cs.tp.recipesServerApplication.model.Recipe;

public interface FavoriteRecipeRepository extends JpaRepository<FavoriteRecipe, Long> {
    boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);

    FavoriteRecipe findByUserIdAndRecipeId(Long userId, Long recipeId);

    Page<Recipe> findRecipesByUserId(Long userId, Pageable pageable);
}

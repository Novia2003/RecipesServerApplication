package ru.vsu.cs.tp.recipesServerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.tp.recipesServerApplication.model.Recipe;
import ru.vsu.cs.tp.recipesServerApplication.model.RecipeType;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findByRecipeIdAndRecipeType(Long recipeId, RecipeType recipeType);
}

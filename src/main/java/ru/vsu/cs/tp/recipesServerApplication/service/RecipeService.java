package ru.vsu.cs.tp.recipesServerApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.model.Recipe;
import ru.vsu.cs.tp.recipesServerApplication.model.RecipeType;
import ru.vsu.cs.tp.recipesServerApplication.repository.RecipeRepository;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public void increaseNumberViews(Long recipeId, RecipeType recipeType) {
        Recipe recipe = recipeRepository.findByRecipeIdAndRecipeType(recipeId, recipeType);

        if (recipe == null) {
            createRecipe(recipeId, recipeType);
        } else {
            recipe.setNumberViews(recipe.getNumberViews() + 1);
            recipeRepository.save(recipe);
        }
    }

    public void createRecipe(Long recipeId, RecipeType recipeType) {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);
        recipe.setRecipeType(recipeType);
        recipe.setNumberViews(1L);
        recipeRepository.save(recipe);
    }
}

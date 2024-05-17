package ru.vsu.cs.tp.recipesServerApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.ingredient.IngredientDTOResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipeAllInfoResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipePreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipesPreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.model.*;
import ru.vsu.cs.tp.recipesServerApplication.repository.FolkRecipeRepository;
import ru.vsu.cs.tp.recipesServerApplication.repository.RecipeIngredientMeasurementRepository;
import ru.vsu.cs.tp.recipesServerApplication.repository.StepRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolkRecipeService {

    private final FolkRecipeRepository folkRecipeRepository;

    private final StepRepository stepRepository;

    private final FavoriteRecipeService favoriteRecipeService;

    private final RecipeService recipeService;

    private final RecipeIngredientMeasurementRepository recipeIngredientMeasurementRepository;

    public RecipeAllInfoResponse getRecipeInformation(Long id, String jwt) {
        FolkRecipe recipe = folkRecipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found"));

        RecipeAllInfoResponse response = new RecipeAllInfoResponse();
        response.setId(recipe.getId());

        recipeService.increaseNumberViews(recipe.getId(), RecipeType.FOLK);

        response.setTitle(recipe.getName());
        response.setDescription(recipe.getDescription());
        response.setReadyInMinutes(recipe.getReadyInMinutes());
        response.setImage(recipe.getImage());

        List<IngredientDTOResponse> extendedIngredients = new ArrayList<>();
        List<RecipeIngredientMeasurement> recipeIngredientMeasurements = recipeIngredientMeasurementRepository.findByFolkRecipeId(recipe.getId());

        for (RecipeIngredientMeasurement recipeIngredientMeasurement : recipeIngredientMeasurements) {
            IngredientDTOResponse ingredientDTOResponse = new IngredientDTOResponse();

            ingredientDTOResponse.setName(recipeIngredientMeasurement.getIngredient().getName());
            ingredientDTOResponse.setAmount(recipeIngredientMeasurement.getQuantity());
            ingredientDTOResponse.setUnit(recipeIngredientMeasurement.getMeasurementUnit());

            extendedIngredients.add(ingredientDTOResponse);
        }

        response.setExtendedIngredients(extendedIngredients);

        List<Step> steps = stepRepository.findByFolkRecipeId(recipe.getId());
        steps.sort(Comparator.comparingInt(Step::getStepNumber));
        List<String> list = new ArrayList<>();

        for (Step step : steps)
            list.add(step.getStep());

        response.setSteps(list);
        response.setIsUserRecipe(true);
        response.setIsFavouriteRecipe(favoriteRecipeService.isRecipeFavourite(jwt, recipe.getId(), RecipeType.FOLK));

        return response;
    }

    public RecipesPreviewResponse getRecipes(String query, String jwt) {
        RecipesPreviewResponse response = new RecipesPreviewResponse();

        List<FolkRecipe> recipes;

        if (query == null)
            recipes = folkRecipeRepository.findByIsApprovedTrue();
        else
            recipes = folkRecipeRepository.findByNameStartsWithIgnoreCaseAndIsApprovedTrue(query);

        List<RecipePreviewResponse> results = new ArrayList<>();
        for (FolkRecipe recipe : recipes) {
            RecipePreviewResponse recipePreviewResponse = new RecipePreviewResponse();

            recipePreviewResponse.setId(recipe.getId());
            recipePreviewResponse.setTitle(recipe.getName());
            recipePreviewResponse.setImage(recipe.getImage());
            recipePreviewResponse.setIsUserRecipe(true);
            recipePreviewResponse.setIsFavouriteRecipe(favoriteRecipeService.isRecipeFavourite(jwt, recipe.getId(), RecipeType.FOLK));

            results.add(recipePreviewResponse);
        }

        response.setResults(results);

        return response;
    }
}

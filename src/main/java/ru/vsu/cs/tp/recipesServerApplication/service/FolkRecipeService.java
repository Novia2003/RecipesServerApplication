package ru.vsu.cs.tp.recipesServerApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.dto.request.ingredient.IngredientDTORequest;
import ru.vsu.cs.tp.recipesServerApplication.dto.request.recipe.UserRecipeRequest;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.ingredient.IngredientDTOResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipeAllInfoResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipePreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipesPreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.model.*;
import ru.vsu.cs.tp.recipesServerApplication.repository.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FolkRecipeService {

    private final FolkRecipeRepository folkRecipeRepository;

    private final StepRepository stepRepository;

    private final FavoriteRecipeService favoriteRecipeService;

    private final RecipeService recipeService;

    private final RecipeIngredientMeasurementRepository recipeIngredientMeasurementRepository;

    private final MealTypeRepository mealTypeRepository;

    private final IngredientRepository ingredientRepository;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final ImageService imageService;

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

    public RecipesPreviewResponse getRecipes(String query, Integer page, Integer number, String jwt) {
        RecipesPreviewResponse response = new RecipesPreviewResponse();

        Page<FolkRecipe> recipes;

        Pageable pageable = PageRequest.of(page, number);

        if (query == null)
            recipes = folkRecipeRepository.findByIsApprovedTrue(pageable);
        else
            recipes = folkRecipeRepository.findByNameStartsWithIgnoreCaseAndIsApprovedTrue(query, pageable);

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

    public RecipesPreviewResponse getFolkRecipes(String jwt, Integer page, Integer number) {
        String email = jwtService.extractUsername(jwt);
        if (email == null)
            return null;

        var user = userRepository.findByEmail(email);
        if (user.isEmpty())
            return null;

        Pageable pageable = PageRequest.of(page, number);

        Page<FolkRecipe> recipes = folkRecipeRepository.findByAuthorId(user.get().getId(), pageable);
        List<RecipePreviewResponse> results = new ArrayList<>();

        for (FolkRecipe recipe: recipes) {
            RecipePreviewResponse result = new RecipePreviewResponse();

            result.setId(recipe.getId());
            result.setTitle(recipe.getName());
            result.setImage(recipe.getImage());
            result.setIsUserRecipe(true);
            result.setIsFavouriteRecipe(favoriteRecipeService.isRecipeFavourite(jwt, recipe.getId(), RecipeType.FOLK));

            results.add(result);
        }

        RecipesPreviewResponse response = new RecipesPreviewResponse();
        response.setResults(results);

        return response;
    }

    public boolean createUserRecipe(UserRecipeRequest recipeRequest, String jwt) {
        FolkRecipe folkRecipe = new FolkRecipe();

        folkRecipe.setName(recipeRequest.getTitle());
        folkRecipe.setDescription(recipeRequest.getDescription());
        folkRecipe.setReadyInMinutes(recipeRequest.getReadyInMinutes());

        String email = jwtService.extractUsername(jwt);
        if (email == null)
            return false;

        var user = userRepository.findByEmail(email);
        if (user.isEmpty())
            return false;

        folkRecipe.setAuthor(user.get());

        folkRecipe.setMealType(mealTypeRepository.findByName(recipeRequest.getCategory()));

        folkRecipe.setImage(imageService.upload(recipeRequest.getImage(), recipeRequest.getImageExtension()));

        folkRecipe.setIsReviewedByAdmin(!recipeRequest.getIsPublish());
        folkRecipe.setIsApproved(false);
        folkRecipeRepository.save(folkRecipe);

        for (IngredientDTORequest request : recipeRequest.getExtendedIngredients()) {
            RecipeIngredientMeasurement recipeIngredientMeasurement = new RecipeIngredientMeasurement();

            recipeIngredientMeasurement.setFolkRecipe(folkRecipe);

            Ingredient ingredient = ingredientRepository.findByName(request.getName());

            if (ingredient == null)
                return false;

            recipeIngredientMeasurement.setIngredient(ingredient);
            recipeIngredientMeasurement.setQuantity(request.getAmount());
            recipeIngredientMeasurement.setMeasurementUnit(request.getUnit());

            recipeIngredientMeasurementRepository.save(recipeIngredientMeasurement);
        }

        for (int i = 0; i < recipeRequest.getSteps().size(); i++) {
            Step step = new Step();

            step.setFolkRecipe(folkRecipe);
            step.setStepNumber(i);
            step.setStep(recipeRequest.getSteps().get(i));

            stepRepository.save(step);
        }

        return true;
    }

    public RecipesPreviewResponse getRecipesToCheck(Integer page, Integer number) {
        Pageable pageable = PageRequest.of(page, number);

        Page<FolkRecipe> recipes = folkRecipeRepository.findByIsReviewedByAdminFalse(pageable);

        List<RecipePreviewResponse> results = new ArrayList<>();

        for (FolkRecipe recipe: recipes) {
            RecipePreviewResponse result = new RecipePreviewResponse();

            result.setId(recipe.getId());
            result.setTitle(recipe.getName());
            result.setImage(recipe.getImage());
            result.setIsUserRecipe(true);
            result.setIsFavouriteRecipe(false);

            results.add(result);
        }

        RecipesPreviewResponse response = new RecipesPreviewResponse();
        response.setResults(results);

        return response;
    }

    public boolean checkRecipe(Long recipeId, Boolean isApproved) {
        Optional<FolkRecipe> folkRecipeOptional = folkRecipeRepository.findById(recipeId);

        if (folkRecipeOptional.isEmpty())
            return false;

        FolkRecipe folkRecipe = folkRecipeOptional.get();

        folkRecipe.setIsReviewedByAdmin(true);
        folkRecipe.setIsApproved(isApproved);

        folkRecipeRepository.save(folkRecipe);

        return true;
    }
}

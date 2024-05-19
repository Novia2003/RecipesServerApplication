package ru.vsu.cs.tp.recipesServerApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsu.cs.tp.recipesServerApplication.configuration.rest.SpoonacularProperties;
import ru.vsu.cs.tp.recipesServerApplication.dto.api.ingredient.IngredientDTO;
import ru.vsu.cs.tp.recipesServerApplication.dto.api.recipe.RandomRecipe;
import ru.vsu.cs.tp.recipesServerApplication.dto.api.recipe.RecipeAllInfo;
import ru.vsu.cs.tp.recipesServerApplication.dto.api.recipe.RecipePreview;
import ru.vsu.cs.tp.recipesServerApplication.dto.api.recipe.RecipesPreview;
import ru.vsu.cs.tp.recipesServerApplication.dto.api.step.StepDTO;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.ingredient.IngredientDTOResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipeAllInfoResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipePreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipesPreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.model.RecipeType;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpoonacularService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final SpoonacularProperties properties;

    private final RecipeService recipeService;

    private final FavoriteRecipeService favoriteRecipeService;

    private final DietService dietService;

    public RecipeAllInfoResponse getRecipeInformation(Long id, String jwt) {
        String resourceUrl = properties.getUrl() + "/recipes/" + id + "/information?apiKey=" + properties.getApiKey();
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        RecipeAllInfo recipe;
        try {
            recipe = objectMapper.readValue(response.getBody(), RecipeAllInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return getRecipeAllInfoResponse(recipe, jwt);
    }

    private RecipeAllInfoResponse getRecipeAllInfoResponse(RecipeAllInfo recipe, String jwt) {
        RecipeAllInfoResponse allInfoResponse = new RecipeAllInfoResponse();
        allInfoResponse.setId(recipe.getId());

        recipeService.increaseNumberViews(recipe.getId(), RecipeType.FROM_API);

        allInfoResponse.setTitle(recipe.getTitle());
        allInfoResponse.setImage(recipe.getImage());
        allInfoResponse.setReadyInMinutes(recipe.getReadyInMinutes());

        List<IngredientDTOResponse> list = new ArrayList<>();
        for (IngredientDTO ingredientDTO: recipe.getExtendedIngredients()) {
            IngredientDTOResponse ingredientDTOResponse = new IngredientDTOResponse();

            ingredientDTOResponse.setName(ingredientDTO.getName());
            ingredientDTOResponse.setAmount(ingredientDTO.getAmount());
            ingredientDTOResponse.setUnit(ingredientDTO.getUnit());

            list.add(ingredientDTOResponse);
        }

        allInfoResponse.setExtendedIngredients(list);

        List<String> steps = new ArrayList<>();
        for (StepDTO stepDTO : recipe.getAnalyzedInstructions().get(0).getSteps())
            steps.add(stepDTO.getStep());

        allInfoResponse.setSteps(steps);
        allInfoResponse.setIsUserRecipe(false);
        allInfoResponse.setIsFavouriteRecipe(favoriteRecipeService.isRecipeFavourite(jwt, recipe.getId(), RecipeType.FROM_API));

        return allInfoResponse;
    }

    public RecipesPreviewResponse getRecipes(String query, String type, String diet, Integer page, Integer number, String jwt) {
        String resourceUrl = properties.getUrl() + "/recipes/complexSearch?apiKey=" + properties.getApiKey();

        if (query != null)
            resourceUrl += "&query=" + query;

        if (type != null)
            resourceUrl += "&type=" + type;

        if (diet != null) {
            resourceUrl += "&diet=" + diet;

            if (query == null && type == null && page == 0)
                dietService.increaseNumberViews(diet);
        }

        resourceUrl += "&offset=" + (page * number);

        resourceUrl += "&number=" + number;

        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);


        ObjectMapper objectMapper = new ObjectMapper();
        RecipesPreview recipes;

        try {
            recipes = objectMapper.readValue(response.getBody(), RecipesPreview.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return getRecipesPreviewResponse(recipes, jwt);
    }

    private RecipesPreviewResponse getRecipesPreviewResponse(RecipesPreview recipes, String jwt) {
        RecipesPreviewResponse recipesPreviewResponse = new RecipesPreviewResponse();
        List<RecipePreviewResponse> list = new ArrayList<>();

        for (RecipePreview result: recipes.getResults()) {
            RecipePreviewResponse recipePreviewResponse = new RecipePreviewResponse();

            recipePreviewResponse.setId(result.getId());
            recipePreviewResponse.setTitle(result.getTitle());
            recipePreviewResponse.setImage(result.getImage());
            recipePreviewResponse.setIsUserRecipe(false);
            recipePreviewResponse.setIsFavouriteRecipe(favoriteRecipeService.isRecipeFavourite(jwt, result.getId(), RecipeType.FROM_API));

            list.add(recipePreviewResponse);
        }

        recipesPreviewResponse.setResults(list);
        return recipesPreviewResponse;
    }

    public RecipePreviewResponse getRandomRecipe() {
        String resourceUrl = properties.getUrl() + "/recipes/random?apiKey=" + properties.getApiKey();
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        RandomRecipe recipe;
        try {
            recipe = objectMapper.readValue(response.getBody(), RandomRecipe.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        RecipePreviewResponse recipePreviewResponse = new RecipePreviewResponse();

        RecipePreview recipePreview = recipe.getRecipes().get(0);

        recipePreviewResponse.setId(recipePreview.getId());
        recipePreviewResponse.setTitle(recipePreview.getTitle());
        recipePreviewResponse.setImage(recipePreview.getImage());
        recipePreviewResponse.setIsUserRecipe(false);
        recipePreviewResponse.setIsFavouriteRecipe(false);

        return recipePreviewResponse;
    }
}

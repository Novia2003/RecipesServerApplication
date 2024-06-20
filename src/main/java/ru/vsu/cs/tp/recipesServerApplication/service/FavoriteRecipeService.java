package ru.vsu.cs.tp.recipesServerApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsu.cs.tp.recipesServerApplication.configuration.rest.SpoonacularProperties;
import ru.vsu.cs.tp.recipesServerApplication.dto.api.recipe.RecipePreview;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipePreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipesPreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.model.*;
import ru.vsu.cs.tp.recipesServerApplication.repository.FavoriteRecipeRepository;
import ru.vsu.cs.tp.recipesServerApplication.repository.FolkRecipeRepository;
import ru.vsu.cs.tp.recipesServerApplication.repository.RecipeRepository;
import ru.vsu.cs.tp.recipesServerApplication.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class FavoriteRecipeService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final SpoonacularProperties properties;

    private final FavoriteRecipeRepository favoriteRecipeRepository;

    private final JwtService jwtService;

    private final RecipeRepository recipeRepository;

    private final UserRepository userRepository;

    private final FolkRecipeRepository folkRecipeRepository;

    public Boolean isRecipeFavourite(String jwt, Long recipe_id, RecipeType type) {
        if (jwt == null)
            return false;

        String email = jwtService.extractUsername(jwt);
        if (email == null)
            return false;

        var user = userRepository.findByEmail(email);
        if (user.isEmpty())
            return false;

        if (user.get().getRole() != Role.USER)
            return false;

        Recipe recipe = recipeRepository.findByRecipeIdAndRecipeType(recipe_id, type);

        if (recipe == null)
            return false;

        return favoriteRecipeRepository.existsByUserIdAndRecipeId(user.get().getId(), recipe.getId());
    }

    public RecipesPreviewResponse getFavouriteRecipes(String jwt, Integer page, Integer number) {
        String email = jwtService.extractUsername(jwt);
        if (email == null)
            return null;

        var user = userRepository.findByEmail(email);
        if (user.isEmpty())
            return null;

        Pageable pageable = PageRequest.of(page, number);

        Page<FavoriteRecipe> favoriteRecipes = favoriteRecipeRepository.findByUserId(user.get().getId(), pageable);
        List<RecipePreviewResponse> results = new ArrayList<>();

        for (FavoriteRecipe favoriteRecipe : favoriteRecipes) {
            Recipe recipe = favoriteRecipe.getRecipe();
            RecipePreviewResponse result;

            if (recipe.getRecipeType() == RecipeType.FROM_API) {
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    return null;
                }

                result = getFavouriteSpoonacularRecipePreview(recipe.getRecipeId());
            }
            else
                result = getFavouriteFolkRecipePreview(recipe.getRecipeId());

            results.add(result);
        }

        RecipesPreviewResponse response = new RecipesPreviewResponse();
        response.setResults(results);

        return response;
    }

    public RecipePreviewResponse getFavouriteSpoonacularRecipePreview(Long id) {
        String resourceUrl = properties.getUrl() + "/recipes/" + id + "/information?apiKey=" + properties.getApiKey();
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        RecipePreview recipe;
        try {
            recipe = objectMapper.readValue(response.getBody(), RecipePreview.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        RecipePreviewResponse recipePreviewResponse = new RecipePreviewResponse();
        recipePreviewResponse.setId(recipe.getId());
        recipePreviewResponse.setTitle(recipe.getTitle());
        recipePreviewResponse.setImage(recipe.getImage());
        recipePreviewResponse.setIsUserRecipe(false);
        recipePreviewResponse.setIsFavouriteRecipe(true);

        return recipePreviewResponse;
    }

    public RecipePreviewResponse getFavouriteFolkRecipePreview(Long id) {
        FolkRecipe recipe = folkRecipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found"));

        RecipePreviewResponse recipePreviewResponse = new RecipePreviewResponse();
        recipePreviewResponse.setId(recipe.getId());
        recipePreviewResponse.setTitle(recipe.getName());
        recipePreviewResponse.setImage(recipe.getImage());
        recipePreviewResponse.setIsUserRecipe(true);
        recipePreviewResponse.setIsFavouriteRecipe(true);

        return recipePreviewResponse;
    }

    public boolean addRecipeToFavorites(Long recipe_id, Boolean isUserRecipe, String jwt) {
        String email = jwtService.extractUsername(jwt);
        if (email == null)
            return false;

        var user = userRepository.findByEmail(email);
        if (user.isEmpty())
            return false;

        RecipeType type = (isUserRecipe) ? RecipeType.FOLK : RecipeType.FROM_API;
        Recipe recipe = recipeRepository.findByRecipeIdAndRecipeType(recipe_id, type);

        if (recipe == null)
            return false;

        FavoriteRecipe favoriteRecipe = new FavoriteRecipe();
        favoriteRecipe.setRecipe(recipe);
        favoriteRecipe.setUser(user.get());

        favoriteRecipeRepository.save(favoriteRecipe);
        return true;
    }

    public boolean removeRecipeFromFavorites(Long recipe_id, Boolean isUserRecipe, String jwt) {
        String email = jwtService.extractUsername(jwt);
        if (email == null)
            return false;

        var user = userRepository.findByEmail(email);
        if (user.isEmpty())
            return false;

        RecipeType type = (isUserRecipe) ? RecipeType.FOLK : RecipeType.FROM_API;
        Recipe recipe = recipeRepository.findByRecipeIdAndRecipeType(recipe_id, type);

        if (recipe == null)
            return false;

        FavoriteRecipe favoriteRecipe = favoriteRecipeRepository.findByUserIdAndRecipeId(user.get().getId(), recipe.getId());

        if (favoriteRecipe == null)
            return false;

        favoriteRecipeRepository.delete(favoriteRecipe);
        return true;
    }
}

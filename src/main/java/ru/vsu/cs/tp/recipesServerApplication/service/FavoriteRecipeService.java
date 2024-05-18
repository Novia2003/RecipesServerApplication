package ru.vsu.cs.tp.recipesServerApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipePreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipesPreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.model.FavoriteRecipe;
import ru.vsu.cs.tp.recipesServerApplication.model.Recipe;
import ru.vsu.cs.tp.recipesServerApplication.model.RecipeType;
import ru.vsu.cs.tp.recipesServerApplication.repository.FavoriteRecipeRepository;
import ru.vsu.cs.tp.recipesServerApplication.repository.RecipeRepository;
import ru.vsu.cs.tp.recipesServerApplication.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteRecipeService {

    private final FavoriteRecipeRepository favoriteRecipeRepository;

    private final JwtService jwtService;

    private final RecipeRepository recipeRepository;

    private final UserRepository userRepository;

    private final FolkRecipeService folkRecipeService;

    private final SpoonacularService spoonacularService;

    public Boolean isRecipeFavourite(String jwt, Long recipe_id, RecipeType type) {
        if (jwt == null)
            return false;

        String email = jwtService.extractUsername(jwt);
        if (email == null)
            return false;

        var user = userRepository.findByEmail(email);
        if (user.isEmpty())
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

        Page<Recipe> recipes = favoriteRecipeRepository.findRecipesByUserId(user.get().getId(), pageable);
        List<RecipePreviewResponse> results = new ArrayList<>();

        for (Recipe recipe: recipes) {
            RecipePreviewResponse result;

            if (recipe.getRecipeType() == RecipeType.FROM_API)
                result = spoonacularService.getFavouriteRecipePreview(recipe.getRecipeId());
            else
                result = folkRecipeService.getFavouriteRecipePreview(recipe.getRecipeId());

            results.add(result);
        }

        RecipesPreviewResponse response = new RecipesPreviewResponse();
        response.setResults(results);

        return response;
    }

    public boolean addRecipeToFavorites(Long recipe_id, Boolean isUserRecipes, String jwt) {
        String email = jwtService.extractUsername(jwt);
        if (email == null)
            return false;

        var user = userRepository.findByEmail(email);
        if (user.isEmpty())
            return false;

        RecipeType type = (isUserRecipes) ? RecipeType.FOLK : RecipeType.FROM_API;
        Recipe recipe = recipeRepository.findByRecipeIdAndRecipeType(recipe_id, type);

        if (recipe == null)
            return false;

        FavoriteRecipe favoriteRecipe = new FavoriteRecipe();
        favoriteRecipe.setRecipe(recipe);
        favoriteRecipe.setUser(user.get());

        favoriteRecipeRepository.save(favoriteRecipe);
        return true;
    }

    public boolean removeRecipeFromFavorites(Long recipe_id, Boolean isUserRecipes, String jwt) {
        String email = jwtService.extractUsername(jwt);
        if (email == null)
            return false;

        var user = userRepository.findByEmail(email);
        if (user.isEmpty())
            return false;

        RecipeType type = (isUserRecipes) ? RecipeType.FOLK : RecipeType.FROM_API;
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

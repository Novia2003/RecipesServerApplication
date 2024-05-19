package ru.vsu.cs.tp.recipesServerApplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipesPreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.service.FavoriteRecipeService;

@RestController
@RequestMapping("/api/v1/favouriteRecipes")
@RequiredArgsConstructor
public class FavoriteRecipeController {

    private final FavoriteRecipeService favoriteRecipeService;

    @GetMapping("/")
    public ResponseEntity<RecipesPreviewResponse> getFavouriteRecipes(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer number,
            @RequestHeader(name="token") String jwt
    ) {
        RecipesPreviewResponse recipes = favoriteRecipeService.getFavouriteRecipes(jwt, page, number);

        if (recipes == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recipes);
    }

    @PostMapping("/{recipeId}")
    public ResponseEntity<?> addRecipeToFavorites(
            @PathVariable("recipeId") Long recipeId,
            @RequestHeader(name="token") String jwt,
            @RequestParam Boolean isUserRecipe
    ) {
        boolean result = favoriteRecipeService.addRecipeToFavorites(recipeId, isUserRecipe, jwt);

        if (!result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add recipe to favorites");
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<?> removeRecipeFromFavorites(
            @PathVariable("recipeId") Long recipeId,
            @RequestHeader(name="token") String jwt,
            @RequestParam Boolean isUserRecipe
    ) {
        boolean result = favoriteRecipeService.removeRecipeFromFavorites(recipeId, isUserRecipe, jwt);

        if (!result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to remove recipe from favorites");
        }

        return ResponseEntity.ok().build();
    }
}

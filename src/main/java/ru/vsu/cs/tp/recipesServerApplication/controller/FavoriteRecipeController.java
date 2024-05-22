package ru.vsu.cs.tp.recipesServerApplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipesPreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.service.FavoriteRecipeService;

@RestController
@RequestMapping("/api/v1/favouriteRecipes")
@RequiredArgsConstructor
@Tag(name = "FavoriteRecipeController", description = "Functions for working with recipes from favorites")
@SecurityRequirement(name = "bearerAuth")
public class FavoriteRecipeController {

    private final FavoriteRecipeService favoriteRecipeService;

    @GetMapping("/")
    @Operation(description = "Getting a list of recipes in the user's favorites")
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
    @Operation(description = "Adding a recipe to favorites")
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
    @Operation(description = "Deleting a recipe from favorites")
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

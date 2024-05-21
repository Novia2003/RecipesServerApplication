package ru.vsu.cs.tp.recipesServerApplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipeAllInfoResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipePreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipesPreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.service.FolkRecipeService;
import ru.vsu.cs.tp.recipesServerApplication.service.SpoonacularService;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@Tag(name = "RecipeController", description = "Functions for working with recipes")
public class RecipeController {

    private final SpoonacularService spoonacularService;

    private final FolkRecipeService folkRecipeService;

    @GetMapping("/{id}")
    @Operation(description = "Getting full information about the recipe")
    public ResponseEntity<RecipeAllInfoResponse> getRecipeById(
            @PathVariable Long id,
            @RequestHeader(required = false, name="token") String jwt,
            @RequestParam Boolean isUserRecipe
    ) {
        RecipeAllInfoResponse recipe;

        if (isUserRecipe)
            recipe = folkRecipeService.getRecipeInformation(id, jwt);
        else
            recipe = spoonacularService.getRecipeInformation(id, jwt);

        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/complexSearch")
    @Operation(description = "Search for recipes:)")
    public ResponseEntity<RecipesPreviewResponse> getRecipes(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String diet,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer number,
            @RequestHeader(required = false, name="token") String jwt,
            @RequestParam Boolean isUserRecipes
    ) {
        RecipesPreviewResponse recipes;

        if (isUserRecipes)
            recipes = folkRecipeService.getRecipes(query, page, number, jwt);
        else
            recipes = spoonacularService.getRecipes(query, type, diet, page, number, jwt);

        if (recipes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }


    @GetMapping("/random")
    @Operation(description = "Getting a random recipe")
    public ResponseEntity<RecipePreviewResponse> getRandomRecipe(
            @RequestHeader(required = false, name="token") String jwt
    ) {
        RecipePreviewResponse recipe = spoonacularService.getRandomRecipe(jwt);

        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
}

package ru.vsu.cs.tp.recipesServerApplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipeAllInfoResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipesPreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.service.FolkRecipeService;
import ru.vsu.cs.tp.recipesServerApplication.service.SpoonacularService;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final SpoonacularService spoonacularService;

    private final FolkRecipeService folkRecipeService;

    @GetMapping("/{id}")
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
    public ResponseEntity<RecipesPreviewResponse> getRecipes(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String diet,
            @RequestHeader(required = false, name="token") String jwt,
            @RequestParam Boolean isUserRecipes
    ) {
        RecipesPreviewResponse recipes;

        if (isUserRecipes)
            recipes = folkRecipeService.getRecipes(query, jwt);
        else
            recipes = spoonacularService.getRecipes(query, type, diet, jwt);

        if (recipes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }
}

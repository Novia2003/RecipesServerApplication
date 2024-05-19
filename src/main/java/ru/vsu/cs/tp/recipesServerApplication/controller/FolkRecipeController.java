package ru.vsu.cs.tp.recipesServerApplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.tp.recipesServerApplication.dto.request.recipe.UserRecipeRequest;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipesPreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.service.FolkRecipeService;

@RestController
@RequestMapping("/api/v1/userRecipes")
@RequiredArgsConstructor
public class FolkRecipeController {

    private final FolkRecipeService folkRecipeService;

    @GetMapping("/")
    public ResponseEntity<RecipesPreviewResponse> getUserRecipes(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer number,
            @RequestHeader(name="token") String jwt
    ) {
        RecipesPreviewResponse recipes = folkRecipeService.getFolkRecipes(jwt, page, number);

        if (recipes == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recipes);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createUserRecipe(
            @RequestBody UserRecipeRequest recipeRequest,
            @RequestHeader(name="token") String jwt
    ) {
        boolean result = folkRecipeService.createUserRecipe(recipeRequest, jwt);

        if (!result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user recipe");
        }

        return ResponseEntity.ok().build();
    }
}
package ru.vsu.cs.tp.recipesServerApplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipesPreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.service.FolkRecipeService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final FolkRecipeService folkRecipeService;

//    @GetMapping("/statistic")
//    public ResponseEntity<AllDiets> getStatistic() {
//
//        return ResponseEntity.ok(diets);
//    }

    @GetMapping("/recipesToCheck")
    public ResponseEntity<RecipesPreviewResponse> getRecipesToCheck(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer number
    ) {
        RecipesPreviewResponse response = folkRecipeService.getRecipesToCheck(page, number);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/recipesToCheck/{recipeId}")
    public ResponseEntity<?> checkRecipe(
            @PathVariable("recipeId") Long recipeId,
            @RequestParam Boolean isApproved
    ) {
        boolean result = folkRecipeService.checkRecipe(recipeId, isApproved);

        if (!result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to remove recipe from favorites");
        }
        return ResponseEntity.ok().build();
    }
}

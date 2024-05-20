package ru.vsu.cs.tp.recipesServerApplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.recipe.RecipesPreviewResponse;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.statistic.StatisticResponse;
import ru.vsu.cs.tp.recipesServerApplication.service.FolkRecipeService;
import ru.vsu.cs.tp.recipesServerApplication.service.StatisticService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "AdminController", description = "The functions of the administrator")
public class AdminController {

    private final FolkRecipeService folkRecipeService;

    private final StatisticService statisticService;

    @GetMapping("/statistic")
    @Operation(description = "Getting statistical data")
    public ResponseEntity<StatisticResponse> getStatistic() {
        StatisticResponse response = statisticService.getStatistic();

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/recipesToCheck")
    @Operation(description = "Getting a list of user recipes to check")
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
    @Operation(description = "Approval/disapproval of the recipe")
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

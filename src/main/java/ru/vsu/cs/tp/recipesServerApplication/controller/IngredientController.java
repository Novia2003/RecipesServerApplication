package ru.vsu.cs.tp.recipesServerApplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.ingredient.AllIngredients;
import ru.vsu.cs.tp.recipesServerApplication.service.IngredientService;

@RestController
@RequestMapping("/api/v1/ingredients")
@RequiredArgsConstructor
@Tag(name = "IngredientController", description = "List of ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/")
    @Operation(description = "Getting a list of ingredients")
    public ResponseEntity<AllIngredients> getDiets() {
        AllIngredients ingredients = ingredientService.getAllIngredients();

        if (ingredients == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredients);
    }
}

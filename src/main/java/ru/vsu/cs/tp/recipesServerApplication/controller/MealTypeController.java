package ru.vsu.cs.tp.recipesServerApplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.mealType.AllMealTypes;
import ru.vsu.cs.tp.recipesServerApplication.service.MealTypeService;

@RestController
@RequestMapping("/api/v1/mealTypes")
@RequiredArgsConstructor
@Tag(name = "MealTypeController", description = "List of meal types")
public class MealTypeController {

    private final MealTypeService mealTypeService;

    @GetMapping("/")
    @Operation(description = "Getting a list of meal types")
    public ResponseEntity<AllMealTypes> getMealTypes() {
        AllMealTypes mealTypes = mealTypeService.getAllMealTypes();

        if (mealTypes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mealTypes);
    }
}

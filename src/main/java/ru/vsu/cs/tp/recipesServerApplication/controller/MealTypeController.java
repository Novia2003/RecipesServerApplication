package ru.vsu.cs.tp.recipesServerApplication.controller;

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
public class MealTypeController {

    private final MealTypeService mealTypeService;

    @GetMapping("/")
    public ResponseEntity<AllMealTypes> getMealTypes() {
        AllMealTypes mealTypes = mealTypeService.getAllMealTypes();

        if (mealTypes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mealTypes);
    }
}

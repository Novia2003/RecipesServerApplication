package ru.vsu.cs.tp.recipesServerApplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.diet.AllDiets;
import ru.vsu.cs.tp.recipesServerApplication.service.DietService;

@RestController
@RequestMapping("/api/v1/diets")
@RequiredArgsConstructor
@Tag(name = "DietController", description = "List of diets")
public class DietController {

    private final DietService dietService;

    @GetMapping("/")
    @Operation(description = "Getting a list of diets")
    public ResponseEntity<AllDiets> getDiets() {
        AllDiets diets = dietService.getAllDiets();

        if (diets == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(diets);
    }
}

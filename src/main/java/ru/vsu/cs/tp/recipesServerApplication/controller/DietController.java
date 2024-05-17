package ru.vsu.cs.tp.recipesServerApplication.controller;

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
public class DietController {

    private final DietService dietService;

    @GetMapping("/")
    public ResponseEntity<AllDiets> getDiets() {
        AllDiets diets = dietService.getAllDiets();

        if (diets == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(diets);
    }
}

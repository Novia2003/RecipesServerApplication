package ru.vsu.cs.tp.recipesServerApplication.dto.response.ingredient;

import lombok.Data;

import java.util.List;

@Data
public class IngredientWithUnitResponse {
    String title;
    List<String> units;
}

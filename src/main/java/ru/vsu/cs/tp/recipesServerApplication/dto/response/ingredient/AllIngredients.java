package ru.vsu.cs.tp.recipesServerApplication.dto.response.ingredient;

import lombok.Data;

import java.util.List;

@Data
public class AllIngredients {
    List<IngredientWithUnitResponse> ingredients;
}

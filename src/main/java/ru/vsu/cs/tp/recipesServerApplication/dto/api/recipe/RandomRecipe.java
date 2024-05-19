package ru.vsu.cs.tp.recipesServerApplication.dto.api.recipe;

import lombok.Data;

import java.util.List;

@Data
public class RandomRecipe {
    List<RecipePreview> recipes;
}

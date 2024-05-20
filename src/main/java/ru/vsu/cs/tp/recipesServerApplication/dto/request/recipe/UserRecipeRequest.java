package ru.vsu.cs.tp.recipesServerApplication.dto.request.recipe;

import lombok.Data;
import ru.vsu.cs.tp.recipesServerApplication.dto.request.ingredient.IngredientDTORequest;

import java.util.List;

@Data
public class UserRecipeRequest {
    private String title;
    private byte[] image;
    private String description;
    private String category;
    private int readyInMinutes;
    private List<IngredientDTORequest> extendedIngredients;
    private List<String> steps;
    private Boolean isPublish;
}

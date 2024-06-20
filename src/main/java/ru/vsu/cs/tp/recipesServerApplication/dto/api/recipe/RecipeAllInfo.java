package ru.vsu.cs.tp.recipesServerApplication.dto.api.recipe;

import lombok.Data;
import ru.vsu.cs.tp.recipesServerApplication.dto.api.ingredient.IngredientDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.vsu.cs.tp.recipesServerApplication.dto.api.step.AnalyzedInstructionsDTO;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeAllInfo {
    private Long id;
    private String title;
    private String image;
    private List<String> dishTypes;
    private int readyInMinutes;
    private List<IngredientDTO> extendedIngredients;
    private List<AnalyzedInstructionsDTO> analyzedInstructions;
}

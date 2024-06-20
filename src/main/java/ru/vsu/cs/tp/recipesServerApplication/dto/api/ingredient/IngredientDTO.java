package ru.vsu.cs.tp.recipesServerApplication.dto.api.ingredient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientDTO {
    private String name;
    private BigDecimal amount;
    private String unit;
}
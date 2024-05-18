package ru.vsu.cs.tp.recipesServerApplication.dto.response.ingredient;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IngredientDTOResponse {
    private String name;
    private BigDecimal amount;
    private String unit;
}

package ru.vsu.cs.tp.recipesServerApplication.dto.request.ingredient;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IngredientDTORequest {
    private String name;
    private BigDecimal amount;
    private String unit;
}

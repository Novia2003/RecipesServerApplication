package ru.vsu.cs.tp.recipesServerApplication.dto.response.mealType;

import lombok.Data;

import java.util.List;

@Data
public class AllMealTypes {
    private List<MealTypeDTO> mealTypes;
}

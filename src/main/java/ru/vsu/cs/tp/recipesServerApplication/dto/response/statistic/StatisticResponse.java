package ru.vsu.cs.tp.recipesServerApplication.dto.response.statistic;

import lombok.Data;

@Data
public class StatisticResponse {
    private Long quantityUsers;
    private Long quantityUserRecipes;
    private String popularDiet;
    private String popularRecipe;
}

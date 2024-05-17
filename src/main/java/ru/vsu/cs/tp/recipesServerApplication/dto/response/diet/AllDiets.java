package ru.vsu.cs.tp.recipesServerApplication.dto.response.diet;

import lombok.Data;

import java.util.List;

@Data
public class AllDiets {
    private List<DietDTO> diets;
}

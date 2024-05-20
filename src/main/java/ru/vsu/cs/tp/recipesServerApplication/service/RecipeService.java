package ru.vsu.cs.tp.recipesServerApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.model.*;
import ru.vsu.cs.tp.recipesServerApplication.repository.RecipeRepository;
import ru.vsu.cs.tp.recipesServerApplication.repository.RecipeViewRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final RecipeViewRepository recipeViewRepository;

    public void increaseNumberViews(Long recipeId, RecipeType recipeType) {
        Recipe recipe = recipeRepository.findByRecipeIdAndRecipeType(recipeId, recipeType);

        if (recipe == null) {
            createRecipe(recipeId, recipeType);
        } else {
            RecipeView recipeView = new RecipeView();

            recipeView.setViewDate(LocalDate.now());
            recipeView.setRecipe(recipe);

            recipeViewRepository.save(recipeView);
        }
    }

    public void createRecipe(Long recipeId, RecipeType recipeType) {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);
        recipe.setRecipeType(recipeType);
        recipeRepository.save(recipe);

        RecipeView recipeView = new RecipeView();

        recipeView.setViewDate(LocalDate.now());
        recipeView.setRecipe(recipe);

        recipeViewRepository.save(recipeView);
    }

    public Recipe getMostViewedDietLastMonth() {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        LocalDate startDate = lastMonth.atDay(1);
        LocalDate endDate = lastMonth.atEndOfMonth();
        List<RecipeView> views = recipeViewRepository.findAllByViewDateBetween(startDate, endDate);

        return views.stream()
                .collect(Collectors.groupingBy(RecipeView::getRecipe, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}

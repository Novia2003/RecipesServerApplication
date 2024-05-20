package ru.vsu.cs.tp.recipesServerApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.statistic.StatisticResponse;
import ru.vsu.cs.tp.recipesServerApplication.model.*;
import ru.vsu.cs.tp.recipesServerApplication.repository.FolkRecipeRepository;
import ru.vsu.cs.tp.recipesServerApplication.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final UserRepository userRepository;

    private final FolkRecipeRepository folkRecipeRepository;

    private final DietService dietService;

    private final RecipeService recipeService;

    private SpoonacularService spoonacularService;

    public StatisticResponse getStatistic() {
        StatisticResponse response = new StatisticResponse();
        response.setQuantityUsers(userRepository.countByRole(Role.USER));
        response.setQuantityUserRecipes(folkRecipeRepository.count());

        Diet diet = dietService.getMostViewedDietLastMonth();

        if (diet == null)
            response.setPopularDiet("There is no data for the last month");
        else
            response.setPopularDiet(diet.getName());

        Recipe recipe = recipeService.getMostViewedDietLastMonth();

        if (recipe == null)
            response.setPopularRecipe("There is no data for the last month");
        else
            if (recipe.getRecipeType() == RecipeType.FOLK) {
                Optional<FolkRecipe> folkRecipeOptional = folkRecipeRepository.findById(recipe.getId());

                if (folkRecipeOptional.isEmpty()) response.setPopularRecipe("There is no data for the last month");
                else response.setPopularRecipe(folkRecipeOptional.get().getName());

            } else response.setPopularRecipe(spoonacularService.getRecipeTitle(recipe.getId()));

        return response;
    }
}

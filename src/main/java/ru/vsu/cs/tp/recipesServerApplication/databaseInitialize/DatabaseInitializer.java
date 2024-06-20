package ru.vsu.cs.tp.recipesServerApplication.databaseInitialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vsu.cs.tp.recipesServerApplication.service.DietService;
import ru.vsu.cs.tp.recipesServerApplication.service.IngredientService;
import ru.vsu.cs.tp.recipesServerApplication.service.MealTypeService;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInitializer  {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private MealTypeService mealTypeService;

    @Autowired
    private DietService dietService;

    public void initializeIngredients() {
        String filePath = "src/main/resources/ingredients/Ingredients.csv";
        ingredientService.importIngredientsFromCsv(filePath);
    }

    public void initializeMealTypes() {
        List<String> mealTypes = new ArrayList<>();
        mealTypes.add("main course");
        mealTypes.add("side dish");
        mealTypes.add("dessert");
        mealTypes.add("appetizer");
        mealTypes.add("salad");
        mealTypes.add("bread");
        mealTypes.add("breakfast");
        mealTypes.add("soup");
        mealTypes.add("beverage");
        mealTypes.add("sauce");
        mealTypes.add("marinade");
        mealTypes.add("fingerfood");
        mealTypes.add("snack");
        mealTypes.add("drink");

        mealTypeService.saveMealTypes(mealTypes);
    }

    public void initializeDiets() {
        List<String> diets = new ArrayList<>();
        diets.add("Gluten Free");
        diets.add("Ketogenic");
        diets.add("Vegetarian");
        diets.add("Lacto-Vegetarian");
        diets.add("Ovo-Vegetarian");
        diets.add("Vegan");
        diets.add("Pescetarian");
        diets.add("Paleo");
        diets.add("Primal");
        diets.add("Low FODMAP");
        diets.add("Whole30");

        dietService.saveDiets(diets);
    }
}

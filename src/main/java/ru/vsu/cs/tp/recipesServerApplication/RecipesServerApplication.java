package ru.vsu.cs.tp.recipesServerApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.vsu.cs.tp.recipesServerApplication.service.DietService;
import ru.vsu.cs.tp.recipesServerApplication.service.MealTypeService;
import ru.vsu.cs.tp.recipesServerApplication.service.SpoonacularService;

@SpringBootApplication
public class RecipesServerApplication {

	public static void main(String[] args) throws JsonProcessingException {
		ApplicationContext context = SpringApplication.run(RecipesServerApplication.class, args);
		/*
		DatabaseInitializer initializer = context.getBean(DatabaseInitializer.class);
		initializer.initializeIngredients();
		initializer.initializeMealTypes();
		initializer.initializeDiets();
		 */

		SpoonacularService spoonacularService = context.getBean(SpoonacularService.class);
		DietService dietService = context.getBean(DietService.class);
		MealTypeService mealTypeService = context.getBean(MealTypeService.class);

//		var result = spoonacularService.getRecipeInformation(715415);
//		var result2 = spoonacularService.getRecipes("pizza", null, null);
		var result3 = dietService.getAllDiets();
		var result4 = mealTypeService.getAllMealTypes();

		System.out.println();
	}
}

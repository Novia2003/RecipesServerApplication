package ru.vsu.cs.tp.recipesServerApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.vsu.cs.tp.recipesServerApplication.service.DietService;
import ru.vsu.cs.tp.recipesServerApplication.service.ImageService;
import ru.vsu.cs.tp.recipesServerApplication.service.MealTypeService;
import ru.vsu.cs.tp.recipesServerApplication.service.SpoonacularService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Recipes API", version = "1.0"))
public class RecipesServerApplication {

	public static void main(String[] args) throws IOException {
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

		ImageService imageService = context.getBean(ImageService.class);
		File file = new File("C:\\Users\\vyach\\IdeaProjects\\ImageByteArray\\src\\029.jpg");

		String image = null;

//		try {
//			byte[] fileBytes = convertFileToByteArray(file);
//			image = imageService.upload(fileBytes, "jpeg");
//			System.out.println("File successfully converted to byte array. Length: " + fileBytes.length);
//		} catch (IOException e) {
//			System.err.println("Error converting file to byte array: " + e.getMessage());
//		}
//
//		System.out.println(image);
	}

	public static byte[] convertFileToByteArray(File file) throws IOException {
		// Проверка на существование файла
		if (!file.exists()) {
			throw new IOException("File not found: " + file.getAbsolutePath());
		}

		// Чтение файла в массив байт
		return Files.readAllBytes(file.toPath());
	}
}

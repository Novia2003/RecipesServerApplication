package ru.vsu.cs.tp.recipesServerApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RecipesServerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(RecipesServerApplication.class, args);

		/*
		DatabaseInitializer initializer = context.getBean(DatabaseInitializer.class);
		initializer.initializeIngredients();
		initializer.initializeMealTypes();
		initializer.initializeDiets();
		 */
	}
}

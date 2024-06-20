package ru.vsu.cs.tp.recipesServerApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.ingredient.AllIngredients;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.ingredient.IngredientWithUnitResponse;
import ru.vsu.cs.tp.recipesServerApplication.model.Ingredient;
import ru.vsu.cs.tp.recipesServerApplication.repository.IngredientRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    private final SpoonacularService spoonacularService;

    public void importIngredientsFromCsv(String filePath) {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                rows.add(values);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the file with ingredients");
        }

        for (String[] row : rows) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(row[0].trim());
//            ingredient.setPossibleMeasurementUnits(
//                    spoonacularService.getIngredientPossibleUnits(Integer.parseInt(row[1].trim()))
//            );

            ingredientRepository.save(ingredient);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public AllIngredients getAllIngredients() {
        List<IngredientWithUnitResponse> ingredients = new ArrayList<>();

        for (Ingredient ingredient : ingredientRepository.findAll()) {
            IngredientWithUnitResponse ingredientWithUnit = new IngredientWithUnitResponse();

            ingredientWithUnit.setTitle(ingredient.getName());

            List<String> units = new ArrayList<>();
            Collections.addAll(units, ingredient.getPossibleMeasurementUnits().split(", "));
            ingredientWithUnit.setUnits(units);

            ingredients.add(ingredientWithUnit);
        }

        AllIngredients allIngredients = new AllIngredients();
        allIngredients.setIngredients(ingredients);

        return allIngredients;
    }
}

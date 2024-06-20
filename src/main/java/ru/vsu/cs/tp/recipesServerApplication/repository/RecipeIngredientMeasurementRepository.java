package ru.vsu.cs.tp.recipesServerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.tp.recipesServerApplication.model.RecipeIngredientMeasurement;

import java.util.List;

public interface RecipeIngredientMeasurementRepository extends JpaRepository<RecipeIngredientMeasurement, Long> {
    List<RecipeIngredientMeasurement> findByFolkRecipeId(Long folkRecipeId);
}

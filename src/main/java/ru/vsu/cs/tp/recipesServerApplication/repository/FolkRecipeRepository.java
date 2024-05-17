package ru.vsu.cs.tp.recipesServerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.tp.recipesServerApplication.model.FolkRecipe;

import java.util.List;

public interface FolkRecipeRepository extends JpaRepository<FolkRecipe, Long> {
    List<FolkRecipe> findByIsApprovedTrue();

    List<FolkRecipe> findByNameStartsWithIgnoreCaseAndIsApprovedTrue(String query);
}

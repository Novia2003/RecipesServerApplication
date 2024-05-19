package ru.vsu.cs.tp.recipesServerApplication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.tp.recipesServerApplication.model.FolkRecipe;

public interface FolkRecipeRepository extends JpaRepository<FolkRecipe, Long> {
    Page<FolkRecipe> findByIsApprovedTrue(Pageable pageable);

    Page<FolkRecipe> findByNameStartsWithIgnoreCaseAndIsApprovedTrue(String query, Pageable pageable);

    Page<FolkRecipe> findByAuthorId(Long authorId, Pageable pageable);
}

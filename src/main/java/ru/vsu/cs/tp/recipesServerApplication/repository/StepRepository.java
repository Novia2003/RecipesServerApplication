package ru.vsu.cs.tp.recipesServerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.tp.recipesServerApplication.model.Step;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findByFolkRecipeId(Long folkRecipeId);
}

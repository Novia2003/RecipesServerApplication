package ru.vsu.cs.tp.recipesServerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.tp.recipesServerApplication.model.RecipeView;

import java.time.LocalDate;
import java.util.List;

public interface RecipeViewRepository extends JpaRepository<RecipeView, Long> {
    List<RecipeView> findAllByViewDateBetween(LocalDate startDate, LocalDate endDate);
}

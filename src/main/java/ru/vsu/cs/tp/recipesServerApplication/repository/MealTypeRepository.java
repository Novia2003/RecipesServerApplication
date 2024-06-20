package ru.vsu.cs.tp.recipesServerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.tp.recipesServerApplication.model.MealType;

public interface MealTypeRepository extends JpaRepository<MealType, Long> {
    MealType findByName(String name);
}

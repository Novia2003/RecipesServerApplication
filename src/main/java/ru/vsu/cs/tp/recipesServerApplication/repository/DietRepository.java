package ru.vsu.cs.tp.recipesServerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.tp.recipesServerApplication.model.Diet;

public interface DietRepository extends JpaRepository<Diet, Long> {
    Diet findByName(String name);
}
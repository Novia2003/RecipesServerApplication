package ru.vsu.cs.tp.recipesServerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.tp.recipesServerApplication.model.DietView;

import java.time.LocalDate;
import java.util.List;

public interface DietViewRepository extends JpaRepository<DietView, Long> {
    List<DietView> findAllByViewDateBetween(LocalDate startDate, LocalDate endDate);

}

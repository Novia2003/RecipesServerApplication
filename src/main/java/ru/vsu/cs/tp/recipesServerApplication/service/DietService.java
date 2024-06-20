package ru.vsu.cs.tp.recipesServerApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.diet.AllDiets;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.diet.DietDTO;
import ru.vsu.cs.tp.recipesServerApplication.model.Diet;
import ru.vsu.cs.tp.recipesServerApplication.model.DietView;
import ru.vsu.cs.tp.recipesServerApplication.repository.DietRepository;
import ru.vsu.cs.tp.recipesServerApplication.repository.DietViewRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;
    private final DietViewRepository dietViewRepository;

    public void saveDiets(List<String> diets) {
        for (String name: diets) {
            Diet diet = new Diet();
            diet.setName(name);
            dietRepository.save(diet);
        }
    }

    public AllDiets getAllDiets() {
        List<DietDTO> diets = new ArrayList<>();

        for (Diet diet: dietRepository.findAll()) {
            DietDTO dietDTO = new DietDTO();
            dietDTO.setTitle(diet.getName());
            diets.add(dietDTO);
        }

        AllDiets allDiets = new AllDiets();
        allDiets.setDiets(diets);

        return allDiets;
    }

    public void increaseNumberViews(String name) {
        Diet diet = dietRepository.findByName(name);

        if (diet != null) {
            DietView dietView = new DietView();
            dietView.setDiet(diet);
            dietView.setViewDate(LocalDate.now());
            dietViewRepository.save(dietView);
        }
    }

    public Diet getMostViewedDietLastMonth() {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        LocalDate startDate = lastMonth.atDay(1);
        LocalDate endDate = lastMonth.atEndOfMonth();
        List<DietView> views = dietViewRepository.findAllByViewDateBetween(startDate, endDate);

        return views.stream()
                .collect(Collectors.groupingBy(DietView::getDiet, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}

package ru.vsu.cs.tp.recipesServerApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.diet.AllDiets;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.diet.DietDTO;
import ru.vsu.cs.tp.recipesServerApplication.model.Diet;
import ru.vsu.cs.tp.recipesServerApplication.repository.DietRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;

    public void saveDiets(List<String> diets) {
        for (String name: diets) {
            Diet diet = new Diet();
            diet.setName(name);
            diet.setNumberViews(0L);

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
            diet.setNumberViews(diet.getNumberViews() + 1);
            dietRepository.save(diet);
        }
    }
}

package ru.vsu.cs.tp.recipesServerApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.mealType.AllMealTypes;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.mealType.MealTypeDTO;
import ru.vsu.cs.tp.recipesServerApplication.model.MealType;
import ru.vsu.cs.tp.recipesServerApplication.repository.MealTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealTypeService {

    private final MealTypeRepository mealTypeRepository;

    public void saveMealTypes(List<String> mealTypes) {
        for (String name: mealTypes) {
            MealType mealType = new MealType();
            mealType.setName(name);

            mealTypeRepository.save(mealType);
        }
    }

    public AllMealTypes getAllMealTypes() {
        List<MealTypeDTO> mealTypes = new ArrayList<>();

        for (MealType mealType: mealTypeRepository.findAll()) {
            MealTypeDTO mealTypeDTO = new MealTypeDTO();
            mealTypeDTO.setTitle(mealType.getName());

            mealTypes.add(mealTypeDTO);
        }

        AllMealTypes allMealTypes = new AllMealTypes();
        allMealTypes.setMealTypes(mealTypes);

        return allMealTypes;
    }
}
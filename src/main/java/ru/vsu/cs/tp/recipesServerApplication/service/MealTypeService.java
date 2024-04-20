package ru.vsu.cs.tp.recipesServerApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.model.MealType;
import ru.vsu.cs.tp.recipesServerApplication.repository.MealTypeRepository;

import java.util.List;

@Service
public class MealTypeService {

    @Autowired
    private MealTypeRepository mealTypeRepository;

    public void saveMealTypes(List<String> mealTypes) {
        for (String name: mealTypes) {
            MealType mealType = new MealType();
            mealType.setName(name);

            mealTypeRepository.save(mealType);
        }
    }
}
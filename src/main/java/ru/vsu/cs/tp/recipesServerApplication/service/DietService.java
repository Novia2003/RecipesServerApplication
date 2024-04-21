package ru.vsu.cs.tp.recipesServerApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.model.Diet;
import ru.vsu.cs.tp.recipesServerApplication.repository.DietRepository;

import java.util.List;

@Service
public class DietService {

    @Autowired
    private DietRepository dietRepository;

    public void saveDiets(List<String> diets) {
        for (String name: diets) {
            Diet diet = new Diet();
            diet.setName(name);
            diet.setNumberViews(0L);

            dietRepository.save(diet);
        }
    }
}

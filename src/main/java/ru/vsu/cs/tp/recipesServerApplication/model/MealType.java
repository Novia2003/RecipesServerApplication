package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Basic;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "meal_type")
public class MealType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Basic
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "mealTypeById")
    private Collection<FolkRecipe> folkRecipesByMealTypeId;
}

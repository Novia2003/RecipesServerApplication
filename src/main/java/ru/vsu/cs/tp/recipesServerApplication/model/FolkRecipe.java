package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "folk_recipes")
public class FolkRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "folk_recipe_id_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "meal_type_id", nullable = false)
    private MealType mealType;

    @Column(columnDefinition = "BYTEA")
    private byte[] image;

    @Column(nullable = false)
    private Boolean isReviewedByAdmin;

    @Column(nullable = false)
    private Boolean isApproved;

    @OneToMany(mappedBy = "folkRecipe")
    @ToString.Exclude
    private Collection<Step> cookingSteps;

    @OneToMany(mappedBy = "folkRecipe")
    @ToString.Exclude
    private Collection<RecipeIngredientMeasurement> recipeIngredientMeasurements;
}

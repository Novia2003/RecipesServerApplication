package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Basic;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "folk_recipes")
public class FolkRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Basic
    @Column(nullable = false)
    private String name;

    @Basic
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User authorById;

    @ManyToOne
    @JoinColumn(name = "meal_type_id", nullable = false)
    private MealType mealTypeById;

    @Basic
    @Column(name = "image", columnDefinition = "BYTEA")
    private byte[] image;

    @Basic
    @Column(name = "is_reviewed_by_admin", nullable = false)
    private Boolean isReviewedByAdmin;

    @Basic
    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;

    @OneToMany(mappedBy = "folkRecipeById")
    private Collection<Step> stepsByFolkRecipeId;

    @OneToMany(mappedBy = "folkRecipeById")
    private Collection<RecipeIngredientMeasurement> recipeIngredientMeasurementsByFolkRecipeId;
}

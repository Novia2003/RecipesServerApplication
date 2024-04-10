package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Basic;
import lombok.Data;

@Entity
@Data
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Basic
    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Basic
    @Column(name = "recipe_type", nullable = false)
    private String recipeType;

    @Basic
    @Column(name = "click_number", nullable = false)
    private Long clickNumber;
}

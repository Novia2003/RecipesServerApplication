package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "steps")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "steps_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "folk_recipe_id", nullable = false)
    private FolkRecipe folkRecipe;

    @Column(nullable = false)
    private String step;

    @Column(nullable = false)
    private int stepNumber;
}

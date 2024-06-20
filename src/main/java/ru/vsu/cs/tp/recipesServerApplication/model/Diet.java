package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.*;
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
@Table(name = "diets")
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diet_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "diet")
    @ToString.Exclude
    private Collection<DietView> dietViews;
}

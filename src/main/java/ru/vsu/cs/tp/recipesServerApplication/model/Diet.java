package ru.vsu.cs.tp.recipesServerApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "diets")
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diets_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "number_views", nullable = false)
    private Long numberViews;
}

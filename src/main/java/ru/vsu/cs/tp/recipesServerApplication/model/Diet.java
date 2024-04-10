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
@Table(name = "diet")
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Basic
    @Column(nullable = false, unique = true)
    private String name;

    @Basic
    @Column(name = "click_number", nullable = false)
    private Long clickNumber;
}

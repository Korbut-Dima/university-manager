package com.example.university_manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Table(name = "lectors")
public class Lector {

    public Lector() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    private String secondName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotNull(message = "Rank must be indicated")
    @Enumerated(EnumType.STRING)
    private Rank degree;

    @NotNull(message = "Salary is expected")
    @PositiveOrZero(message = "Salary should be zero or greater")
    private BigDecimal salary;
}

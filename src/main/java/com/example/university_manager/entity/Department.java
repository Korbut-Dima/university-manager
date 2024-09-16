package com.example.university_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {

    public Department() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Name of department is expected")
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "head_of_department_id")
    private Lector departmentHead;

    @ManyToMany
    @JoinTable(
            name = "department_lectors",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "lector_id")
    )
    private Set<Lector> lectors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lector getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(Lector departmentHead) {
        this.departmentHead = departmentHead;
    }

    public Set<Lector> getLectors() {
        return lectors;
    }

    public void setLectors(Set<Lector> lectors) {
        this.lectors = lectors;
    }
}

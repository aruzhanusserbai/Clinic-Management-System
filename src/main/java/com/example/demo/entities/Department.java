package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "department")
    private String department;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "department")
    private List<DoctorDepartment> doctorDepartments = new ArrayList<>();
}

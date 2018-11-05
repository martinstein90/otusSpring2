package com.martin.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;

@Entity(name = "employee")
public class Employee {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;

    public Employee() {
    }

    public Employee(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}

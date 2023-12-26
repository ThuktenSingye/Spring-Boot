package com.sdu.usermanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Department")
public class Department {
    // Define Field
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Dept_Id") // Optional
    private int Dept_Id;

    @Column(name = "Dept_Name")
    private String Dept_Name;
}

package com.sdu.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdu.usermanagement.dto.DepartmentDTO;
import com.sdu.usermanagement.service.DepartmentService;

@RestController
// @RequestMapping("v1/api")
@RequestMapping("/departments")
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentController {
// Define request and response endpoint
    @Autowired
    private DepartmentService departmentService;

// Adding Department
    @PostMapping
    private ResponseEntity<String> addDepartment(@RequestBody DepartmentDTO departmentDTO){
        return departmentService.saveDepartment(departmentDTO);

    }

// Get All Department
    @GetMapping
    private ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return departmentService.findAllDepartment();
    }
    
 // Get All Department
    @GetMapping("/total")
    private ResponseEntity<Long> getDepartmentCount() {
        return departmentService.findTotalDepartmentCount();
    }
    

// Retrieve Single Department By Id
    @GetMapping("/{dept_id}")
    private ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Integer dept_id){
        return departmentService.findDepartmentById(dept_id);

    }

// Updating the department
    @PutMapping
    private ResponseEntity<String> updateDepartment(@RequestBody DepartmentDTO departmentDTO){
        return departmentService.saveDepartment(departmentDTO);
    }

// Deleting the department
    @DeleteMapping("/{dept_id}")
    private ResponseEntity<String> deleteDepartment(@PathVariable Integer dept_id){
        return departmentService.deleteDepartment(dept_id);
    }

}

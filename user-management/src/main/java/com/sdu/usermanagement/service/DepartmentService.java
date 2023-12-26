package com.sdu.usermanagement.service;

import java.util.List;

import com.sdu.usermanagement.dto.DepartmentDTO;


public interface DepartmentService {
// List all Department
    List<DepartmentDTO> findAll();
    
// Find Department By Id
    DepartmentDTO findById(int dept_id);

// Save Department
    DepartmentDTO save(DepartmentDTO departmentDTO);

// Update Department
    void update(DepartmentDTO departmentDTO);

// Delete Department
    void delete(int dept_id);
}

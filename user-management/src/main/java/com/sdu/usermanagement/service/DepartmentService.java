package com.sdu.usermanagement.service;

import java.util.List;

import com.sdu.usermanagement.dto.DepartmentDTO;


public interface DepartmentService {
// List all Department
    List<DepartmentDTO> findAllDepartment();

// // Find Department By Id
    DepartmentDTO findDepartmentById(int dept_id);

// Save Department
    DepartmentDTO save(DepartmentDTO departmentDTO);

//     void delete(int dept_id);
    void delete(int dept_id);
}

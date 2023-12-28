package com.sdu.usermanagement.service;
import java.util.List;
import org.springframework.http.ResponseEntity;

import com.sdu.usermanagement.dto.DepartmentDTO;


public interface DepartmentService {
// List all Department
    ResponseEntity<List<DepartmentDTO>> findAllDepartment();

// // Find Department By Id
    DepartmentDTO findDepartmentById(int dept_id);

// Save Department
    ResponseEntity<Void> save(DepartmentDTO departmentDTO);

//     void delete(int dept_id);
    void delete(int dept_id);
}

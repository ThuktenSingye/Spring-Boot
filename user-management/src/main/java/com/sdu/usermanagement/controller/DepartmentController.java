package com.sdu.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("v1/api")
public class DepartmentController {
// Define request and response endpoint
    @Autowired
    private DepartmentService departmentService;

// Adding Department
    @PostMapping("/departments")
    private ResponseEntity<DepartmentDTO> addDepartment(@RequestBody DepartmentDTO departmentDTO){
        try {
            departmentDTO.setDeptId(0);
            DepartmentDTO savedDepartment = departmentService.save(departmentDTO);

            return ResponseEntity.ok(savedDepartment);

        } catch (Exception e) {
            // Handle any exceptions that might occur during the save operation
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

// Get All Department
    @GetMapping("/departments")
    private ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        // Create the list of department DTO
        List<DepartmentDTO> departmentDTOList = departmentService.findAllDepartment();

        // Return the list with HTTP status 200 (OK)
        return new ResponseEntity<>(departmentDTOList, HttpStatus.OK);
    }

// Retrieve Single Department By Id
    @GetMapping("/departments/{dept_id}")
    private ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable int dept_id){
        try{
            DepartmentDTO departmentDTO = departmentService.findDepartmentById(dept_id);
            return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

// Updating the department
    @PutMapping("/departments")
    private ResponseEntity<DepartmentDTO> updateDepartment(@RequestBody DepartmentDTO departmentDTO){
        try{
            DepartmentDTO savedDepartment = departmentService.save(departmentDTO);


            return new ResponseEntity<>(savedDepartment, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

// Deleting the department
    @DeleteMapping("/departments/{dept_id}")
    private ResponseEntity<DepartmentDTO> deleteDepartment(@PathVariable int dept_id){
        try{
            DepartmentDTO departmentDTO = departmentService.findDepartmentById(dept_id);
            departmentService.delete(dept_id);

            return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}

package com.sdu.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdu.usermanagement.dto.DepartmentDTO;
import com.sdu.usermanagement.model.Department;
import com.sdu.usermanagement.repository.DepartmentRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<Void> save(DepartmentDTO departmentDTO) {
        // TODO Auto-generated method stub
        // convert the Dto to entity to be save to a db
        try{
            if(departmentDTO == null){
                
                throw new RuntimeException("Missing Department Paramters");
            }
            Department department = departmentRepository.save(dtoToEntity(departmentDTO));
            if(department == null){
                throw new RuntimeException("Error while saving department");
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
    }


    // Method to convert Department Entity to Department DTO
    private DepartmentDTO entityToDto(Department department){

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDeptId(department.getDeptId());
        departmentDTO.setDeptName(department.getDeptName());
        return departmentDTO;
    }

    // Method to convert Department DTO back to Department Entity
    private Department dtoToEntity(DepartmentDTO departmentDTO){

        Department department = new Department();
        department.setDeptId(departmentDTO.getDeptId());
        department.setDeptName(departmentDTO.getDeptName());

        return department;
    }


    @Override
    public  ResponseEntity<List<DepartmentDTO>> findAllDepartment() {
        // TODO Auto-generated method stub
        try{
            List<DepartmentDTO> departmentDTOs = departmentRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
            if(departmentDTOs == null){
                /* Log the error */
            }
            return new ResponseEntity<>(departmentDTOs, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public DepartmentDTO findDepartmentById(int dept_id) {
        // TODO Auto-generated method stub
        Optional<Department> result = departmentRepository.findById(dept_id);
        Department department = null;
        if (result.isPresent()) {
            department = result.get();

        } else {
            // Handle the case where the department with the given ID is not found
            throw new RuntimeException("Did not found department id - "+ dept_id);

        }
        return entityToDto(department);

    }


    @Override
    @Transactional
    public void delete(int dept_id) {
        // TODO Auto-generated method stub
        try{
            departmentRepository.deleteById(dept_id);
        }
        catch(Exception e){
            throw new RuntimeException("Did not found department with id - "+ dept_id);
        }

    }

}

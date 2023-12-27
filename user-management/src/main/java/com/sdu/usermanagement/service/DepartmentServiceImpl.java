package com.sdu.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdu.usermanagement.dto.DepartmentDTO;
import com.sdu.usermanagement.model.Department;
import com.sdu.usermanagement.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        // TODO Auto-generated method stub
        // convert the Dto to entity to be save to a db
        Department department = departmentRepository.save(dtoToEntity(departmentDTO));
        return entityToDto(department);
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
    public List<DepartmentDTO> findAllDepartment() {
        // TODO Auto-generated method stub
        return departmentRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
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
            throw new RuntimeException("Did not found employee id - "+ dept_id);

        }
        return entityToDto(department);

    }


    @Override
    public void delete(int dept_id) {
        // TODO Auto-generated method stub
        departmentRepository.deleteById(dept_id);

    }

}

package com.sdu.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sdu.usermanagement.dto.DepartmentDTO;
import com.sdu.usermanagement.model.Department;
import com.sdu.usermanagement.repository.DepartmentRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class DepartmentServiceImpl implements DepartmentService{

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<String> saveDepartment(DepartmentDTO departmentDTO) {

        try{
            if(departmentDTO == null){
                return new ResponseEntity<>("Missing Department Parameter", HttpStatus.BAD_REQUEST);
            }
            Department department = departmentRepository.save(dtoToEntity(departmentDTO));
            if(department == null){
                /* Log the error */
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
    }



    @Override
    public  ResponseEntity<List<DepartmentDTO>> findAllDepartment() {
        try{
            List<DepartmentDTO> departmentDTOs = departmentRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
            if(departmentDTOs == null){
                /* Log the error */
                logger.info("NUll department List");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(departmentDTOs, HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Error while finding the list of department: ", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<DepartmentDTO> findDepartmentById(Integer dept_id) {
       try{
            /* Bad Request */
            if (dept_id ==  null || dept_id < 0) {
                /* Bad Request - Invalid user_id format */
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Optional<Department> result = departmentRepository.findById(dept_id);
            DepartmentDTO departmentDTO = null;
            if(result.isPresent()){
                departmentDTO = entityToDto(result.get());
            }
            else{
                /* user not found */
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            /* Succesful  */
            return new ResponseEntity<>(departmentDTO,HttpStatus.OK);
        }
        catch(Exception e){
            /* Log the errrpr */
            logger.error("Error while finding department by id:", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Override
    public ResponseEntity<String> deleteDepartment(Integer dept_id) {
       try{
            if(dept_id == null || dept_id < 0){
                /* log the error */
                return new ResponseEntity<>("Invalid dept_id format", HttpStatus.BAD_REQUEST);
            }
            if (!departmentRepository.existsById(dept_id)) {
                return new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
            }
            departmentRepository.deleteById(dept_id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            /* Log the error */
            logger.error("Error while deleting department", e.getMessage());
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

}

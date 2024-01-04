package com.sdu.usermanagement.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdu.usermanagement.dto.DepartmentDTO;
import com.sdu.usermanagement.model.Department;
import com.sdu.usermanagement.repository.DepartmentRepository;

import jakarta.transaction.Transactional;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
// In real life, minimized the try and catch
public class DepartmentServiceImpl implements DepartmentService{


    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<String> saveDepartment(DepartmentDTO departmentDTO) {

        try{
            if(departmentRepository.saveAndFlush(dtoToEntity(departmentDTO)) == null){
                log.info("[] returned value");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }
            return new ResponseEntity<>(HttpStatus.CREATED);
            
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
    }



    @Override
    public  ResponseEntity<List<DepartmentDTO>> findAllDepartment() {
        try{
            List<DepartmentDTO> departmentDTOs = departmentRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
            return new ResponseEntity<>(departmentDTOs, HttpStatus.OK);
        }
        catch(Exception e){
            log.error("Error while finding the list of department: "  + e.getMessage());
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
            DepartmentDTO departmentDTO = entityToDto(departmentRepository.findById(dept_id).orElseThrow()); 
            /* Succesful  */
            return new ResponseEntity<>(departmentDTO,HttpStatus.OK);
        }
        catch(Exception e){
            /* Log the errrpr */
            log.error("Error while finding department by id:" + e.getMessage());
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
        catch(Exception e){
            /* Log the error */
            log.error("Error while deleting department" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Method to convert Department Entity to Department DTO
    private DepartmentDTO entityToDto(Department department){

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDeptId(department.getDeptId());
        departmentDTO.setDeptName(department.getDeptName());
        departmentDTO.setDeptDescription(department.getDeptDescription());
        return departmentDTO;
    }

    // Method to convert Department DTO back to Department Entity
    private Department dtoToEntity(DepartmentDTO departmentDTO){

        Department department = new Department();
        department.setDeptId(departmentDTO.getDeptId());
        department.setDeptName(departmentDTO.getDeptName());
        department.setDeptDescription((departmentDTO.getDeptDescription()));
        return department;
    }


    @Override
	public ResponseEntity<Long> findTotalDepartmentCount() {
		try {
            long totalDepartmentCount = departmentRepository.count(); // Using count() to get the total department count
            return new ResponseEntity<>(totalDepartmentCount, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while retrieving total department count: "+ e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}



    

}

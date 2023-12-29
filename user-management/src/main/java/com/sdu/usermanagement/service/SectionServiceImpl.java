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

import com.sdu.usermanagement.dto.SectionDTO;
import com.sdu.usermanagement.model.Department;
import com.sdu.usermanagement.model.Section;
import com.sdu.usermanagement.repository.DepartmentRepository;
import com.sdu.usermanagement.repository.SectionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SectionServiceImpl implements SectionService{

    private static final Logger logger = LoggerFactory.getLogger(SectionServiceImpl.class);

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<List<SectionDTO>> findAllSection() {
        try{
            List<SectionDTO> sectionDTOs = sectionRepository.findAll().stream().map(this::sectionEntityToDto).collect(Collectors.toList());
            if(sectionDTOs == null){
                /* Log the error */
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(sectionDTOs, HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Error while retrieving all section: ", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
    }

    @Override
    public ResponseEntity<SectionDTO> findSectionById(Integer sect_id) {
        try{
            /* Bad Request */
            if (sect_id ==  null || sect_id < 0) {
                /* Bad Request - Invalid user_id format */
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Optional<Section> result = sectionRepository.findById(sect_id);
            SectionDTO sectionDTO = null;
            if(result.isPresent()){
                sectionDTO = sectionEntityToDto(result.get());
            }
            else{
                /* user not found */
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            /* Succesful  */
            return new ResponseEntity<>(sectionDTO ,HttpStatus.OK);
        }
        catch(Exception e){
            /* Log the errrpr */
            logger.error("Error while retrieving section by id: ", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> saveSection(SectionDTO sectionDTO) {
        try{
            if(sectionDTO == null){
                return new ResponseEntity<>("Missing Section Parameter ", HttpStatus.BAD_REQUEST);
            }
            Department department = departmentRepository.findById(sectionDTO.getDepartment().getDeptId()).orElse(null);

            if (department == null) {
                return new ResponseEntity<>("Department not found", HttpStatus.BAD_REQUEST);
            }
           
            // Set the Department in the Section entity
            Section section = sectionDtoToEntity(sectionDTO);
            section.setDepartment(department);
            Section savedSection = sectionRepository.save(section);

            if(savedSection == null){
                /* Log the error */
                logger.info("Saved Section is null! Error while saving");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e){
        logger.error("Error while saving/updating  section: ", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<String> deleteSection(Integer sect_id) {
        System.out.println("Inside section");
        try{
            if(sect_id == null || sect_id < 0){
                /* log the error */
                return new ResponseEntity<>("Invalid sec_id format", HttpStatus.BAD_REQUEST);
            }
            if (!sectionRepository.existsById(sect_id)) {
                return new ResponseEntity<>("Section not found", HttpStatus.NOT_FOUND);
            }
            sectionRepository.deleteById(sect_id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Section not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(Exception e){
            /* Log the error */
            logger.error("Error while deleting section: ", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    /* Define method to convert Section Entity to Section DTO */
    private SectionDTO sectionEntityToDto(Section section){
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setSectId(section.getSectId());
        sectionDTO.setSectName(section.getSectName());
        sectionDTO.setDepartment(section.getDepartment());

        return sectionDTO;
    }
    /* Method to convert Section DTO to Section Entity */
    private Section sectionDtoToEntity(SectionDTO sectionDTO){
        Section section = new Section();
        section.setSectId(sectionDTO.getSectId());
        section.setSectName(sectionDTO.getSectName());
        section.setDepartment(sectionDTO.getDepartment());

        return section;
    }
    
}

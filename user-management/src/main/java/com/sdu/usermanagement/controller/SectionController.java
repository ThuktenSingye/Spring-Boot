package com.sdu.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.sdu.usermanagement.dto.SectionDTO;
import com.sdu.usermanagement.service.SectionService;

@RestController
@RequestMapping("v1/api")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    /* Add new Service */
    @PostMapping("/services")
    public ResponseEntity<SectionDTO> addService(@RequestBody SectionDTO sectionDTO){
        try{
            // sectionDTO.setSectId(0);
            // System.out.println("Section DTO: " + sectionDTO);
            SectionDTO savedSection = sectionService.save(sectionDTO);
            return new ResponseEntity<>(savedSection, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /* Get All Service  */
    @GetMapping("/services")
    private ResponseEntity<List<SectionDTO>> getAllSection() {
        try{
            // Create the list of department DTO
            List<SectionDTO> sectionList = sectionService.findAllSection();

            // Return the list with HTTP status 200 (OK)
            return new ResponseEntity<>(sectionList, HttpStatus.OK);
        }   
        catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    /* Get services by id */
    @GetMapping("/services/{sect_id}")
     private ResponseEntity<SectionDTO> getSectionById(@PathVariable int sect_id){
        try{
            SectionDTO sectionDTO = sectionService.findSectionById(sect_id);
            return new ResponseEntity<>(sectionDTO, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/services/{sect_id}")
    private ResponseEntity<SectionDTO> deleteService(@PathVariable int sect_id){
        try{
            SectionDTO sectionDTO = sectionService.findSectionById(sect_id);
            sectionService.delete(sect_id);

            return new ResponseEntity<>(sectionDTO, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}

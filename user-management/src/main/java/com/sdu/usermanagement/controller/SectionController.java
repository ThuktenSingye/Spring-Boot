package com.sdu.usermanagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("/sections")
    public ResponseEntity<String> addService(@RequestBody SectionDTO sectionDTO){
        System.out.println(sectionDTO);
        return sectionService.saveSection(sectionDTO);
    }
    /* Get All Service  */
    @GetMapping("/sections")
    private ResponseEntity<List<SectionDTO>> getAllSection() {
        return sectionService.findAllSection();
        
    }

    /* Get services by id */
    @GetMapping("/sections/{sect_id}")
     private ResponseEntity<SectionDTO> getSectionById(@PathVariable Integer sect_id){
        return sectionService.findSectionById(sect_id);
    }

    @DeleteMapping("/sections/{sect_id}")
    private ResponseEntity<String> deleteService(@PathVariable Integer sect_id){
        return sectionService.deleteSection(sect_id);
    }



}

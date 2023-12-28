package com.sdu.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.sdu.usermanagement.dto.SectionDTO;
import com.sdu.usermanagement.model.Section;
import com.sdu.usermanagement.repository.SectionRepository;

import jakarta.transaction.Transactional;

@Service
public class SectionServiceImpl implements SectionService{

    @Autowired
    private SectionRepository sectionRepository;

    @Override
    public List<SectionDTO> findAllSection() {
        // TODO Auto-generated method stub
        return sectionRepository.findAll().stream().map(this::sectionEntityToDto).collect(Collectors.toList());
    }

    @Override
    public SectionDTO findSectionById(int sect_id) {
        // TODO Auto-generated method stub

        Optional<Section> result = sectionRepository.findById(sect_id);
        Section section = null;
        if(result.isPresent()){
            section = result.get();
        }
        else{
            throw new RuntimeException("Did not found section with id - "+ sect_id);
        }
        
        return sectionEntityToDto(section);
    }

    @Override
    @Transactional
    @Modifying
    public SectionDTO save(SectionDTO sectionDTO) {
        // TODO Auto-generated method stub
        Section section = sectionRepository.save(sectionDtoToEntity(sectionDTO));

        return sectionEntityToDto(section);
    }

    @Override
    @Transactional
    public void delete(int sect_id) {
        // TODO Auto-generated method stub
        sectionRepository.deleteById(sect_id);
        
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

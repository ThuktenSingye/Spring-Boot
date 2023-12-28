package com.sdu.usermanagement.service;

import java.util.List;

import com.sdu.usermanagement.dto.SectionDTO;

public interface SectionService {
    /* List all Section */
    List<SectionDTO> findAllSection();
    /* Find Section by ID */
    SectionDTO findSectionById(int sect_id);
    /* Save or Update Sectino */
    SectionDTO save(SectionDTO sectionDTO);
    /* Delete Section */
    void delete(int sect_id);
}

package com.sdu.usermanagement.dto;
import java.util.List;

import com.sdu.usermanagement.model.Section;

import lombok.Data;

@Data
public class DepartmentDTO {
    private int deptId;
    private String deptName;
    private String deptDescription;
    private List<Section> sections;
}

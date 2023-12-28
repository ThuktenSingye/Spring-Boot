package com.sdu.usermanagement.dto;

import com.sdu.usermanagement.model.Address;
import com.sdu.usermanagement.model.Gender;
import com.sdu.usermanagement.model.Section;

import lombok.Data;

@Data
public class UserDTO {
    private int userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private int mobileNo;
    private String email;
    private Gender gender;
    private Address address;
    private Section section;
}

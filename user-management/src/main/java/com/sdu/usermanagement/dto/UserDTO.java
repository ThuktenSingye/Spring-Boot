package com.sdu.usermanagement.dto;

import java.sql.Date;


import com.sdu.usermanagement.model.Address;
import com.sdu.usermanagement.model.Gender;
import com.sdu.usermanagement.model.ProfileImage;
import com.sdu.usermanagement.model.Section;

import lombok.Data;

@Data
public class UserDTO {
    private int userId;
    private long cidNo;
    private String firstName;
    private String middleName;
    private String lastName;
    private int mobileNo;
    private String email;
    private Date dob;
    private Gender gender;
    private Address address;
    private Section section;
    private ProfileImage profileImage;
}

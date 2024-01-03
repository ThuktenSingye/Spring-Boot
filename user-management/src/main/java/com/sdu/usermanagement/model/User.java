package com.sdu.usermanagement.model;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User{
    /* Define field */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private int userId;

    @Column(name = "CID_No")
    private long cidNo;

    @Column(name = "FName")
    private String firstName;

    @Column(name = "MName")
    private String middleName;

    @Column(name = "LName")
    private String lastName;

    @Column(name = "Mobile_No")
    private int mobileNo;

    @Column(name = "Email")
    private String email;


    @Column(name = "DOB")
    private Date dob;

    @ManyToOne(
        cascade = {
            CascadeType.DETACH, 
            CascadeType.PERSIST, 
            CascadeType.REFRESH, 
            CascadeType.MERGE
        }
    )
    @JoinColumn(name = "Gender_Id")
    private Gender gender;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Address_Id")
    private Address address;

    @ManyToOne(
        cascade = {
            CascadeType.DETACH, 
            CascadeType.PERSIST, 
            CascadeType.REFRESH, 
            CascadeType.MERGE
        }
    )
    @JoinColumn(name = "Section_Id")
    private Section section;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Image_Id")
    private ProfileImage profileImage;

}
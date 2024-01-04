package com.sdu.usermanagement.repository;
import com.sdu.usermanagement.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer>{

    List<User> findBySectionSectId(Integer sectId);
    @Modifying
    @Query(value = "UPDATE user SET Email = :email WHERE User_Id = :userId", nativeQuery = true)
    void updateUserEmail(@Param("email") String email,@Param("userId") Integer userId);
    
    @Query(value = "SELECT COUNT(*) FROM user u JOIN gender g ON u.Gender_Id = g.Gender_Id WHERE u.Gender_Id= :genderId", nativeQuery = true)
    long countByGender(@Param("genderId") Integer genderId);

    @Query(value = "SELECT u.*, g.*, s.*, a.*, d.* " +
        "FROM user u " +
        "JOIN section s ON u.Section_Id = s.Section_Id " +
        "JOIN department d ON s.Dept_Id = d.Dept_Id " +
        "JOIN gender g ON u.Gender_Id = g.Gender_Id " +
        "JOIN address a ON u.Address_Id = a.Address_Id " +
        "WHERE d.Dept_Id = :deptId",
        nativeQuery = true)
    List<User> findAllDepartmentUser(@Param("deptId") Integer deptId);
    
}
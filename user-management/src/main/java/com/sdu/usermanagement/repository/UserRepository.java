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
    
}
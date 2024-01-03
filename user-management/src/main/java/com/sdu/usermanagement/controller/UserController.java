package com.sdu.usermanagement.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sdu.usermanagement.dto.UserDTO;
import com.sdu.usermanagement.service.UserService;

@RestController
@RequestMapping("v1/api")
public class UserController {
    
    @Autowired
    private UserService userService;

    // Adding Department
    @PostMapping("/users")
    public ResponseEntity<String> addUser(
        @RequestPart(name = "user", required = true) UserDTO userDTO,
        @RequestPart(value = "profileImageFile", required = false) MultipartFile profileImageFile) {
        return userService.save(userDTO, profileImageFile);
       
    }
    
    @GetMapping("/users")
    private ResponseEntity<List<UserDTO>> getAllUser() {
        return userService.findAllUser();
    }
    
    @GetMapping("/users/total")
    private ResponseEntity<Long> getAllUserCount() {
        return userService.getTotalUserCount();
    }
    
    @GetMapping("/users/total/{genderId}")
    private ResponseEntity<Long> getUserByGender(@PathVariable Integer genderId) {
        return userService.getUserCountByGender(genderId);
    }
    
    
    /* Retrieve single user */
    @GetMapping("/users/{user_id}")
    private ResponseEntity<UserDTO> getUserById(@PathVariable Integer user_id){
       return userService.findUserById(user_id);
    }
    /* Delete user */
    @DeleteMapping("users/{user_id}")
    private ResponseEntity<String> deleteUser(@PathVariable Integer user_id){
        return userService.deleteUser(user_id);
    }

    // Updating the department
    // @PutMapping("/users")
    // private ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO){
    //     return userService.(userDTO);
    // }
    /* Native Query */
    @PutMapping("users/email")
    private ResponseEntity<String> updateUserEmail(@RequestBody UserDTO userDTO){
        return userService.updateEmail(userDTO.getEmail(), userDTO.getUserId());
    }

    @GetMapping("/sections/{sect_id}/users")
    private ResponseEntity<List<UserDTO>> getSectionUsers(@PathVariable Integer sect_id){
        return userService.getAllUserBySectionId(sect_id);
    }

}

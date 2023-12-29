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
import org.springframework.web.bind.annotation.RestController;

import com.sdu.usermanagement.dto.UserDTO;
import com.sdu.usermanagement.service.UserService;

@RestController
@RequestMapping("v1/api")
public class UserController {
    
    @Autowired
    private UserService userService;

    // Adding Department
    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }
    
    @GetMapping("/users")
    private ResponseEntity<List<UserDTO>> getAllUser() {
        return userService.findAllUser();
    }

    /* Retrieve single user */
    @GetMapping("/users/{user_id}")
    private ResponseEntity<UserDTO> getDepartmentById(@PathVariable Integer user_id){
       return userService.findUserById(user_id);

    }
    /* Delete user */
    @DeleteMapping("users/{user_id}")
    private ResponseEntity<String> deleteUser(@PathVariable Integer user_id){
        return userService.deleteUser(user_id);
    }

    // Updating the department
    @PutMapping("/users")
    private ResponseEntity<String> updateDepartment(@RequestBody UserDTO userDTO){
        return userService.save(userDTO);
    }
    

}

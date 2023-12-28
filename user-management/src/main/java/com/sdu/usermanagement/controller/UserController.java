package com.sdu.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        try {
            // userDTO.setUserId(0);
            System.out.println("User DTO: " + userDTO);
            UserDTO savedUser = userService.save(userDTO);

            // Return a ResponseEntity with the saved department and HTTP status 201 (Created)
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
            // return null;

        } catch (Exception e) {
            // Handle any exceptions that might occur during the save operation
            System.out.println("An error occurred while processing the request: " +e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/users")
    private ResponseEntity<List<UserDTO>> getAllUser() {

        try{
            // Create the list of department DTO
            List<UserDTO> users = userService.findAllUser();

            // Return the list with HTTP status 200 (OK)
            return ResponseEntity.ok(users);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
    }

    /* Retrieve single user */
    @GetMapping("/users/{user_id}")
    private ResponseEntity<UserDTO> getDepartmentById(@PathVariable int user_id){
        try{
            UserDTO userDTO = userService.findUserById(user_id);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    /* Delete user */
    @DeleteMapping("users/{user_id}")
    private ResponseEntity<UserDTO> deleteUser(@PathVariable int user_id){
        try{
            UserDTO userDTO = userService.findUserById(user_id);
            userService.deleteUser(user_id);

            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Updating the department
    @PutMapping("/users")
    private ResponseEntity<UserDTO> updateDepartment(@RequestBody UserDTO userDTO){
        try{
            UserDTO updatedUser = userService.save(userDTO);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    

}

package com.sdu.usermanagement.service;
import com.sdu.usermanagement.dto.UserDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;


public interface UserService {
    /* Add user */
    ResponseEntity<String> save(UserDTO userDTO);

    /* List all user */
    ResponseEntity<List<UserDTO>> findAllUser();

    /* Get User by User Id */
    ResponseEntity<UserDTO> findUserById(Integer user_id);

    /* Delete the user */
    ResponseEntity<String> deleteUser(Integer user_id);

}

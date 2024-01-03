package com.sdu.usermanagement.service;
import com.sdu.usermanagement.dto.UserDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {
    /* Add user */
    ResponseEntity<String> save(UserDTO userDTO, MultipartFile profileImageFile);

    /* List all user */
    ResponseEntity<List<UserDTO>> findAllUser();

    /* Get User by User Id */
    ResponseEntity<UserDTO> findUserById(Integer user_id);

    /* Delete the user */
    ResponseEntity<String> deleteUser(Integer user_id);

    ResponseEntity<String> updateEmail(String email, Integer user_id);
    
    ResponseEntity<Long> getTotalUserCount();
    
    ResponseEntity<Long> getUserCountByGender(Integer genderId);

    ResponseEntity<List<UserDTO>> getAllUserBySectionId(Integer sect_id);
    

}

package com.sdu.usermanagement.service;


import com.sdu.usermanagement.dto.UserDTO;

import java.util.List;


public interface UserService {
    /* Add user */
    UserDTO save(UserDTO userDTO);

    /* List all user */
    List<UserDTO> findAllUser();

    /* Get User by User Id */
    UserDTO findUserById(int user_id);

    /* Delete the user */
    void deleteUser(int user_id);

}

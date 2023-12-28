package com.sdu.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import com.sdu.usermanagement.dto.UserDTO;
import com.sdu.usermanagement.model.User;
import com.sdu.usermanagement.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;


@Service
 @Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
   
    public UserDTO save(UserDTO userDTO) {
        // TODO Auto-generated method stub
        
        userDTO.setGender(entityManager.merge(userDTO.getGender()));
        userDTO.setSection(entityManager.merge(userDTO.getSection()));
        User user = userRepository.save(userDtoToEntity(userDTO));  

        return userEntityToDto(user);
    }

    /* Method to convert User Entity to User DTO */
    private UserDTO userEntityToDto(User user){
        UserDTO userDTO = new UserDTO();
        
        userDTO.setUserId(user.getUserId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobileNo(user.getMobileNo());
        userDTO.setAddress(user.getAddress());
        userDTO.setGender(user.getGender());
        userDTO.setSection(user.getSection());

        return userDTO;
    }

    private User userDtoToEntity(UserDTO userDTO){
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobileNo(userDTO.getMobileNo());
        user.setAddress(userDTO.getAddress());
        user.setGender(userDTO.getGender());
        user.setSection(userDTO.getSection());
        return user;
    }
    /* Method to convert User DTO to User Entity */

    @Override
    public List<UserDTO> findAllUser() {
        // TODO Auto-generated method stub
         return userRepository.findAll().stream().map(this::userEntityToDto).collect(Collectors.toList());
        
    }

    @Override
    public UserDTO findUserById(int user_id) {
        // TODO Auto-generated method stub
        Optional<User> result = userRepository.findById(user_id);
        User user = null;
        if(result.isPresent()){
            user = result.get();
        }
        else{
            throw new RuntimeException("Did not found user with id: " + user_id);
        }
        return userEntityToDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(int user_id){
        userRepository.deleteById(user_id);
    }
    
}

package com.sdu.usermanagement.service;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdu.usermanagement.dto.UserDTO;
import com.sdu.usermanagement.model.Gender;
import com.sdu.usermanagement.model.ProfileImage;
import com.sdu.usermanagement.model.User;
import com.sdu.usermanagement.repository.GenderRepository;
import com.sdu.usermanagement.repository.SectionRepository;
import com.sdu.usermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import com.sdu.usermanagement.model.Section;


@Service
@Transactional
public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private SectionRepository sectionRepository;

    private final String FOLDER_PATH ="/home/thukten/Desktop/Spring-Boot/user-management/src/main/resources/UserProfile/";


    @Override
    public ResponseEntity<String> save(UserDTO userDTO, MultipartFile profileImageFile) {

        String filePath = FOLDER_PATH + profileImageFile.getOriginalFilename();
      
        try{
            if(userDTO.getGender() == null){
                return new ResponseEntity<>("Missing Gender Parameter", HttpStatus.BAD_REQUEST);
            }
            if(userDTO.getSection() == null){
                return new ResponseEntity<>("Missing Section Parameter", HttpStatus.BAD_REQUEST);
            }
            Gender gender = genderRepository.findById(userDTO.getGender().getGenderId()).orElse(null);
            if (gender == null) {
                return new ResponseEntity<>("Gender not found", HttpStatus.BAD_REQUEST);
            }
            Section section = sectionRepository.findById(userDTO.getSection().getSectId()).orElse(null);
            
            if (section == null) {
                return new ResponseEntity<>("Section not found", HttpStatus.BAD_REQUEST);
            }
            

            /* GET profile  */

            ProfileImage profileImage = null;
            if(profileImageFile != null){
                profileImage = ProfileImage.builder()
                .imageName(profileImageFile.getOriginalFilename())
                .imageType(profileImageFile.getContentType())
                .imagePath(filePath)
                .build();
            }
            profileImageFile.transferTo(new File(filePath));
            
            
            User user = userDtoToEntity(userDTO);
            user.setGender(gender);
            user.setSection(section);
            user.setProfileImage(profileImage);

            User saveUser = userRepository.saveAndFlush(user);  
            System.out.println("Save user: " + saveUser);

            if(saveUser== null){
                // log the error 
                logger.info("Saved user is null! Error while saving user");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e){
            // log the error
            logger.error("Error while saving user: ", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAllUser() {
       try{
            List<UserDTO> users = userRepository.findAll().stream().map(this::userEntityToDto).collect(Collectors.toList());
            if(users== null){
                /* Log the error */
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Error while retrieving all user: ", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @Override
    public ResponseEntity<UserDTO> findUserById(Integer user_id) {
        try{
            /* Bad Request */
            if (user_id == null || user_id < 0) {
                /* Bad Request - Invalid user_id format */
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Optional<User> result = userRepository.findById(user_id);
            UserDTO userDTO = null;
            if(result.isPresent()){
                userDTO = userEntityToDto(result.get());
            }
            else{
                /* user not found */
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            /* Succesful  */
            return new ResponseEntity<>(userDTO,HttpStatus.OK);
        }
        catch(Exception e){
            /* Log the errrpr */
            logger.error("Error while retrieving user by id : ", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
    }

    @Override
    public ResponseEntity<String> deleteUser(Integer user_id){
        try{
            if(user_id == null || user_id < 0){
                /* log the error */
                return new ResponseEntity<>("Invalid user_id format", HttpStatus.BAD_REQUEST);
            }
            if (!userRepository.existsById(user_id)) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            userRepository.deleteById(user_id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            /* Log the error */
            logger.error("Error while deleting user : ", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
        /* Method to convert User Entity to User DTO */
    private UserDTO userEntityToDto(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setCidNo(user.getCidNo());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobileNo(user.getMobileNo());
        userDTO.setDob(user.getDob());
        userDTO.setAddress(user.getAddress());
        userDTO.setGender(user.getGender());
        userDTO.setSection(user.getSection());
        userDTO.setProfileImage(user.getProfileImage());

        return userDTO;
    }

    /* Method to convert User DTO to User Entity */
    private User userDtoToEntity(UserDTO userDTO){
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setCidNo(userDTO.getCidNo());
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobileNo(userDTO.getMobileNo());
        user.setDob(userDTO.getDob());
        user.setAddress(userDTO.getAddress());
        user.setGender(userDTO.getGender());
        user.setSection(userDTO.getSection());
        user.setProfileImage(userDTO.getProfileImage());
        return user;
    }

    @Override
    public ResponseEntity<String> updateEmail(String email, Integer user_id) {

        try{
            if(email == null || user_id == null || user_id < 0){
                return new ResponseEntity<>("Invalid/Null Email and User Id", HttpStatus.BAD_REQUEST);
            }
            userRepository.updateUserEmail(email, user_id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  
    
}

package com.sdu.usermanagement.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.sdu.usermanagement.model.ProfileImage;
import com.sdu.usermanagement.model.User;
import com.sdu.usermanagement.repository.ProfileImageRepository;
import com.sdu.usermanagement.repository.UserRepository;


@Service
public class ProfileImageServiceImpl implements ProfileImageServie{

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Autowired
    private UserRepository userRepository;


    private final String FOLDER_PATH ="/home/thukten/Desktop/Spring-Boot/user-management/src/main/resources/UserProfile/";


    @Override
    public ResponseEntity<String> uploadImage(Integer user_id, MultipartFile profileImageFile) {

        String filePath = FOLDER_PATH + profileImageFile.getOriginalFilename();
        user_id = Integer.valueOf(user_id);
        
        try{    
            if(user_id == null || profileImageFile == null){
                return new ResponseEntity<>("Invalid user id or no image file", HttpStatus.BAD_REQUEST);
            }
            if(!userRepository.existsById(user_id)){
                return new ResponseEntity<>("Users not found!", HttpStatus.NOT_FOUND);
            }

            Optional<User> result = userRepository.findById(user_id);
            User user = null;
            if(result.isPresent()){
                user = result.get();
            }
            else{
                /* user not found */
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // get the image id
            int image_id = user.getProfileImage().getImageId();
            String existingProfileImagePath = FOLDER_PATH + user.getProfileImage().getImageName();
            /* Get the image */
            ProfileImage profileImage = null;
            if(profileImageFile != null){
                profileImage = ProfileImage.builder()
                .imageId(image_id)
                .imageName(profileImageFile.getOriginalFilename())
                .imageType(profileImageFile.getContentType())
                .imagePath(filePath)
                .build();
            }
             /* Delete the existing file */
            Files.deleteIfExists(Paths.get(existingProfileImagePath));
            /* Update the profile image profile */
            ProfileImage uploadedProfile  = profileImageRepository.save(profileImage); 
            
            profileImageFile.transferTo(new File(filePath));
            /* Updated the user table */
            // user.setProfileImage(profileImage);
            if(uploadedProfile == null){
                return new ResponseEntity<>("Uploaded Profile is null",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
            return new ResponseEntity<>(HttpStatus.CREATED);

        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<byte[]> getProfileImage(Integer user_id) {
        try {
            Optional<User> result = userRepository.findById(user_id);
            User user = null;

            if(result != null){
                user = result.get();
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String filePath = user.getProfileImage().getImagePath();
            
            byte[] images = Files.readAllBytes(Paths.get(filePath));

            if(images == null){
                // Cannot read image byte
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            HttpHeaders headers = new HttpHeaders();
        
            String imageType = user.getProfileImage().getImageType();

            if ("image/png".equals(imageType)) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } 
            else if ("image/jpeg".equals(imageType)) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } 
            else if ("image/gif".equals(imageType)) {
                headers.setContentType(MediaType.IMAGE_GIF);
            } 
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(images, headers, HttpStatus.OK);
           
        } catch (IOException e) {
            // Handle the IOException
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
}

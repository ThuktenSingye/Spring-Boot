package com.sdu.usermanagement.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.sdu.usermanagement.utility.FileNameGenerator;
import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class ProfileImageServiceImpl implements ProfileImageServie{

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileNameGenerator fileNameGenerator;


    @Value("${user-profile.upload-dir}")
    private String FOLDER_PATH;


    @Override
    public ResponseEntity<String> uploadImage(Integer user_id, MultipartFile profileImageFile) {
        String filePath = Paths.get(FOLDER_PATH, fileNameGenerator.generateUniqueFileName(profileImageFile.getOriginalFilename())).toString();
        log.info("File path:"+ filePath);
        user_id = Integer.valueOf(user_id);
        
        try{    
            if(user_id == null || profileImageFile == null){
                return new ResponseEntity<>("Invalid user id or no image file", HttpStatus.BAD_REQUEST);
            }
            if(!userRepository.existsById(user_id)){
                return new ResponseEntity<>("Users not found!", HttpStatus.NOT_FOUND);
            }

            User user = userRepository.findById(user_id).orElseThrow();
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
            ProfileImage uploadedProfile  = profileImageRepository.saveAndFlush(profileImage); 
            
            profileImageFile.transferTo(new File(filePath));
            // user.setProfileImage(profileImage);
            if(uploadedProfile == null){
                return new ResponseEntity<>("Save or Uploaded Profile is null",HttpStatus.INTERNAL_SERVER_ERROR);
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
            User user = userRepository.findById(user_id).orElseThrow();

            String filePath = user.getProfileImage().getImagePath();
            log.info("File path: " + filePath);
            
            byte[] images = Files.readAllBytes(Paths.get(filePath));

            if(images == null){
                // Cannot read image byte
                log.info("Read image file is null");
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

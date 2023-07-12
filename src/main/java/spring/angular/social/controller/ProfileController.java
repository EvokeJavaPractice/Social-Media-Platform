package spring.angular.social.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import spring.angular.social.entity.Profile;
import spring.angular.social.entity.ProfileImage;
import spring.angular.social.service.ProfileService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping("/{profileId}")
    public ResponseEntity<Profile> getProfile(@PathVariable Long profileId) {
   
        Profile profile = profileService.getProfile(profileId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Profile> getProfileByUserId(@PathVariable Long userId) {
        Profile profile = profileService.getProfileByUserId(userId);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        Profile createdProfile = profileService.createProfile(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long profileId, @RequestBody Profile profile) {
        Profile updatedProfile = profileService.updateProfile(profileId, profile);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long profileId) {
        profileService.deleteProfile(profileId);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{profileId}/uploadImage")
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable Long profileId,
            @RequestParam("file") MultipartFile file) {
        String response = profileService.uploadImage(profileId, file);
		return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    @GetMapping("/getImageUrl/{sid}")
    public ResponseEntity<String> getImageUrl(@PathVariable Long sid){
    	System.out.println(sid);
    	return new ResponseEntity<String>(profileService.getProfileImageUrl(sid),HttpStatus.OK);
    	
    }
    @DeleteMapping("/removeImage/{profileId}")
    public String deleteImage(@PathVariable Long profileId) {
     	System.out.println(profileId);
    	profileService.deleteImage(profileId);
        return "Image Deleted";
    }
}

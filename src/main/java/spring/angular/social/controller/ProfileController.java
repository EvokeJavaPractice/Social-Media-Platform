package spring.angular.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.angular.social.entity.Profile;
import spring.angular.social.service.ProfileService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

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

    @PostMapping("/{profileId}/image")
    public ResponseEntity<Profile> uploadProfileImage(@PathVariable Long profileId,
                                                     @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(profileService.uploadImage(profileId, file), HttpStatus.OK);
    }

    @DeleteMapping("/{profileId}/image")
    public ResponseEntity<Object> deleteImage(@PathVariable Long profileId) {
        profileService.deleteImage(profileId);
        return ResponseEntity.noContent().build();
    }
}

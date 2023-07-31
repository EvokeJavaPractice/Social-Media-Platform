package spring.angular.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.angular.social.dto.ProfileDto;
import spring.angular.social.entity.Profile;
import spring.angular.social.mappers.ProfileMapper;
import spring.angular.social.service.ProfileService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileMapper mapper;

    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long profileId) {

        Profile profile = profileService.getProfile(profileId);
        return ResponseEntity.ok(mapper.toDto(profile));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ProfileDto> getProfileByUserId(@PathVariable Long userId) {
        Profile profile = profileService.getProfileByUserId(userId);
        if (profile != null) {
            return ResponseEntity.ok(mapper.toDto(profile));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProfileDto> createProfile(@RequestBody ProfileDto profileDto) {
        Profile createdProfile = profileService.createProfile(mapper.toEntity(profileDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(createdProfile));
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable Long profileId, @RequestBody ProfileDto profileDto) {
        Profile updatedProfile = profileService.updateProfile(profileId, mapper.toEntity(profileDto));
        return ResponseEntity.ok(mapper.toDto(updatedProfile));
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long profileId) {
        profileService.deleteProfile(profileId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{profileId}/image")
    public ResponseEntity<ProfileDto> uploadProfileImage(@PathVariable Long profileId,
                                                     @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(mapper.toDto(profileService.uploadImage(profileId, file)), HttpStatus.OK);
    }

    @GetMapping("/{profileId}/image")
    public ResponseEntity<String> getImageUrl(@PathVariable Long profileId) {

        Profile profile = profileService.getProfile(profileId);
        String profileImageUrl= profile.getProfileImage();
        return new ResponseEntity<String>(profileImageUrl,HttpStatus.OK);

    }

    @DeleteMapping("/{profileId}/image")
    public ResponseEntity<Object> deleteImage(@PathVariable Long profileId) {
        profileService.deleteImage(profileId);
        return ResponseEntity.noContent().build();
    }
}

package spring.angular.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.angular.social.entity.Profile;
import spring.angular.social.exception.ProfileNotFoundException;
import spring.angular.social.repository.ProfileRepository;

import java.util.Optional;


@Service
public class ProfileService {


    @Autowired
    private AWSStorageService storageService;

    @Autowired
    private ProfileRepository profileRepository;


    public Profile getProfile(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with ID: " + profileId));
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Long profileId, Profile updatedProfile) {
        Profile existingProfile = profileRepository.findById(profileId)
                .orElseThrow(()
                        -> new ProfileNotFoundException("Profile not found with ID: " + profileId));

        existingProfile.setFullName(updatedProfile.getFullName());
        existingProfile.setBio(updatedProfile.getBio());

        return profileRepository.save(existingProfile);
    }

    public void deleteProfile(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with ID: " + profileId));

        profileRepository.delete(profile);
    }

    public Profile getProfileByUserId(Long userId) {
        Optional<Profile> optionalProfile = profileRepository.findByUserId(userId);
        if (optionalProfile.isPresent()) {
            return optionalProfile.get();
        } else {
            throw new RuntimeException("Profile not found for user ID: " + userId);
        }
    }

    public Profile uploadImage(Long profileId, MultipartFile file) {
        Optional<Profile> profile = profileRepository.findById(profileId);
        if (profile.isEmpty()) {
            throw new ProfileNotFoundException("Profile not found with ID: " + profileId);
        }
        String imageUrl = storageService.save(file);
        profile.get().setProfileImage(imageUrl);
        profileRepository.save(profile.get());
        return profile.get();
    }


    public void deleteImage(Long profileId) {
        Optional<Profile> profile = profileRepository.findById(profileId);
        if (profile.isEmpty()) {
            throw new ProfileNotFoundException("Profile not found with ID: " + profileId);
        }
        storageService.deleteImage(profile.get().getProfileImage());
        profile.get().setProfileImage(null);
    }

}

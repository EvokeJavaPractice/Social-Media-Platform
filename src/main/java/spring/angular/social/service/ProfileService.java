package spring.angular.social.service;
import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import spring.angular.social.entity.Profile;
import spring.angular.social.exception.ProfileNotFoundException;
import spring.angular.social.repository.ProfileRepository;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile getProfile(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with ID: " + profileId));
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Long profileId, Profile updatedProfile) {
        Profile existingProfile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with ID: " + profileId));

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
        // Assuming you have a repository for the Profile entity, e.g., ProfileRepository
        Optional<Profile> optionalProfile = profileRepository.findByUserId(userId);

        if (optionalProfile.isPresent()) {
            return optionalProfile.get();
        } else {
            throw new RuntimeException("Profile not found for user ID: " + userId);
        }
    }

}

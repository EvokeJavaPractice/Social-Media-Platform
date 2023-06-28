package spring.angular.social.serviceTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import spring.angular.social.entity.Profile;
import spring.angular.social.exception.ProfileNotFoundException;
import spring.angular.social.repository.ProfileRepository;
import spring.angular.social.service.ProfileService;

public class ProfileServiceTest {

    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        profileService = new ProfileService(profileRepository);
    }

    @Test
    public void testGetProfile_success() {
        
        Long profileId = 1L;
        Profile profile = new Profile();
        profile.setId(profileId);

        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));

        Profile result = profileService.getProfile(profileId);

        verify(profileRepository, times(1)).findById(profileId);
        assertEquals(profile, result);
    }

    @Test(expected = ProfileNotFoundException.class)
    public void testGetProfile_failure() {
        
        Long profileId = 1L;

        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());
        profileService.getProfile(profileId);
    }

    @Test
    public void testCreateProfile() {
        
        Profile profile = new Profile();

        when(profileRepository.save(profile)).thenReturn(profile);

        Profile result = profileService.createProfile(profile);

        verify(profileRepository, times(1)).save(profile);
        assertEquals(profile, result);
    }

    @Test
    public void testUpdateProfile_success() {
        
        Long profileId = 1L;
        Profile existingProfile = new Profile();
        existingProfile.setId(profileId);
        existingProfile.setFullName("Bhavani");
        existingProfile.setBio("I'm Bhavani DASARI");
        
        Profile updatedProfile = new Profile();
        updatedProfile.setFullName("Bhavu");
        updatedProfile.setBio("I'm Bhavau Dasari");

        when(profileRepository.findById(profileId)).thenReturn(Optional.of(existingProfile));
        when(profileRepository.save(existingProfile)).thenReturn(existingProfile);

        Profile result = profileService.updateProfile(profileId, updatedProfile);

        verify(profileRepository, times(1)).findById(profileId);
        verify(profileRepository, times(1)).save(existingProfile);

        assertEquals(updatedProfile.getFullName(), result.getFullName());
        assertEquals(updatedProfile.getBio(), result.getBio());
    }

    @Test(expected = ProfileNotFoundException.class)
    public void testUpdateProfile_failure() {
       
        Long profileId = 1L;
        Profile updatedProfile = new Profile();

        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        profileService.updateProfile(profileId, updatedProfile);
    }

    @Test
    public void testDeleteProfile_success() {
        
        Long profileId = 1L;
        Profile profile = new Profile();
        profile.setId(profileId);

        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));

        profileService.deleteProfile(profileId);

        verify(profileRepository, times(1)).findById(profileId);
        verify(profileRepository, times(1)).delete(profile);
    }

    @Test(expected = ProfileNotFoundException.class)
    public void testDeleteProfile_failure() {
       
        Long profileId = 1L;

        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        profileService.deleteProfile(profileId);
    }


}


package spring.angular.social.controllerTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import spring.angular.social.controller.ProfileController;
import spring.angular.social.entity.Profile;
import spring.angular.social.service.ProfileService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProfileControllerTest {

    private final ProfileService profileService = mock(ProfileService.class);
    private final ProfileController profileController = new ProfileController(profileService);

    @Test
    public void testGetProfile() {
    	
    	Long profileId = 1L;
        Profile profile = new Profile();
        when(profileService.getProfile(profileId)).thenReturn(profile);
        
        ResponseEntity<Profile> expectedResult = profileController.getProfile(profileId);
        
        assertEquals(expectedResult.getStatusCode(),HttpStatus.OK);
        ;
        
        
    }
    
    @Test
    public void testGetProfileByUserId_success() {
    	
    	Long userId=1L;
        Profile profile = new Profile();
        when(profileService.getProfileByUserId(userId)).thenReturn(profile);
        
        ResponseEntity<Profile> expectedResult = profileController.getProfileByUserId(userId);
        
        assertEquals(expectedResult.getStatusCode(),HttpStatus.OK);
        assertEquals(expectedResult.getBody(),profile);
        
        
    }
    
    @Test
    public void testGetProfileByUserId_failure() {
    	
    	Long userId=1L;
        Profile profile = null;
        when(profileService.getProfileByUserId(userId)).thenReturn(profile);
        
        ResponseEntity<Profile> expectedResult = profileController.getProfileByUserId(userId);
        
        assertEquals(expectedResult.getStatusCode(),HttpStatus.NOT_FOUND);
        assertEquals(expectedResult.getBody(),null);
        
        
    }
    
    @Test
    public void testCreateProfile() {
    	
    	Profile profile = new Profile();
    	when(profileService.createProfile(profile)).thenReturn(profile);
        
    	ResponseEntity<Profile> expectedResult = profileController.createProfile(profile);
    	
    	assertEquals(expectedResult.getStatusCode(),HttpStatus.CREATED);
    	assertEquals(expectedResult.getBody(),profile);
        
    }
    
    @Test
    public void testUpdateProfile() {
    	
    	Long profileId = 1L;
    	Profile profile = new Profile();
    	
    	when(profileService.updateProfile(profileId, profile)).thenReturn(profile);
    	ResponseEntity<Profile>  expectedResult = profileController.updateProfile(profileId, profile) ;
        
    	assertEquals(expectedResult.getStatusCode(),HttpStatus.OK);
    	assertEquals(expectedResult.getBody(),profile);
    	
    }
    
    @Test
    public void deleteProfile() {
    	
    	Long profileId = 1L;
    	
    	ResponseEntity<Void> expectedResult = profileController.deleteProfile(profileId);
    	
    	assertEquals(expectedResult.getBody(),null);
    	verify(profileService, times(1)).deleteProfile(profileId);
    	
    }
}


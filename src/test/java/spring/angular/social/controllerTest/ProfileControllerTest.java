package spring.angular.social.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import spring.angular.social.entity.Profile;
import spring.angular.social.service.ProfileService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileControllerTest {

    @MockBean
    private ProfileService profileService;

    @Autowired
    MockMvc mockMvc;


    @Test
    public void testGetProfile() throws Exception {
        Long profileId = 1L;
        Profile profile = new Profile();
        doReturn(profile).when(profileService).getProfile(profileId);

        mockMvc.perform(get("/profiles/{id}", profileId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(profile)));
    }


    @Test
    public void testGetProfileByUserId_success() throws Exception {

        Long userId = 1L;
        Profile profile = new Profile();
        doReturn(profile).when(profileService).getProfileByUserId(userId);

        mockMvc.perform(get("/profiles/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(profile)));
    }

    @Test
    public void testCreateProfile() throws Exception {
        Profile profile = new Profile();
        doReturn(profile).when(profileService).createProfile(profile);

        mockMvc.perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(profile)))
                .andExpect(status().isCreated())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(profile)));
    }

    @Test
    public void testUpdateProfile() throws Exception {
        Long profileId = 1L;
        Profile profile = new Profile();
        doReturn(profile).when(profileService).updateProfile(profileId, profile);

        mockMvc.perform(put("/profiles/{id}", profileId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(profile)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(profile)));
    }

    @Test
    public void deleteProfile() throws Exception {
        Long profileId = 1L;
        mockMvc.perform(delete("/profiles/{id}", profileId))
                .andExpect(status().isNoContent());
        verify(profileService, times(1)).deleteProfile(profileId);
    }
}


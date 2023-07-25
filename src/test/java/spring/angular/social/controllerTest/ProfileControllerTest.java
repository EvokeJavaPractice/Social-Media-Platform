package spring.angular.social.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spring.angular.social.controller.ProfileController;
import spring.angular.social.dto.ProfileDto;
import spring.angular.social.entity.Profile;
import spring.angular.social.mappers.ProfileMapper;
import spring.angular.social.service.ProfileService;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.is;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

    @MockBean
    private ProfileService profileService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProfileMapper mapper;


    public Profile getProfile(){
        Profile profile=new Profile();
        profile.setId(1L);
        profile.setBio("YOLO");
        profile.setFullName("Manasa Lagishetty");
        return profile;
    }
    public ProfileDto getProfileDto(){
        ProfileDto profileDto=new ProfileDto();
        profileDto.setId(1L);
        profileDto.setBio("YOLO");
        profileDto.setFullName("Manasa Lagishetty");
        return  profileDto;
    }


    @Test
    public void testGetProfile() throws Exception {
        Long profileId = 1L;

        Mockito.when(profileService.getProfile(profileId)).thenReturn(getProfile());
        Mockito.when(mapper.toDto(any(Profile.class))).thenReturn(getProfileDto());
        Mockito.when(mapper.toEntity(any(ProfileDto.class))).thenReturn(getProfile());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/api/profiles/{profileId}", profileId)
                        .content(new ObjectMapper().writeValueAsString(getProfileDto()));

        mockMvc.perform(mockHttpServletRequestBuilder)
                       .andExpect(status().isOk())
                       .andExpect(jsonPath("$", notNullValue()))
                       .andExpect(jsonPath("$.bio", is("YOLO")));
    }


    @Test
    public void testGetProfileByUserId() throws Exception {

        Long userId = 1L;
        Mockito.when(profileService.getProfileByUserId(userId)).thenReturn(getProfile());
        Mockito.when(mapper.toDto(any(Profile.class))).thenReturn(getProfileDto());
        Mockito.when(mapper.toEntity(any(ProfileDto.class))).thenReturn(getProfile());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/api/profiles/user/{userId}", userId)
                        .content(new ObjectMapper().writeValueAsString(getProfileDto()));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.fullName",is("Manasa Lagishetty")));
    }

    @Test
    public void testCreateProfile() throws Exception {
        Mockito.when(profileService.createProfile(any(Profile.class))).thenReturn(getProfile());
        Mockito.when(mapper.toDto(any(Profile.class))).thenReturn(getProfileDto());
        Mockito.when(mapper.toEntity(any(ProfileDto.class))).thenReturn(getProfile());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.post("/api/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getProfileDto()));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.fullName",is("Manasa Lagishetty")))
                .andExpect(jsonPath("$.bio",is("YOLO")));
    }

    @Test
    public void testUpdateProfile() throws Exception {
        Long profileId = 1L;

        when(profileService.updateProfile(any(Long.class),any(Profile.class))).thenReturn(getProfile());
        Mockito.when(mapper.toDto(any(Profile.class))).thenReturn(getProfileDto());
        Mockito.when(mapper.toEntity(any(ProfileDto.class))).thenReturn(getProfile());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.put("/api/profiles/{profileId}",profileId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getProfileDto()));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.bio",is("YOLO")))
                .andExpect(jsonPath("$.fullName",is("Manasa Lagishetty")));
    }

    @Test
    public void deleteProfile() throws Exception {
        Long profileId = 1L;

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.delete("/api/profiles/{profileId}",profileId);
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isNoContent());

              verify(profileService, times(1)).deleteProfile(profileId);
    }
}


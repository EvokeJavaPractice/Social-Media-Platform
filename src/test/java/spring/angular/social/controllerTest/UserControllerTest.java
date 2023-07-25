package spring.angular.social.controllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spring.angular.social.controller.UserController;
import spring.angular.social.dto.UserDto;
import spring.angular.social.entity.User;
import spring.angular.social.mappers.UserMapper;
import spring.angular.social.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserMapper mapper;

    public User getUser() {
        User user = new User(
                1L, "surya@gmail.com", "Surya", "password");
        return user;
    }

    public UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername("Manasa");
        userDto.setEmailId("Manasa@gmail.com");
        userDto.setPassword("password");
        return userDto;
    }

    @Test
    public void testSaveUser() throws Exception {

        when(userService.save(any(User.class))).thenReturn(getUser());
        when(mapper.toEntity(any(UserDto.class))).thenReturn(getUser());
        when(mapper.toDto(any(User.class))).thenReturn(getUserDto());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(getUserDto());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonString);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.username", is("Manasa")))
                .andExpect(jsonPath("$.emailId", is("Manasa@gmail.com")));

    }


    @Test
    public void testGetUser() throws Exception {
        when(userService.getUser(any(User.class))).thenReturn(getUser());
        when(mapper.toEntity(any(UserDto.class))).thenReturn(getUser());
        when(mapper.toDto(any(User.class))).thenReturn(getUserDto());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(getUserDto());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonString);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.username", is("Manasa")))
                .andExpect(jsonPath("$.password", is("password")));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(getUser());
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(getUserDto());

        when(userService.getAllUsers()).thenReturn(userList);
        when(mapper.toDto(any(List.class))).thenReturn(userDtoList);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(userDtoList);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/api/users")
                        .content(jsonString);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].username", is("Manasa")))
                .andExpect(jsonPath("$[0].password", is("password")))
                .andExpect(jsonPath("$[0].emailId", is("Manasa@gmail.com")));

    }

    @Test
    public void testUserByUserName() throws Exception {
        String username="Manasa";
        when(userService.findByUsername(any(String.class))).thenReturn(getUser());
        when(mapper.toEntity(any(UserDto.class))).thenReturn(getUser());
        when(mapper.toDto(any(User.class))).thenReturn(getUserDto());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(getUserDto());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/api/users/{username}",username)
                        .content(jsonString);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.username", is("Manasa")))
                .andExpect(jsonPath("$.password", is("password")))
                .andExpect(jsonPath("$.emailId", is("Manasa@gmail.com")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Long id=1L;

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.delete("/api/users/"+id);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is("user deleted")));

        verify(userService).delete(id);
    }
}


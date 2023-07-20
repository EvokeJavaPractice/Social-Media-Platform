package spring.angular.social.controllerTest;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.angular.social.controller.UserController;
import spring.angular.social.entity.User;
import spring.angular.social.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;
    
    private UserController userController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService);
    }

    @Test
    public void testSaveUser() {
    	
        User user = new User();
        user.setUsername("Bhavani");
        user.setPassword("Bhavani");

        when(userService.save(user)).thenReturn(user);
        System.out.println(user);
        ResponseEntity<User> response = userController.saveUser(user);
        System.out.println(userController.saveUser(user));
        System.out.println(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());

    }
    @Test
    public void testGetUser(){
    	
    	 User user = new User();
         user.setUsername("Bhavani");
         user.setPassword("Bhavani");
         when(userService.getUser(user)).thenReturn(user);
         
         ResponseEntity<User> expectedRes = userController.getUser(user);
         assertEquals(HttpStatus.OK, expectedRes.getStatusCode());
         assertEquals(user,expectedRes.getBody());
    }
    
    @Test
    public void testGetAllUsers() 
    {
    	User user1 = new User();
    	user1.setUsername("Bhavani");
    	user1.setPassword("Bhavani");
    	
        User user2 = new User();
        user2.setUsername("Bhavu");
        user2.setPassword("Bhavu");
        
        List<User> users = Arrays.asList(user1, user2);
        
    	when(userService.getAllUsers()).thenReturn(users);
    	
    	ResponseEntity<List<User>> expectedRes = userController.getAllUsers();
    	
        assertEquals(HttpStatus.OK,expectedRes.getStatusCode());
        
        assertEquals(users,expectedRes.getBody());
    }
    
    @Test
    public void TestGetUserByUsername() {
    	
    	User user = new User();
    	user.setUsername("Bhavani");
    	user.setPassword("Bhavani");
    	
    	when(userService.findByUsername(user.getUsername())).thenReturn(user);
    	
        ResponseEntity<User> expectedRes = 
        		userController.getUserByUsername(user.getUsername());
        
        assertEquals(HttpStatus.OK,expectedRes.getStatusCode());
        
        assertEquals(user,expectedRes.getBody());
    }
    
    @Test
    public void testDeleteUser()
    {
    	User user = new User();
    	user.setId(1L);
    	user.setUsername("Bhavani");
    	user.setPassword("Bhavani");
    	
        ResponseEntity<String> expectedRes = userController.deleteUser(user.getId());
        
        assertEquals(HttpStatus.OK, expectedRes.getStatusCode());
        
        verify(userService, times(1)).delete(user.getId());
    	
    }

    

}


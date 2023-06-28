package spring.angular.social.serviceTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import spring.angular.social.entity.User;
import spring.angular.social.exception.InvalidPasswordException;
import spring.angular.social.exception.UserNotFoundException;
import spring.angular.social.repository.UserRepository;
import spring.angular.social.service.UserService;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void testFindByUsername() {
       
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);

        User result = userService.findByUsername(username);
        verify(userRepository, times(1)).findByUsername(username);
        assertEquals(user, result);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

       
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();
        verify(userRepository, times(1)).findAll();
        assertEquals(users, result);
    }

    @Test
    public void testSave() {
    	
        User user = new User();
        User savedUser = new User();
        when(userRepository.save(user)).thenReturn(savedUser);

        User result = userService.save(user);

        verify(userRepository, times(1)).save(user);

        assertEquals(savedUser, result);
    }

    @Test
    public void testFindById_success() {
        
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(userId);

        verify(userRepository, times(1)).findById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void testFindById_failure() {
        
        Long userId = 1L;
        
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(userId);

        verify(userRepository, times(1)).findById(userId);
        assertFalse(result.isPresent());
    }

    @Test
    public void testDelete() {
        
        Long userId = 1L;

        userService.delete(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testGetUser_success() {
        
        String username = "bhavani";
        String password = "bhavani";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(user);

        User result = userService.getUser(user);
        
        verify(userRepository, times(1)).findByUsername(username);
        assertEquals(user, result);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUser_InvalidUsername() {
        
        String username = "bhavani";
        String password = "bhavani";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(null);
        
        userService.getUser(user);
    }

    

}

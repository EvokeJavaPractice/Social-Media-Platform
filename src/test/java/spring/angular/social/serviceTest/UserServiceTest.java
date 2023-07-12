package spring.angular.social.servicetest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import spring.angular.social.entity.User;
import spring.angular.social.repository.UserRepository;
import spring.angular.social.service.UserService;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	
	
		
		@InjectMocks
		private UserService userService;
		@Mock
		private UserRepository userRepository;
		
		User user;
		@BeforeEach
		void setUp() {
			user=new User();
			user.setId(34L);
			user.setEmailId("user@gmail.com");
			user.setPassword("user123");
			user.setUsername("user");
			
		}

		@Test
		public void testSaveUser() {

			user.setEmailId("user@gmail.com");
			user.setPassword("user123");
			user.setUsername("user");

			when(userRepository.save(user)).thenReturn(user);

			User savedUser = userService.save(user);

			// Assert
			assertNotNull(savedUser);
			assertEquals(user, savedUser);

	}
	
	  @Test 
	  public void testGetAllUsers() {
		  
			List<User> listOfUsers = new ArrayList<>();
	  
			listOfUsers.add(user);
	  
	  user=new User(); 
	  user.setEmailId("users@gmail.com");
	  user.setUsername("user"); 
	  user.setPassword("password");
	  
	  when(userRepository.findAll()).thenReturn(listOfUsers);
	  List<User>actual = userService.getAllUsers();
	  assertThat(actual).isEqualTo(listOfUsers); 
	  }
	  
		
		  @Test 
		  public void testfindByUsername() {
		  
		  when(userRepository.findByUsername("username")).thenReturn(user);
		  User actual = userService.findByUsername("username");
		  assertThat(actual).isEqualTo(user); 
		  }
		  
		  
		  @Test
		  public void testfindByUserId() {
		  
			  when(userRepository.findById(34L)).thenReturn(Optional.of(user));
			  Optional<User> actual = userService.findById(34L);
			  assertEquals(Optional.of(user), actual);
		  }
		  
			 
			  @Test 
			  public void testDelateUser() {
				  Long userId = 3L;
			  when(userRepository.findById(3L)).thenReturn(Optional.of(user));
			  userService.delete(3L);
			 
			  // Verifying that the userRepository's deleteById method was called with the correct user ID
			   verify(userRepository, times(1)).deleteById(userId);
			  
			  
			  }
			 
		 
	 
	
}

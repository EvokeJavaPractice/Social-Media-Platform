package spring.angular.social.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.angular.social.entity.FriendConnection;
import spring.angular.social.entity.User;
import spring.angular.social.exception.InvalidPasswordException;
import spring.angular.social.exception.UserNotFoundException;
import spring.angular.social.repository.FriendConnectionRepository;
import spring.angular.social.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    
    @Autowired
    private FriendConnectionRepository friendConnectionRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

	public User save(User user) {
		return userRepository.save(user);
	}

	public Optional<User> findById(Long userId) {
		return userRepository.findById(userId);
		}

	public void delete(Long id) {
		 userRepository.deleteById(id);
	}

	public Optional<FriendConnection> findFriendConnectionByUsers(Long userId, Long friendId) {
        return friendConnectionRepository.findByUserIdAndFriendId(userId, friendId);
    }
	public User getUser(User user) {
		String username= user.getUsername();
		String password = user.getPassword();
		User foundUser=findByUsername(username);
		 if (foundUser == null) {
	            throw new UserNotFoundException("Invalid username or password");
	        }

	        if (!foundUser.getPassword().equals(password)) {
	            throw new InvalidPasswordException("Invalid username or password");
	        }
	        return foundUser;
	}
}

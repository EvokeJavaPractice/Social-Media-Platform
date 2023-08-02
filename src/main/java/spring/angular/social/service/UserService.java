package spring.angular.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.angular.social.entity.FriendConnection;
import spring.angular.social.entity.User;
import spring.angular.social.exception.DuplicateAccountException;
import spring.angular.social.exception.InvalidPasswordException;
import spring.angular.social.exception.UserNotFoundException;
import spring.angular.social.repository.FriendConnectionRepository;
import spring.angular.social.repository.UserRepository;
import spring.angular.social.securityRequest.SignupRequest;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendConnectionRepository friendConnectionRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new DuplicateAccountException(" Username already exist");
        }

        // check email exist
        if (userRepository.existsByEmailId(signupRequest.getEmail())) {
            throw new DuplicateAccountException(" EmailId already exist");
        }
        // create user
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        emailService.sendEmail();

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

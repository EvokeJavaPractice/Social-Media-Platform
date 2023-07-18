package spring.angular.social.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import spring.angular.social.entity.User;
import spring.angular.social.entity.UserDetailsImpl;
import spring.angular.social.exception.InvalidCredentialsException;
import spring.angular.social.exception.InvalidTokenException;
import spring.angular.social.repository.UserRepository;
import spring.angular.social.securityRequest.LoginRequest;
import spring.angular.social.securityRequest.SignupRequest;
import spring.angular.social.securityResponse.JwtResponse;
import spring.angular.social.securityResponse.MessageResponse;
import spring.angular.social.service.UserService;
import spring.angular.social.util.JwtUtils;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

//    @PostMapping
//    public ResponseEntity<User> saveUser(@RequestBody User user) {
//        User usr = userService.save(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(usr);
//    }

//    @PostMapping("/login")
//    public ResponseEntity<User> getUser(@RequestBody User user) {
//        User usr = userService.getUser(user);
//        return ResponseEntity.status(HttpStatus.OK).body(usr);
//    }

	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signupRequest) {

		System.out.println("send request from postman" + signupRequest);

		// check username exist
		if (userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse(" Username already exist"));
		}

		// check email exist
		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse(" EmailId already exist"));
		}
		// create user
		User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
				encoder.encode(signupRequest.getPassword()));

		userService.save(user);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			// check for Authentication
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			// set as SecurityContext(Authentication)
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Generate JWT Token
			String jwt = jwtUtils.generateToken(authentication);

			// current user object
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

			// Create response headers and add the JWT token
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Authorization", "Bearer " + jwt);
			responseHeaders.set("Username", userDetails.getUsername());
			responseHeaders.set("Email", userDetails.getEmail());

			// Create the JwtResponse object with the user details
			JwtResponse jwtResponse = new JwtResponse(jwt, // token
					userDetails.getId(), // id
					userDetails.getUsername(), // username
					userDetails.getEmail() // email

			);

			// Return response with the JwtResponse in the body and the JWT token in the
			// headers
			return ResponseEntity.ok().headers(responseHeaders).body(jwtResponse);
		} catch (AuthenticationException ex) {
			throw new InvalidCredentialsException("Invalid User Credentials");
		} catch (JwtException ex) {
			if (ex instanceof SignatureException) {
				throw new InvalidTokenException("Invalid Token Signature");
			} else {
				throw new InvalidTokenException("Invalid Token");
			}

		}
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		User user = userService.findByUsername(username);
		return ResponseEntity.ok(user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.ok("user deleted");
	}

}

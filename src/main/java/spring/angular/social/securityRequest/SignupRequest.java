package spring.angular.social.securityRequest;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequest {

	@NotBlank
	@Size(min = 3, max = 20)
	private String username;
	
	@NotBlank
	@Size(max=50)
	@Email
	private String email;	
	
	@NotBlank
	@Size(min = 4, max = 40)
	private String password;
	
}

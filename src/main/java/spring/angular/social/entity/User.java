package spring.angular.social.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="social_user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	private String emailId;
	 	@Column(nullable = false, unique = true)
	    private String username;
	    @Column(nullable = false)
	    private String password;

}

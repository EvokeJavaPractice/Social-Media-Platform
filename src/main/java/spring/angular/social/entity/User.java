package spring.angular.social.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="social_user")
public class User {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
	 	@Column(nullable = false, unique = true)
	    private String username;
	 	
		private String email;
		
	    @Column(nullable = false)
	    private String password;

		public User(String username, String email, String password) {
			super();
			this.username = username;
			this.email = email;
			this.password = password;
		}
}

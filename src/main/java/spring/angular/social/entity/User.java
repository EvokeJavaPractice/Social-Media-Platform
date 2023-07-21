package spring.angular.social.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="social_user")
@NoArgsConstructor
public class User {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
	 	@Column(nullable = false, unique = true)
	    private String username;
	 	
		private String emailId;
		
	    @Column(nullable = false)
	    private String password;

		public User(String username, String emailId, String password) {
			super();
			this.username = username;
			this.emailId = emailId;
			this.password = password;
		}
}

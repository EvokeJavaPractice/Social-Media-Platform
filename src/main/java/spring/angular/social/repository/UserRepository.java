package spring.angular.social.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.angular.social.dto.AllDetails;
import spring.angular.social.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	boolean existsByUsername(String username);
    boolean existsByEmailId(String email);

	@Query(value="select user_name userName,p.content postContent,co.content postComment from " +
			"users u ,post p,comment co " +
			"where u.id=p.user_id " +
			"and p.user_id=co.user_id",nativeQuery = true)
	public AllDetails getDetails();
    
}

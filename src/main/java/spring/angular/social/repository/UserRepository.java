package spring.angular.social.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.angular.social.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
}

package spring.angular.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.angular.social.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 User findByUsername(String username);

}

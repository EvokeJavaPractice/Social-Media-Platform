package spring.angular.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.angular.social.entity.Profile;
import spring.angular.social.entity.User;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUser(User user);
    Optional<Profile> findByUserId(Long userId);
}
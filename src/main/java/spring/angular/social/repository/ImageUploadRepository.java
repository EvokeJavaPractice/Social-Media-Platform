package spring.angular.social.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.angular.social.entity.ProfileImage;
@Repository
public interface ImageUploadRepository extends JpaRepository<ProfileImage, Long> {

	Optional<ProfileImage> findBySenderId(Long senderId);

	
	

}

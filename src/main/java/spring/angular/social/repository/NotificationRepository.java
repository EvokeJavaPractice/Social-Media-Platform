package spring.angular.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.angular.social.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
}

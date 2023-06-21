package spring.angular.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.angular.social.entity.FriendConnection;
import spring.angular.social.entity.User;

public interface FriendConnectionRepository extends JpaRepository<FriendConnection, Long> {
    List<FriendConnection> findByUserOrFriend(User user, User friend);
	Long countByUserId(Long userId);
	List<FriendConnection> findByUserId(Long userId);
}

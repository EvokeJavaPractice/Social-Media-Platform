package spring.angular.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.angular.social.entity.FriendConnection;
import spring.angular.social.entity.User;

import java.util.List;
import java.util.Optional;

public interface FriendConnectionRepository extends JpaRepository<FriendConnection, Long> {

    List<FriendConnection> findByUserOrFriend(User user, User friend);

    Long countByUserId(Long userId);

    List<FriendConnection> findByUserId(Long userId);

    Optional<FriendConnection> findByUserIdAndFriendId(Long userId, Long friendId);
}

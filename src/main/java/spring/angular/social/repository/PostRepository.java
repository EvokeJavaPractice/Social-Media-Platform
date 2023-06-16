package spring.angular.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.angular.social.entity.Post;
import spring.angular.social.entity.User;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
}

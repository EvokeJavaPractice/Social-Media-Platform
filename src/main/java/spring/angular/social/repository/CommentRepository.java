package spring.angular.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.angular.social.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    int countByPostId(Long postId);

    List<Comment> findByPostId(Long postId);
}

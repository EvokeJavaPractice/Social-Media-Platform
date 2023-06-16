package spring.angular.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.angular.social.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}

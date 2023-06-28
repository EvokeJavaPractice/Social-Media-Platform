package spring.angular.social.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.angular.social.entity.Notification;
import spring.angular.social.entity.PostLike;
import spring.angular.social.repository.PostLikeRepository;

@Service
public class PostLikeService {
    private final PostLikeRepository likeRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    public PostLikeService(PostLikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }
    
    public PostLikeService(PostLikeRepository likeRepository, NotificationService notificationService) {
		this.likeRepository = likeRepository;
		this.notificationService = notificationService;
	}

	public PostLike createPostLike(PostLike like) {
        PostLike pl = likeRepository.save(like);
        Notification notification = new Notification();
        notification.setUser(like.getUser());
        notification.setMessage("You received a new like.");
        notification.setCreatedAt(LocalDateTime.now());

        like.setNotification(notification);

        notificationService.createNotification(notification);
        return pl;
    }
    
    public Optional<PostLike> getPostLikeById(Long likeId) {
        return likeRepository.findById(likeId);
    }
    
    public boolean deletePostLikeById(Long likeId) {
        if (likeRepository.existsById(likeId)) {
            likeRepository.deleteById(likeId);
            return true;
        }
        return false;
    }

    public int getPostLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }
}


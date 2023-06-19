package spring.angular.social.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.angular.social.entity.Comment;
import spring.angular.social.entity.Notification;
import spring.angular.social.repository.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    

    @Autowired
    private NotificationService notificationService;
    

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(Comment comment) {
        Comment cmt = commentRepository.save(comment);
        Notification notification = new Notification();
        notification.setUser(comment.getUser());
        notification.setMessage("You received a new Comment.");
        notification.setCreatedAt(LocalDateTime.now());

        comment.setNotification(notification);

        notificationService.createNotification(notification);
        System.out.println("comment notification"+ cmt.toString());
        return cmt;
    }

    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public boolean deleteCommentById(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }
}


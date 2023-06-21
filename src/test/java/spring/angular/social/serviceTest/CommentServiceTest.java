package spring.angular.social.serviceTest;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import spring.angular.social.entity.Comment;
import spring.angular.social.entity.Notification;
import spring.angular.social.entity.User;
import spring.angular.social.repository.CommentRepository;
import spring.angular.social.service.CommentService;
import spring.angular.social.service.NotificationService;

public class CommentServiceTest {
	
	private CommentService service;

    @Mock
    private CommentRepository repo;

    @Mock
    private NotificationService notificationService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new CommentService(repo);
        service.setNotificationService(notificationService);
    }

    @Test
    public void testCreateComment() {
    	
        // Mocking data
        Comment comment = new Comment();
        comment.setUser(new User());
        LocalDateTime now = LocalDateTime.now();

        // Mock repository behavior
        when(repo.save(comment)).thenReturn(comment);

        // Call the method using service
        Comment result = service.createComment(comment);

        // Verify repository,service interaction
        verify(repo, times(1)).save(comment);
        verify(notificationService, times(1)).createNotification(any(Notification.class));

        // Assert comparison
        assertEquals(comment, result);

        
        Notification notification = comment.getNotification();
        assertNotNull(notification);
        assertEquals(comment.getUser(), notification.getUser());
        assertEquals("You received a new Comment.", notification.getMessage());
        assertNotNull(notification.getCreatedAt());
    }

    @Test
    public void testGetCommentById() {
        
        Long commentId = 1L;
        Comment comment = new Comment();
        comment.setId(commentId);
        when(repo.findById(commentId)).thenReturn(Optional.of(comment));
        Optional<Comment> result = service.getCommentById(commentId);
        verify(repo, times(1)).findById(commentId);
        assertTrue(result.isPresent());
        assertEquals(comment, result.get());
    }

    @Test
    public void testGetCommentById_CommentNotFound() {
       
        Long commentId = 1L;

        when(repo.findById(commentId)).thenReturn(Optional.empty());
        Optional<Comment> result = service.getCommentById(commentId);
        verify(repo, times(1)).findById(commentId);

        assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteCommentById() {
       
        Long commentId = 1L;

        when(repo.existsById(commentId)).thenReturn(true);
        boolean result = service.deleteCommentById(commentId);

        verify(repo, times(1)).existsById(commentId);
        verify(repo, times(1)).deleteById(commentId);

        assertTrue(result);
    }

    @Test
    public void testDeleteCommentById_CommentNotFound() {
        
        Long commentId = 1L;
        when(repo.existsById(commentId)).thenReturn(false);
        boolean result = service.deleteCommentById(commentId);
        verify(repo, times(1)).existsById(commentId);
        verify(repo, never()).deleteById(commentId);

        assertFalse(result);
    }

    @Test
    public void testGetCommentCount() {
        
        Long postId = 1L;
        int commentCount = 5;

        
        when(repo.countByPostId(postId)).thenReturn(commentCount);
        int result = service.getCommentCount(postId);
        verify(repo, times(1)).countByPostId(postId);

        assertEquals(commentCount, result);
    }

    @Test
    public void testGetCommentsByPostId() {
        
        Long postId = 1L;
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment());
        
        when(repo.findByPostId(postId)).thenReturn(comments);
        List<Comment> result = service.getCommentsByPostId(postId);
        
        verify(repo, times(1)).findByPostId(postId);

        assertEquals(comments, result);
    }

}

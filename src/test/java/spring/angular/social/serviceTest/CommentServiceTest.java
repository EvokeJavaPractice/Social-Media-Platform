package spring.angular.social.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import spring.angular.social.entity.Comment;
import spring.angular.social.entity.Notification;
import spring.angular.social.entity.User;
import spring.angular.social.repository.CommentRepository;
import spring.angular.social.service.CommentService;
import spring.angular.social.service.NotificationService;

public class CommentServiceTest {
	
	@InjectMocks
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
    	
        
    	User u = new User();
    	u.setId(new Long(1));
    	u.setEmailId("xyz@gmail.com");
    	u.setUsername("ABC");
    	u.setPassword("ABC");
    	
        Comment comment = new Comment();
        comment.setUser(u);
        
        LocalDateTime now = LocalDateTime.now();
        
        Notification n = new Notification();
        n.setUser(u);
        comment.setNotification(n);
        
        when(repo.save(comment)).thenReturn(comment);
        
        Comment result = service.createComment(comment);

        
        verify(repo, times(1)).save(comment);
        
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

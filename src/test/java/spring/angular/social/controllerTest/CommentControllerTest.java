package spring.angular.social.controllerTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import spring.angular.social.controller.CommentController;
import spring.angular.social.dto.CommentDto;
import spring.angular.social.entity.Comment;
import spring.angular.social.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CommentControllerTest {

    private final CommentService commentService = 
    		mock(CommentService.class);
    private final CommentController commentController = 
    		new CommentController();

    @Test
    public void testCreateComment() {
        
        Comment comment = new Comment();
        
        Comment createdComment = new Comment();


        
        Mockito.when(commentService.createComment(comment)).thenReturn(createdComment);

        ResponseEntity<CommentDto> response = commentController.createComment(comment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdComment, response.getBody());
    }

    @Test
    public void testGetCommentCount() {
        
        Long postId = 1L;
        int commentCount = 5;
        Mockito.when(commentService.getCommentCount(postId)).thenReturn(commentCount);

        ResponseEntity<Integer> response = commentController.getCommentCount(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(commentCount, response.getBody());
    }

    @Test
    public void testGetCommentsByPostId_1() {
       
        Long postId = 1L;
        List<Comment> comments = new ArrayList<>();
        Mockito.when(commentService.getCommentsByPostId(postId)).thenReturn(comments);

        List<CommentDto> response = commentController.getCommentsByPostId(postId);

        assertEquals(comments, response);
    }

    @Test
    public void testGetCommentsByPostId_2() {
        
        Long commentId = 1L;
        Comment comment = new Comment();
        Mockito.when(commentService.getCommentById(commentId)).thenReturn(Optional.of(comment));

        ResponseEntity<CommentDto> response = commentController.getCommentById(commentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comment, response.getBody());
    }

    @Test
    public void testGetCommentsByPostId_3() {
        
        Long commentId = 1L;
        Mockito.when(commentService.getCommentById(commentId)).thenReturn(Optional.empty());

        ResponseEntity<CommentDto> response = commentController.getCommentById(commentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null);
    }

    @Test
    public void testDeleteComment_1() {
        
        Long commentId = 1L;
        Mockito.when(commentService.deleteCommentById(commentId)).thenReturn(true);

        ResponseEntity<Void> response = commentController.deleteComment(commentId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(response.getBody() == null);
    }

    @Test
    public void testDeleteComment_2() {
        
        Long commentId = 1L;
        Mockito.when(commentService.deleteCommentById(commentId)).thenReturn(false);

        ResponseEntity<Void> response = commentController.deleteComment(commentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null);
    }
}

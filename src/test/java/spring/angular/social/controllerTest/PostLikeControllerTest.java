package spring.angular.social.controllerTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import spring.angular.social.controller.PostLikeController;
import spring.angular.social.entity.PostLike;
import spring.angular.social.entity.User;
import spring.angular.social.service.PostLikeService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PostLikeControllerTest {

    private final PostLikeService likeService = mock(PostLikeService.class);
    private final PostLikeController likeController = new PostLikeController(likeService);
    
    @Test
    public void testCreatePostLike() {
    	
    	PostLike like = new PostLike();
    	
    	when(likeService.createPostLike(like)).thenReturn(like);
    	
    	ResponseEntity<PostLike> expected = likeController.createPostLike(like);
        
        assertEquals(HttpStatus.OK, expected.getStatusCode());
        
        assertEquals(like, expected.getBody());
    }
    
    @Test
    public void testGetPostLikeCount() {
    	
    	Long postId = 1L;
        int likeCount = 1;
        
        when(likeService.getPostLikeCount(postId)).thenReturn(likeCount);
        
        ResponseEntity<Integer> expected = likeController.getPostLikeCount(postId);
       
        assertEquals(HttpStatus.OK,expected.getStatusCode());
        
        assertEquals(likeCount,expected.getBody());
    }
    
    @Test
    public void testGetPostLikeById_successCase() {
    	
    	
    	Long likeId = 1L;
    	PostLike PL = new PostLike();
    	Optional<PostLike> postLike = Optional.of(PL);
    	
    	when(likeService.getPostLikeById(likeId)).thenReturn(postLike);
    	
    	ResponseEntity<PostLike> expected = likeController.getPostLikeById(likeId);
    	
    	assertEquals(HttpStatus.OK,expected.getStatusCode());
    	assertEquals(PL,expected.getBody());
    	
    }
    
    @Test
    public void testGetPostLikeById_failureCase() {
    	
    	
    	Long likeId = 1L;
    	PostLike PL = new PostLike();
    	
    	
    	when(likeService.getPostLikeById(likeId)).thenReturn(Optional.empty());
    	
    	ResponseEntity<PostLike> expected = likeController.getPostLikeById(likeId);
    	
    	assertEquals(HttpStatus.NOT_FOUND,expected.getStatusCode());
    	
    	assertTrue(expected.getBody()==null);
    
    }
    
    @Test
    public void testDeletePostLike_successCase() {
    	
    	Long likeId = 1L;
    	
    	when(likeService.deletePostLikeById(likeId)).thenReturn(true);
    	
    	ResponseEntity<Void> expectedValue = likeController.deletePostLike(likeId);
        
    	assertEquals(HttpStatus.NO_CONTENT,expectedValue.getStatusCode());
    	
    }
    
    @Test
    public void testDeletePostLike_failureCase() {
    	
    	Long likeId = 1L;
    	
    	when(likeService.deletePostLikeById(likeId)).thenReturn(false);
    	
    	ResponseEntity<Void> expectedValue = likeController.deletePostLike(likeId);
        
    	assertEquals(HttpStatus.NOT_FOUND,expectedValue.getStatusCode());
    	
    }
    
}


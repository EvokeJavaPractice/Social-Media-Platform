package spring.angular.social.controllerTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import spring.angular.social.constants.AppConstants;
import spring.angular.social.controller.PostController;
import spring.angular.social.dto.PostDto;
import spring.angular.social.entity.Post;
import spring.angular.social.entity.User;
import spring.angular.social.service.PostService;
import spring.angular.social.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PostControllerTest {

  @MockBean
  private PostService postService;

  @MockBean
  private UserService userService;



    @Test
    public void trueSavePost() {
    	
    	Post post = new Post();
    	
    	when(postService.save(post)).thenReturn(post);
    	

    }
    
    @Test
    public void testGetPostsByUser() {
    	String username = "Bhavani";
    	User user = new User();
    	
        when(userService.findByUsername(username)).thenReturn(user);
        
        Post post = new Post();
        List<Post> posts = new ArrayList<Post>();
        posts.add(post);
        
        
        when(postService.getUserPosts(user)).thenReturn(posts);
    }
    
    @Test
    public void testDeletePost() {
    	
    	Long id = 1L; 

    }
    
    @Test
    public void testUpdatePost() {
    	
    	Long postId=1L;
    	Post post = new Post();
    	when(postService.update(postId, post)).thenReturn(post);

    }
    
    @Test
    public void testPost() {
    	
    	PostDto postDto = new PostDto();
    	
    	when(postService.createPost(postDto)).thenReturn(postDto);
    	

    }
    
    @Test
    public void testGetAllPosts()
    {
    	int pageNo=1;
    	int pageSize=1;
    	
    	PostDto postDto = new PostDto();
    	List<PostDto> postDtos = new ArrayList<PostDto>();
    	postDtos.add(postDto);
    	
    	when(postService.getAllPosts(pageNo,pageSize)).thenReturn(postDtos);

    }


    
    
}


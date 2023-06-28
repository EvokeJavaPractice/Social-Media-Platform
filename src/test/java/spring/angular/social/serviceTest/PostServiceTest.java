package spring.angular.social.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import spring.angular.social.dto.PostDto;
import spring.angular.social.entity.Post;
import spring.angular.social.entity.User;
import spring.angular.social.repository.PostRepository;
import spring.angular.social.service.PostService;

public class PostServiceTest {

    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        postService = new PostService(postRepository);
    }

    @Test
    public void testGetUserPosts() {
        
        User user = new User();
        user.setId(1L);

        Post post1 = new Post();
        post1.setId(1L);
        post1.setContent("POST1");
        post1.setUser(user);

        Post post2 = new Post();
        post2.setId(2L);
        post2.setContent("POST2");
        post2.setUser(user);

        List<Post> expectedPosts = Arrays.asList(post1, post2);

        when(postRepository.findByUser(user)).thenReturn(expectedPosts);

        List<Post> result = postService.getUserPosts(user);
        
        verify(postRepository, times(1)).findByUser(user);
        assertEquals(expectedPosts, result);
    }

    @Test
    public void testSavePost() {
        
        Post post = new Post();
        post.setId(1L);
        post.setContent("POSTTTT!!!!!");

        when(postRepository.save(post)).thenReturn(post);

        Post result = postService.save(post);

        verify(postRepository, times(1)).save(post);

        assertEquals(post, result);
    }

    @Test
    public void testDeletePost() {
        
        Long postId = 1L;
        
        postService.delete(postId);

        verify(postRepository, times(1)).deleteById(postId);
    }

    @Test
    public void testUpdatePost_success() {
        
        Long postId = 1L;
        Post existingPost = new Post();
        existingPost.setId(postId);
        existingPost.setContent("EXISTING POST");

        Post updatedPost = new Post();
        updatedPost.setContent("UPADATED POST");

        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(existingPost)).thenReturn(existingPost);

        Post result = postService.update(postId, updatedPost);

        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).save(existingPost);
        assertEquals(updatedPost.getContent(), result.getContent());
    }

    @Test
    public void testUpdatePost_failure() {
        
        Long postId = 1L;
        Post updatedPost = new Post();

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        Post result = postService.update(postId, updatedPost);

        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, never()).save(any(Post.class));
        assertNull(result);
    }

    @Test
    public void testCreatePost() {
        
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setContent("POST1");

        Post post = new Post();
        post.setId(1L);
        post.setContent("POST2");

        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDto result = postService.createPost(postDto);

        verify(postRepository, times(1)).save(any(Post.class));
        assertNotNull(result);
        
    }

    @Test
    public void testGetAllPosts() {
        
        int pageNo = 0;
        int pageRecords = 10;
        Pageable pageable = PageRequest.of(pageNo, pageRecords);
        Post post1 = new Post();
        post1.setId(1L);
        post1.setContent("POST1");
        Post post2 = new Post();
        post2.setId(2L);
        post2.setContent("POST2");

        List<Post> posts = Arrays.asList(post1, post2);
        Page<Post> page = new PageImpl<>(posts, pageable, posts.size());

        when(postRepository.findAll(pageable)).thenReturn(page);

        List<PostDto> result = postService.getAllPosts(pageNo, pageRecords);

        verify(postRepository, times(1)).findAll(pageable);
        assertEquals(posts.size(), result.size());
        assertEquals(post1.getContent(), result.get(0).getContent());
        assertEquals(post2.getContent(), result.get(1).getContent());
    }


}


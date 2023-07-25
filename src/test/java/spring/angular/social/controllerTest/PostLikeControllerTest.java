package spring.angular.social.controllerTest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spring.angular.social.controller.PostLikeController;
import spring.angular.social.dto.PostLikeDto;
import spring.angular.social.entity.Post;
import spring.angular.social.entity.PostLike;
import spring.angular.social.entity.User;
import spring.angular.social.mappers.PostLIkeMapper;
import spring.angular.social.service.PostLikeService;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PostLikeController.class)
public class PostLikeControllerTest {

    @MockBean
    private PostLikeService likeService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PostLIkeMapper mapper;

    public User getUser() {
        User user = new User(
                1L, "surya@gmail.com", "Surya", "password");
        return user;
    }
    public Post getPost(){
        Post post=new Post();
        post.setUser(getUser());
        post.setContent("you only live once");
        return post;
    }
    public PostLike getPostLike(){
        PostLike postLike=new PostLike();
        postLike.setUser(getUser());
        postLike.setId(1L);
        postLike.setPost(getPost());
        return postLike;
    }
    public PostLikeDto getPostLikeDto(){
        PostLikeDto postLikeDto=new PostLikeDto();
        postLikeDto.setUser(getUser());
        postLikeDto.setId(1L);
        postLikeDto.setPost(new Post());
        return postLikeDto;
    }

    @Test
    public void testCreatePostLike() throws Exception {
        Mockito.when(likeService.createPostLike(any(PostLike.class))).thenReturn(getPostLike());
        Mockito.when(mapper.toEntity(any(PostLikeDto.class))).thenReturn(getPostLike());
        Mockito.when(mapper.toDto(any(PostLike.class))).thenReturn(getPostLikeDto());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.post("/api/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getPostLikeDto()));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.user.username", is("Surya")));



    }


//    @Test
//    public void testCreatePostLike() {
//
//        PostLike like = new PostLike();
//
//        when(likeService.createPostLike(like)).thenReturn(like);
//
//    }
//
//  @Test
//    public void testGetPostLikeCount() {
//
//        Long postId = 1L;
//        int likeCount = 1;
//
//        when(likeService.getPostLikeCount(postId)).thenReturn(likeCount);
//
//    }
//
//    @Test
//    public void testGetPostLikeById_successCase() {
//
//
//        Long likeId = 1L;
//        PostLike PL = new PostLike();
//        Optional<PostLike> postLike = Optional.of(PL);
//
//        when(likeService.getPostLikeById(likeId)).thenReturn(postLike);
//
//
//    }
//
//    @Test
//    public void testGetPostLikeById_failureCase() {
//
//
//        Long likeId = 1L;
//        PostLike PL = new PostLike();
//
//
//        when(likeService.getPostLikeById(likeId)).thenReturn(Optional.empty());
//
//
//    }
//
//    @Test
//    public void testDeletePostLike_successCase() {
//
//        Long likeId = 1L;
//
//        when(likeService.deletePostLikeById(likeId)).thenReturn(true);
//
//
//    }
//
//    @Test
//    public void testDeletePostLike_failureCase() {
//
//        Long likeId = 1L;
//
//        when(likeService.deletePostLikeById(likeId)).thenReturn(false);
//
//
//    }

}


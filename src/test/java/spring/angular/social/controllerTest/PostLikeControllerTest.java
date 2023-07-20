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

    @MockBean
    private PostLikeService likeService;

    @MockBean
    private PostLikeController likeController;

    @Test
    public void testCreatePostLike() {

        PostLike like = new PostLike();

        when(likeService.createPostLike(like)).thenReturn(like);

    }

    @Test
    public void testGetPostLikeCount() {

        Long postId = 1L;
        int likeCount = 1;

        when(likeService.getPostLikeCount(postId)).thenReturn(likeCount);

    }

    @Test
    public void testGetPostLikeById_successCase() {


        Long likeId = 1L;
        PostLike PL = new PostLike();
        Optional<PostLike> postLike = Optional.of(PL);

        when(likeService.getPostLikeById(likeId)).thenReturn(postLike);


    }

    @Test
    public void testGetPostLikeById_failureCase() {


        Long likeId = 1L;
        PostLike PL = new PostLike();


        when(likeService.getPostLikeById(likeId)).thenReturn(Optional.empty());


    }

    @Test
    public void testDeletePostLike_successCase() {

        Long likeId = 1L;

        when(likeService.deletePostLikeById(likeId)).thenReturn(true);


    }

    @Test
    public void testDeletePostLike_failureCase() {

        Long likeId = 1L;

        when(likeService.deletePostLikeById(likeId)).thenReturn(false);


    }

}


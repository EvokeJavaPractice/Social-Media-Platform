package spring.angular.social.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.angular.social.entity.PostLike;
import spring.angular.social.service.PostLikeService;

@RestController
@RequestMapping("/api/likes")
public class PostLikeController {
    private final PostLikeService likeService;
    
    public PostLikeController(PostLikeService likeService) {
        this.likeService = likeService;
    }
    
    @PostMapping
    public ResponseEntity<PostLike> createPostLike(@RequestBody PostLike like) {
        PostLike createdPostLike = likeService.createPostLike(like);
        return ResponseEntity.ok(createdPostLike);
    }
    
    @GetMapping("/{likeId}")
    public ResponseEntity<PostLike> getPostLikeById(@PathVariable Long likeId) {
        Optional<PostLike> optionalPostLike = likeService.getPostLikeById(likeId);
        
        if (optionalPostLike.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        PostLike like = optionalPostLike.get();
        return ResponseEntity.ok(like);
    }
    
    @DeleteMapping("/{likeId}")
    public ResponseEntity<Void> deletePostLike(@PathVariable Long likeId) {
        boolean deleted = likeService.deletePostLikeById(likeId);
        
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}


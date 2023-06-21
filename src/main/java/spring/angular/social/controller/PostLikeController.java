package spring.angular.social.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import spring.angular.social.entity.PostLike;
import spring.angular.social.service.PostLikeService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/likes")
public class PostLikeController {
    @Autowired
    private PostLikeService likeService;

    @PostMapping
    public ResponseEntity<PostLike> createPostLike(@RequestBody PostLike like) {
        PostLike createdPostLike = likeService.createPostLike(like);
        return ResponseEntity.ok(createdPostLike);
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Integer> getPostLikeCount(@PathVariable Long postId) {
        int likeCount = likeService.getPostLikeCount(postId);
        return ResponseEntity.ok(likeCount);
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


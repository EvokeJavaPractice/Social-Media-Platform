package spring.angular.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.angular.social.entity.Post;
import spring.angular.social.entity.User;
import spring.angular.social.service.PostService;
import spring.angular.social.service.UserService;
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }
    
    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody Post post){
    	Post pst = postService.save(post);
    	return ResponseEntity.ok(pst);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
        }
        List<Post> posts = postService.getUserPosts(user);
        return ResponseEntity.ok(posts);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id)
    {
    	postService.delete(id);
    	return ResponseEntity.ok("post deleted");
    }
    
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody Post post) {
    	Post pst = postService.update(postId,post);
    	return ResponseEntity.ok(pst);
    }
}
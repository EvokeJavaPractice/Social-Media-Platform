package spring.angular.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.angular.social.constants.AppConstants;
import spring.angular.social.dto.PostDto;
import spring.angular.social.entity.Post;
import spring.angular.social.entity.User;
import spring.angular.social.service.PostService;
import spring.angular.social.service.UserService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody Post post) {
        return ResponseEntity.ok(postService.save(post));
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
        }
        List<Post> posts = postService.getUserPosts(user);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok("post deleted");
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody Post post) {
        Post pst = postService.update(postId, post);
        return ResponseEntity.ok(pst);
    }

    @PostMapping
    public ResponseEntity<PostDto> post(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<PostDto> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        return postService.getAllPosts(pageNo, pageSize);
    }
}
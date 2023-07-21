package spring.angular.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.angular.social.constants.AppConstants;
import spring.angular.social.dto.PostDto;
import spring.angular.social.entity.Post;
import spring.angular.social.entity.User;
import spring.angular.social.mappers.PostMapper;
import spring.angular.social.service.PostService;
import spring.angular.social.service.UserService;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @Autowired
    private PostMapper mapper;

    @GetMapping("/{username}")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        List<Post> posts = postService.getUserPosts(user);
        return ResponseEntity.ok(mapper.toDto(posts));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok("post deleted");
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody Post post) {
        Post pst = postService.update(postId, post);
        return ResponseEntity.ok(mapper.toDto(pst));
    }

    @GetMapping
    public List<PostDto> getAllPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize) {
        return postService.getAllPosts(pageNo, pageSize);
    }
}
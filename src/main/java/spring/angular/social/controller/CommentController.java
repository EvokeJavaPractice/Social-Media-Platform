package spring.angular.social.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import spring.angular.social.dto.CommentDto;
import spring.angular.social.entity.Comment;
import spring.angular.social.mappers.CommentMapper;
import spring.angular.social.service.CommentService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper mapper;
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody Comment comment) {
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.ok(mapper.toDto(createdComment));
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Integer> getCommentCount(@PathVariable Long postId) {
        int commentCount = commentService.getCommentCount(postId);
        return ResponseEntity.ok(commentCount);
    }

    @GetMapping("/post/{postId}")
    public List<CommentDto> getCommentsByPostId(@PathVariable Long postId) {
        return mapper.toDto(commentService.getCommentsByPostId(postId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long commentId) {
        Optional<Comment> optionalComment = commentService.getCommentById(commentId);

        if (optionalComment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Comment comment = optionalComment.get();
        return ResponseEntity.ok(mapper.toDto(comment));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        boolean deleted = commentService.deleteCommentById(commentId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}



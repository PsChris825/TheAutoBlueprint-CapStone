package learn.autoblueprint.controllers;

import learn.autoblueprint.domain.CommentService;
import learn.autoblueprint.models.Comment;
import learn.autoblueprint.domain.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Comment> findAll() {
        return service.findAll();
    }

    @GetMapping("/post/{postId}")
    public List<Comment> findByPostId(@PathVariable int postId) {
        return service.findByPostId(postId);
    }

    @GetMapping("/user/{userId}")
    public List<Comment> findByUserId(@PathVariable int userId) {
        return service.findByUserId(userId);
    }

    @GetMapping("/{commentId}")
    public Comment findById(@PathVariable int commentId) {
        return service.findById(commentId);
    }

    @PostMapping
    public Result<Comment> add(@RequestBody Comment comment) {
        return service.add(comment);
    }

    @PutMapping("/{commentId}")
    public Result<Comment> update(@PathVariable int commentId, @RequestBody Comment comment) {
        comment.setCommentId(commentId);
        return service.update(comment);
    }

    @DeleteMapping("/{commentId}")
    public boolean deleteById(@PathVariable int commentId) {
        return service.deleteById(commentId);
    }
}
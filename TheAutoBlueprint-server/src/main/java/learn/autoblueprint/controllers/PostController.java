package learn.autoblueprint.controllers;

import learn.autoblueprint.domain.PostService;
import learn.autoblueprint.models.Post;
import learn.autoblueprint.domain.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> findAll() {
        return service.findAll();
    }

    @GetMapping("/{postId}")
    public Post findById(@PathVariable int postId) {
        return service.findById(postId);
    }

    @GetMapping("/{postId}/comments")
    public Post findPostWithComments(@PathVariable int postId) {
        return service.findPostWithComments(postId);
    }

    @GetMapping("/user/{userId}")
    public List<Post> findByUserId(@PathVariable int userId) {
        return service.findByUserId(userId);
    }

    @PostMapping
    public Result<Post> add(@RequestBody Post post) {
        return service.add(post);
    }

    @PutMapping("/{postId}")
    public Result<Post> update(@PathVariable int postId, @RequestBody Post post) {
        post.setPostId(postId);
        return service.update(post);
    }

    @DeleteMapping("/{postId}")
    public boolean deleteById(@PathVariable int postId) {
        return service.deleteById(postId);
    }
}

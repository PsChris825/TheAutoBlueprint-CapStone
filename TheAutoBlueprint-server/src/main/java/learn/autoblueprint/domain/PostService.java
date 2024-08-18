package learn.autoblueprint.domain;

import learn.autoblueprint.data.PostRepository;
import learn.autoblueprint.models.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post findById(int postId) {
        return repository.findById(postId);
    }

    public List<Post> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public Result<Post> add(Post post) {
        Result<Post> result = validate(post);
        if (!result.isSuccess()) {
            return result;
        }
        post = repository.add(post);
        result.setPayload(post);
        return result;
    }

    public Result<Post> update(Post post) {
        Result<Post> result = validate(post);
        if (!result.isSuccess()) {
            return result;
        }
        if (!repository.update(post)) {
            result.addMessage("Post not found", ResultType.NOT_FOUND);
        }
        return result;
    }

    public boolean deleteById(int postId) {
        return repository.deleteById(postId);
    }

    private Result<Post> validate(Post post) {
        Result<Post> result = new Result<>();
        if (post == null) {
            result.addMessage("Post cannot be null", ResultType.INVALID);
            return result;
        }
        if (post.getTitle() == null || post.getTitle().isBlank()) {
            result.addMessage("Title is required", ResultType.INVALID);
        }
        if (post.getPostDescription() == null || post.getPostDescription().isBlank()) {
            result.addMessage("Description is required", ResultType.INVALID);
        }
        return result;
    }
}

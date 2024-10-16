package learn.autoblueprint.domain;

import learn.autoblueprint.data.CommentRepository;
import learn.autoblueprint.models.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> findAll() {
        return repository.findAll();
    }

    public List<Comment> findByPostId(int postId) {
        return repository.findByPostId(postId);
    }

    public List<Comment> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public Comment findById(int commentId) {
        return repository.findById(commentId);
    }

    public Result<Comment> add(Comment comment) {
        Result<Comment> result = validate(comment);

        if (!result.isSuccess()) {
            return result;
        }

        if (comment.getUserId() == null) {
            result.addMessage("User ID cannot be null.", ResultType.INVALID);
            return result;
        }

        if (comment.getCommentId() != null && comment.getCommentId() != 0) {
            result.addMessage("New comment must not have id set.", ResultType.INVALID);
            return result;
        }

        comment.setCommentId(0);
        comment = repository.add(comment);
        result.setPayload(comment);
        return result;
    }


    public Result<Comment> update(Comment comment) {
        Result<Comment> result = validate(comment);

        if (!result.isSuccess()) {
            return result;
        }

        if (comment.getCommentId() <= 0) {
            result.addMessage("Existing comment must have id set.", ResultType.INVALID);
            return result;
        }

        if (!repository.update(comment)) {
            String msg = String.format("commentId: %s, not found", comment.getCommentId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int commentId) {
        return repository.deleteById(commentId);
    }

    public String getUsernameById(int userId) {
        return repository.getUsernameById(userId);
    }

    private Result<Comment> validate(Comment comment) {
        Result<Comment> result = new Result<>();

        if (comment == null) {
            result.addMessage("Comment cannot be null", ResultType.INVALID);
            return result;
        }

        if (comment.getCommentText() == null || comment.getCommentText().isBlank()) {
            result.addMessage("Comment text is required", ResultType.INVALID);
        }

        if (comment.getCommentText() != null && comment.getCommentText().length() > 500) {
            result.addMessage("Comment text must be less than 500 characters", ResultType.INVALID);
        }

        return result;
    }
}

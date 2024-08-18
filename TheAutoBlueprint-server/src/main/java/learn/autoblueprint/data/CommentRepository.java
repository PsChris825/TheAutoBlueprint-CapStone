package learn.autoblueprint.data;

import learn.autoblueprint.models.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> findAll();

    List<Comment> findByPostId(int postId);

    List<Comment> findByUserId(int userId);

    Comment findById(int commentId);

    Comment add(Comment comment);

    boolean update(Comment comment);

    boolean deleteById(int commentId);
}

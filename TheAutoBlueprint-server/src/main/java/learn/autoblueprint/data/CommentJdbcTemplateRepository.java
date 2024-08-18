package learn.autoblueprint.data;

import learn.autoblueprint.data.mappers.CommentMapper;
import learn.autoblueprint.models.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CommentJdbcTemplateRepository implements  CommentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CommentMapper commentMapper = new CommentMapper();

    public CommentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> findAll() {
        final String sql = """
                select
                    comment_id,
                    post_id,
                    user_id,
                    comment_text,
                    created_at
                from comment;
                """;

        return jdbcTemplate.query(sql, commentMapper);
    }

    @Override
    public List<Comment> findByPostId(int postId) {
        final String sql = """
                select
                    comment_id,
                    post_id,
                    user_id,
                    comment_text,
                    created_at
                from comment
                where post_id = ?
                order by created_at;
                """;

        return jdbcTemplate.query(sql, commentMapper, postId);
    }

    @Override
    public List<Comment> findByUserId(int userId) {
        final String sql = """
            select
                comment_id,
                post_id,
                user_id,
                comment_text,
                created_at
            from comment
            where user_id = ?;
            """;

        return jdbcTemplate.query(sql, commentMapper, userId);
    }

    @Override
    public Comment findById(int commentId) {
        final String sql = """
                select
                    comment_id,
                    post_id,
                    user_id,
                    comment_text,
                    created_at
                from comment
                where comment_id = ?;
                """;

        return jdbcTemplate.query(sql, commentMapper, commentId).stream().findFirst().orElse(null);
    }

    @Override
    public Comment add(Comment comment) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("comment")
                .usingGeneratedKeyColumns("comment_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("post_id", comment.getPostId());
        args.put("user_id", comment.getUserId());
        args.put("comment_text", comment.getCommentText());
        args.put("created_at", comment.getCreatedAt());

        comment.setCommentId(insert.executeAndReturnKey(args).intValue());
        return comment;
    }

    @Override
    public boolean update(Comment comment) {
        final String sql = """
                update comment set
                    post_id = ?,
                    user_id = ?,
                    comment_text = ?,
                    created_at = ?
                where comment_id = ?;
                """;

        int rowsAffected = jdbcTemplate.update(sql,
                comment.getPostId(),
                comment.getUserId(),
                comment.getCommentText(),
                comment.getCreatedAt(),
                comment.getCommentId());

        return rowsAffected > 0;
    }

    @Override
    public boolean deleteById(int commentId) {
        return jdbcTemplate.update("delete from comment where comment_id = ?", commentId) > 0;
    }
}
package learn.autoblueprint.data;

import learn.autoblueprint.data.mappers.CommentMapper;
import learn.autoblueprint.models.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CommentJdbcTemplateRepository implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CommentMapper commentMapper = new CommentMapper();

    public CommentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getUsernameById(int userId) {
        final String sql = """
                select username
                from app_user
                where app_user_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, String.class);
    }

    @Override
    public List<Comment> findAll() {
        final String sql = """
                select
                    c.comment_id,
                    c.post_id,
                    c.user_id,
                    c.comment_text,
                    c.created_at,
                    u.username
                from comment c
                join app_user u on c.user_id = u.app_user_id;
                """;

        return jdbcTemplate.query(sql, commentMapper);
    }

    @Override
    public List<Comment> findByPostId(int postId) {
        final String sql = """
                select
                    c.comment_id,
                    c.post_id,
                    c.user_id,
                    c.comment_text,
                    c.created_at,
                    u.username
                from comment c
                join app_user u on c.user_id = u.app_user_id
                where c.post_id = ?
                order by c.created_at;
                """;

        return jdbcTemplate.query(sql, commentMapper, postId);
    }

    @Override
    public List<Comment> findByUserId(int userId) {
        final String sql = """
            select
                c.comment_id,
                c.post_id,
                c.user_id,
                c.comment_text,
                c.created_at,
                u.username
            from comment c
            join app_user u on c.user_id = u.app_user_id
            where c.user_id = ?;
            """;

        return jdbcTemplate.query(sql, commentMapper, userId);
    }

    @Override
    public Comment findById(int commentId) {
        final String sql = """
                select
                    c.comment_id,
                    c.post_id,
                    c.user_id,
                    c.comment_text,
                    c.created_at,
                    u.username
                from comment c
                join app_user u on c.user_id = u.app_user_id
                where c.comment_id = ?;
                """;

        return jdbcTemplate.query(sql, commentMapper, commentId).stream().findFirst().orElse(null);
    }

    @Override
    public Comment add(Comment comment) {
        System.out.println("Received comment: " + comment);
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

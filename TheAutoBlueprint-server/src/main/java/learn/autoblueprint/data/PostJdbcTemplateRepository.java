package learn.autoblueprint.data;

import learn.autoblueprint.data.mappers.CommentMapper;
import learn.autoblueprint.data.mappers.PostMapper;
import learn.autoblueprint.models.Comment;
import learn.autoblueprint.models.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class PostJdbcTemplateRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public PostJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> findAll() {
        final String sql = """
            SELECT p.post_id, p.user_id, u.username, p.title, p.post_description, p.image_url, p.created_at, p.updated_at 
            FROM post p
            JOIN app_user u ON p.user_id = u.app_user_id;
            """;
        return jdbcTemplate.query(sql, new PostMapper());
    }

    @Override
    public Post findById(int postId) {
        final String sql = """
            SELECT p.post_id, p.user_id, u.username, p.title, p.post_description, p.image_url, p.created_at, p.updated_at 
            FROM post p
            JOIN app_user u ON p.user_id = u.app_user_id
            WHERE p.post_id = ?;
            """;
        return jdbcTemplate.query(sql, new PostMapper(), postId).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Post> findByUserId(int userId) {
        final String sql = """
            SELECT p.post_id, p.user_id, u.username, p.title, p.post_description, p.image_url, p.created_at, p.updated_at 
            FROM post p
            JOIN app_user u ON p.user_id = u.app_user_id
            WHERE p.user_id = ?;
            """;
        return jdbcTemplate.query(sql, new PostMapper(), userId);
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
    public List<Comment> findCommentsByPostId(int postId) {
        final String sql = """
            SELECT c.comment_id, c.post_id, c.user_id, u.username, c.comment_text, c.created_at 
            FROM comment c
            JOIN app_user u ON c.user_id = u.app_user_id
            WHERE c.post_id = ?;
            """;
        return jdbcTemplate.query(sql, new CommentMapper(), postId);
    }

    @Override
    public Post add(Post post) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("post")
                .usingGeneratedKeyColumns("post_id");

        HashMap<String, Object> values = new HashMap<>();
        values.put("user_id", post.getUserId());
        values.put("username", post.getUsername());  // Include username
        values.put("title", post.getTitle());
        values.put("post_description", post.getPostDescription());
        values.put("image_url", post.getImageUrl());
        values.put("created_at", post.getCreatedAt());
        values.put("updated_at", post.getUpdatedAt());

        post.setPostId(insert.executeAndReturnKey(values).intValue());
        return post;
    }

    @Override
    public boolean update(Post post) {
        final String sql = """
            UPDATE post SET user_id = ?, username = ?, title = ?, post_description = ?, image_url = ?, created_at = ?, updated_at = ? 
            WHERE post_id = ?;
            """;
        return jdbcTemplate.update(sql,
                post.getUserId(),
                post.getUsername(),  // Include username in the update
                post.getTitle(),
                post.getPostDescription(),
                post.getImageUrl(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getPostId()) > 0;
    }

    @Override
    public boolean deleteById(int postId) {
        final String sql = "DELETE FROM post WHERE post_id = ?;";
        return jdbcTemplate.update(sql, postId) > 0;
    }
}

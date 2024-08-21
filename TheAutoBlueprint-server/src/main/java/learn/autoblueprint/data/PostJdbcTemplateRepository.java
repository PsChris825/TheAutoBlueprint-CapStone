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
        final String sql = "SELECT post_id, user_id, title, post_description, image_url, created_at, updated_at FROM post;";
        return jdbcTemplate.query(sql, new PostMapper());
    }

    @Override
    public Post findById(int postId) {
        final String sql = "SELECT post_id, user_id, title, post_description, image_url, created_at, updated_at FROM post WHERE post_id = ?;";
        return jdbcTemplate.query(sql, new PostMapper(), postId).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Post> findByUserId(int userId) {
        final String sql = "SELECT post_id, user_id, title, post_description, image_url, created_at, updated_at FROM post WHERE user_id = ?;";
        return jdbcTemplate.query(sql, new PostMapper(), userId);
    }

    @Override
    public List<Comment> findCommentsByPostId(int postId) {
        final String sql = """
                SELECT
                    p.post_id, p.user_id, p.title, p.post_description, p.image_url, p.created_at, p.updated_at,
                    c.comment_id, c.user_id AS comment_user_id, c.comment_text, c.created_at AS comment_created_at
                FROM
                    post p
                LEFT JOIN
                    comment c ON p.post_id = c.post_id
                WHERE
                    p.post_id = ?;
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
        final String sql = "UPDATE post SET user_id = ?, title = ?, post_description = ?, image_url = ?, created_at = ?, updated_at = ? WHERE post_id = ?;";
        return jdbcTemplate.update(sql,
                post.getUserId(),
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

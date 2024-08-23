package learn.autoblueprint.data.mappers;

import learn.autoblueprint.models.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CommentMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment();
        comment.setCommentId(rs.getInt("comment_id"));
        comment.setPostId(rs.getInt("post_id"));
        comment.setUserId(rs.getInt("user_id"));
        comment.setUsername(rs.getString("username")); // Map the username field
        comment.setCommentText(rs.getString("comment_text"));

        Timestamp createdAtTimestamp = rs.getTimestamp("created_at");
        comment.setCreatedAt(
                (createdAtTimestamp != null) ? createdAtTimestamp.toLocalDateTime() : null
        );

        return comment;
    }
}

package learn.autoblueprint.data.mappers;

import learn.autoblueprint.models.Comment;
import learn.autoblueprint.models.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PostMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setPostId(rs.getInt("post_id"));
        post.setUserId(rs.getInt("user_id"));
        post.setTitle(rs.getString("title"));
        post.setPostDescription(rs.getString("post_description"));
        post.setImageUrl(rs.getString("image_url"));

        Timestamp createdAtTimestamp = rs.getTimestamp("created_at");
        post.setCreatedAt(
                (createdAtTimestamp != null) ? createdAtTimestamp.toLocalDateTime() : null
        );

        Timestamp updatedAtTimestamp = rs.getTimestamp("updated_at");
        post.setUpdatedAt(
                (updatedAtTimestamp != null) ? updatedAtTimestamp.toLocalDateTime() : null
        );


        return post;
    }

}

package learn.autoblueprint.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {

    private Integer commentId;
    private Integer postId;
    private Integer userId;
    private String username; // New field to store the username
    private String commentText;
    private LocalDateTime createdAt;

    public Comment() {}

    public Comment(Integer commentId, Integer postId, Integer userId, String username, String commentText, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.username = username; // Initialize the username field
        this.commentText = commentText;
        this.createdAt = createdAt;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() { // New getter for username
        return username;
    }

    public void setUsername(String username) { // New setter for username
        this.username = username;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(commentId, comment.commentId) &&
                Objects.equals(postId, comment.postId) &&
                Objects.equals(userId, comment.userId) &&
                Objects.equals(username, comment.username) && // Updated equals method
                Objects.equals(commentText, comment.commentText) &&
                Objects.equals(createdAt, comment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, postId, userId, username, commentText, createdAt); // Updated hashCode method
    }
}

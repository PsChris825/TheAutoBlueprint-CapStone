package learn.autoblueprint.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {

    private Integer postId;
    private Integer userId;
    private String title;
    private String postDescription;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Post() {}

    public Post(Integer postId, Integer userId, String title, String postDescription, String imageUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.postDescription = postDescription;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(postId, post.postId) && Objects.equals(userId, post.userId) && Objects.equals(title, post.title) && Objects.equals(postDescription, post.postDescription) && Objects.equals(imageUrl, post.imageUrl) && Objects.equals(createdAt, post.createdAt) && Objects.equals(updatedAt, post.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, userId, title, postDescription, imageUrl, createdAt, updatedAt);
    }
}

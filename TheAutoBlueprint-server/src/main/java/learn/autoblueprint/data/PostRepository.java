// File: src/main/java/learn/autoblueprint/data/PostRepository.java
package learn.autoblueprint.data;

import learn.autoblueprint.models.Post;
import java.util.List;

public interface PostRepository {
    List<Post> findAll();
    Post findById(int postId);

    List<Post> findByUserId(int userId);

    Post add(Post post);
    boolean update(Post post);
    boolean deleteById(int postId);
}

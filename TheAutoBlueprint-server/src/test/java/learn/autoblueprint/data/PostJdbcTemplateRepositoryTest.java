package learn.autoblueprint.data;

import learn.autoblueprint.TestHelpers;
import learn.autoblueprint.models.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PostJdbcTemplateRepositoryTest {

    private static final int MISSING_ID = 99;

    @Autowired
    PostJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state()");
    }

    @Test
    void findAll() {
        List<Post> posts = repository.findAll();
        assertNotNull(posts);
        assertTrue(posts.size() > 0);
    }

    @Test
    void findById() {
        Post post = repository.findById(1);
        assertNotNull(post);
        assertEquals(1, post.getPostId());
    }

    @Test
    void findByUserId() {
        List<Post> posts = repository.findByUserId(1);
        assertNotNull(posts);
        assertTrue(posts.size() > 0);
    }

    @Test
    void add() {
        Post post = TestHelpers.makeNewPost();
        Post result = repository.add(post);
        assertNotNull(result);
        assertNotNull(result.getPostId());
    }

    @Test
    void update() {
        Post post = repository.findById(1);
        post.setTitle("Updated Title");
        assertTrue(repository.update(post));
        Post updatedPost = repository.findById(1);
        assertEquals("Updated Title", updatedPost.getTitle());
    }

    @Test
    void deleteById() {
        assertTrue(repository.deleteById(1));
        assertNull(repository.findById(1));
    }

    @Test
    void shouldNotFindPostById() {
        Post post = repository.findById(MISSING_ID);
        assertNull(post);
    }

    @Test
    void shouldNotUpdateNonExistentPost() {
        Post post = TestHelpers.makeNewPost();
        post.setPostId(MISSING_ID);
        assertFalse(repository.update(post));
    }

    @Test
    void shouldNotDeleteNonExistentPost() {
        assertFalse(repository.deleteById(MISSING_ID));
    }
}
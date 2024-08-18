package learn.autoblueprint.data;

import learn.autoblueprint.TestHelpers;
import learn.autoblueprint.models.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CommentJdbcTemplateRepositoryTest {

    private static final int MISSING_ID = 99;

    @Autowired
    CommentJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state()");
    }

    @Test
    void findAll() {
        List<Comment> comments = repository.findAll();
        assertNotNull(comments);
        assertTrue(comments.size() > 0);
    }

    @Test
    void findByPostId() {
        List<Comment> comments = repository.findByPostId(1);
        assertNotNull(comments);
        assertTrue(comments.size() > 0);
    }

    @Test
    void findByPostId_shouldNotReturnCommentsForInvalidPost() {
        List<Comment> comments = repository.findByPostId(MISSING_ID);
        assertNotNull(comments);
        assertTrue(comments.isEmpty());
    }

    @Test
    void findByUserId() {
        List<Comment> comments = repository.findByUserId(1);
        assertNotNull(comments);
        assertFalse(comments.isEmpty());
    }

    @Test
    void findByUserId_shouldNotReturnCommentsForInvalidUser() {
        List<Comment> comments = repository.findByUserId(MISSING_ID);
        assertNotNull(comments);
        assertTrue(comments.isEmpty());
    }

    @Test
    void findById() {
        Comment comment = repository.findById(1);
        assertNotNull(comment);
    }

    @Test
    void findById_shouldNotReturnCommentForInvalidId() {
        Comment comment = repository.findById(MISSING_ID);
        assertNull(comment);
    }

    @Test
    void add() {
        Comment comment = TestHelpers.makeNewComment();
        Comment result = repository.add(comment);
        assertNotNull(result);
        assertNotNull(result.getCommentId());
    }

    @Test
    void update() {
        Comment comment = TestHelpers.createValidComment();
        comment.setCommentText("Updated comment text");
        boolean success = repository.update(comment);
        assertTrue(success);

        Comment updatedComment = repository.findById(comment.getCommentId());
        assertEquals("Updated comment text", updatedComment.getCommentText());
    }

    @Test
    void update_shouldNotUpdateInvalidComment() {
        Comment comment = TestHelpers.makeNewComment();
        comment.setCommentId(MISSING_ID);
        boolean success = repository.update(comment);
        assertFalse(success);
    }

    @Test
    void deleteById() {
        boolean success = repository.deleteById(1);
        assertTrue(success);

        Comment comment = repository.findById(1);
        assertNull(comment);
    }

    @Test
    void deleteById_shouldNotDeleteInvalidComment() {
        boolean success = repository.deleteById(MISSING_ID);
        assertFalse(success);
    }
}
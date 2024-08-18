package learn.autoblueprint.domain;

import learn.autoblueprint.TestHelpers;
import learn.autoblueprint.data.CommentRepository;
import learn.autoblueprint.models.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CommentServiceTest {

    @Autowired
    CommentService service;

    @MockBean
    CommentRepository repository;

    @Test
    void findAll() {
        List<Comment> expected = List.of(TestHelpers.createValidComment());
        when(repository.findAll()).thenReturn(expected);

        List<Comment> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findByPostId() {
        List<Comment> expected = List.of(TestHelpers.createValidComment());
        when(repository.findByPostId(1)).thenReturn(expected);

        List<Comment> actual = service.findByPostId(1);
        assertEquals(expected, actual);
    }

    @Test
    void findByPostId_shouldNotReturnCommentsForInvalidPost() {
        when(repository.findByPostId(99)).thenReturn(Collections.emptyList());

        List<Comment> actual = service.findByPostId(99);
        assertTrue(actual.isEmpty());
    }

    @Test
    void findByUserId() {
        List<Comment> expected = List.of(TestHelpers.createValidComment());
        when(repository.findByUserId(1)).thenReturn(expected);

        List<Comment> actual = service.findByUserId(1);
        assertEquals(expected, actual);
    }

    @Test
    void findByUserId_shouldNotReturnCommentsForInvalidUser() {
        when(repository.findByUserId(99)).thenReturn(Collections.emptyList());

        List<Comment> actual = service.findByUserId(99);
        assertTrue(actual.isEmpty());
    }

    @Test
    void findById() {
        Comment expected = TestHelpers.createValidComment();
        when(repository.findById(1)).thenReturn(expected);

        Comment actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void findById_shouldNotReturnCommentForInvalidId() {
        when(repository.findById(99)).thenReturn(null);

        Comment actual = service.findById(99);
        assertNull(actual);
    }

    @Test
    void shouldAdd() {
        Comment comment = TestHelpers.makeNewComment();
        comment.setCommentId(0);

        Comment addedComment = TestHelpers.createValidComment();
        addedComment.setCommentId(1);

        when(repository.add(comment)).thenReturn(addedComment);

        Result<Comment> result = service.add(comment);
        assertTrue(result.isSuccess());
        assertEquals(addedComment, result.getPayload());
    }

    @Test
    void add_shouldNotAddInvalidComment() {
        Comment comment = TestHelpers.makeNewComment();
        comment.setCommentText(null);

        Result<Comment> result = service.add(comment);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("commentText is required"));
    }

    @Test
    void update() {
        Comment comment = TestHelpers.createValidComment();
        comment.setCommentText("Updated comment text");
        when(repository.update(comment)).thenReturn(true);

        Result<Comment> result = service.update(comment);
        assertTrue(result.isSuccess());
    }

    @Test
    void update_shouldNotUpdateInvalidComment() {
        Comment comment = TestHelpers.createValidComment();
        comment.setCommentText(null);

        Result<Comment> result = service.update(comment);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().contains("commentText is required"));
    }

    @Test
    void deleteById() {
        when(repository.deleteById(1)).thenReturn(true);

        boolean result = service.deleteById(1);
        assertTrue(result);
    }

    @Test
    void deleteById_shouldNotDeleteInvalidComment() {
        when(repository.deleteById(99)).thenReturn(false);

        boolean result = service.deleteById(99);
        assertFalse(result);
    }
}
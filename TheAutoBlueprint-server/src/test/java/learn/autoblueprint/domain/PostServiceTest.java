package learn.autoblueprint.domain;

import learn.autoblueprint.TestHelpers;
import learn.autoblueprint.data.PostRepository;
import learn.autoblueprint.models.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PostServiceTest {

    @Autowired
    PostService service;

    @MockBean
    PostRepository repository;

    @BeforeEach
    void setUp() {
        Mockito.reset(repository);
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(TestHelpers.createValidPost()));
        List<Post> posts = service.findAll();
        assertNotNull(posts);
        assertTrue(posts.size() > 0);
    }

    @Test
    void findById() {
        when(repository.findById(1)).thenReturn(TestHelpers.createValidPost());
        Post post = service.findById(1);
        assertNotNull(post);
        assertEquals(1, post.getPostId());
    }

    @Test
    void findByUserId() {
        when(repository.findByUserId(1)).thenReturn(List.of(TestHelpers.createValidPost()));
        List<Post> posts = service.findByUserId(1);
        assertNotNull(posts);
        assertTrue(posts.size() > 0);
    }

    @Test
    void add() {
        Post post = TestHelpers.makeNewPost();
        when(repository.add(post)).thenReturn(post);
        Result<Post> result = service.add(post);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
    }

    @Test
    void update() {
        Post post = TestHelpers.createValidPost();
        when(repository.update(post)).thenReturn(true);
        Result<Post> result = service.update(post);
        assertTrue(result.isSuccess());
    }

    @Test
    void deleteById() {
        when(repository.deleteById(1)).thenReturn(true);
        assertTrue(service.deleteById(1));
    }

    @Test
    void shouldNotFindPostById() {
        when(repository.findById(999)).thenReturn(null);
        Post post = service.findById(999);
        assertNull(post);
    }

    @Test
    void shouldNotAddInvalidPost() {
        Post post = new Post();
        Result<Post> result = service.add(post);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalidPost() {
        Post post = new Post();
        Result<Post> result = service.update(post);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotDeleteNonExistentPost() {
        when(repository.deleteById(999)).thenReturn(false);
        assertFalse(service.deleteById(999));
    }
}
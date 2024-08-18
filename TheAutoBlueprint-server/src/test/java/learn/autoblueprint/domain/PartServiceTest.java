package learn.autoblueprint.domain;

import learn.autoblueprint.TestHelpers;
import learn.autoblueprint.data.PartRepository;
import learn.autoblueprint.models.Part;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PartServiceTest {

    @Autowired
    private PartService service;

    @MockBean
    private PartRepository repository;

    @Test
    void shouldAdd() {
        Part part = TestHelpers.makeNewPart();

        when(repository.add(part)).thenReturn(part);

        Result<Part> result = service.add(part);
        assertTrue(result.isSuccess());
        assertEquals(part, result.getPayload());
    }

    @Test
    void shouldNotAddWhenPartIdIsSet() {
        Part part = TestHelpers.makeExistingPart();

        Result<Part> result = service.add(part);
        assertFalse(result.isSuccess());
        assertEquals("New part must not have id set.", result.getMessages().get(0));
    }

    @Test
    void shouldUpdate() {
        Part part = TestHelpers.makeExistingPart();

        when(repository.update(part)).thenReturn(true);

        Result<Part> result = service.update(part);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateWhenPartIdNotSet() {
        Part part = TestHelpers.makeNewPart();

        Result<Part> result = service.update(part);
        assertFalse(result.isSuccess());
        assertEquals("Existing part must have id set.", result.getMessages().get(0));
    }

    @Test
    void shouldDeleteById() {
        when(repository.deleteById(1)).thenReturn(true);

        boolean result = service.deleteById(1);
        assertTrue(result);
    }

    @Test
    void shouldNotDeleteByIdWhenNotExists() {
        when(repository.deleteById(1)).thenReturn(false);

        boolean result = service.deleteById(1);
        assertFalse(result);
    }

    @Test
    void shouldFindById() {
        Part expected = TestHelpers.makeExistingPart();

        when(repository.findById(1)).thenReturn(expected);

        Part actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAll() {
        List<Part> expected = List.of(
                TestHelpers.makePart(1),
                TestHelpers.makePart(2)
        );

        when(repository.findAll()).thenReturn(expected);

        List<Part> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByCarId() {
        List<Part> expected = List.of(
                TestHelpers.makePart(1),
                TestHelpers.makePart(2)
        );

        when(repository.findByCarId(1)).thenReturn(expected);

        List<Part> actual = service.findByCarId(1);
        assertEquals(expected, actual);
    }
}
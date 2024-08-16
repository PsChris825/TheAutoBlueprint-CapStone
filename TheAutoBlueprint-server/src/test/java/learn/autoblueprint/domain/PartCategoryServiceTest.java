// PartCategoryServiceTest.java
package learn.autoblueprint.domain;

import learn.autoblueprint.data.PartCategoryRepository;
import learn.autoblueprint.models.PartCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PartCategoryServiceTest {

    @Autowired
    PartCategoryService service;

    @MockBean
    PartCategoryRepository repository;

    @Test
    void shouldFindAll() {
        List<PartCategory> expected = List.of(
                new PartCategory(1, "Engine"),
                new PartCategory(2, "Suspension")
        );
        when(repository.findAll()).thenReturn(expected);

        List<PartCategory> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindAllWhenEmpty() {
        when(repository.findAll()).thenReturn(List.of());

        List<PartCategory> actual = service.findAll();
        assertTrue(actual.isEmpty());
    }

    @Test
    void shouldFindById() {
        PartCategory expected = new PartCategory(1, "Engine");
        when(repository.findById(1)).thenReturn(expected);

        PartCategory actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByIdWhenNotExists() {
        when(repository.findById(1)).thenReturn(null);

        PartCategory actual = service.findById(1);
        assertNull(actual);
    }

    @Test
    void shouldAdd() {
        PartCategory partCategory = new PartCategory(0, "Brakes");
        PartCategory savedPartCategory = new PartCategory(4, "Brakes");

        when(repository.existsByName("Brakes")).thenReturn(false);
        when(repository.add(partCategory)).thenReturn(savedPartCategory);

        Result<PartCategory> result = service.add(partCategory);
        assertTrue(result.isSuccess());
        assertEquals(savedPartCategory, result.getPayload());
    }

    @Test
    void shouldNotAddWhenNameExists() {
        PartCategory partCategory = new PartCategory(0, "Brakes");

        when(repository.existsByName("Brakes")).thenReturn(true);

        Result<PartCategory> result = service.add(partCategory);
        assertFalse(result.isSuccess());
        assertEquals("Part Category name already exists.", result.getMessages().get(0));
    }

    @Test
    void shouldUpdate() {
        PartCategory partCategory = new PartCategory(1, "Brakes");

        when(repository.existsByName("Brakes")).thenReturn(false);
        when(repository.update(partCategory)).thenReturn(true);

        Result<PartCategory> result = service.update(partCategory);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateWhenIdNotSet() {
        PartCategory partCategory = new PartCategory(0, "Brakes");

        Result<PartCategory> result = service.update(partCategory);
        assertFalse(result.isSuccess());
        assertEquals("Part Category ID must be set.", result.getMessages().get(0));
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
}
package learn.autoblueprint.data;

import learn.autoblueprint.TestHelpers;
import learn.autoblueprint.models.PartCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PartCategoryJdbcTemplateRepositoryTest {

    private static final int MISSING_ID = 99;

    @Autowired
    PartCategoryJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state()");
    }


    @Test
    void shouldFindAll() {
        List<PartCategory> expected = List.of(
                TestHelpers.makePartCategory(3, "Body"),
                TestHelpers.makePartCategory(1, "Engine"),
                TestHelpers.makePartCategory(2, "Suspension")
        );

        List<PartCategory> actual = repository.findAll();

        assertEquals(expected, actual);
        assertEquals(3, actual.size());
    }


    @Test
    void shouldFindById() {
        PartCategory expected = TestHelpers.makePartCategory(1, "Engine");
        PartCategory actual = repository.findById(1);
        assertEquals(expected, actual);
    }


    @Test
    void shouldAdd() {
        PartCategory partCategory = TestHelpers.makePartCategory(0, "Brakes");

        PartCategory actual = repository.add(partCategory);
        assertNotNull(actual);
        assertEquals(4, actual.getCategoryId());
        assertEquals("Brakes", actual.getCategoryName());
    }

    @Test
    void shouldUpdate() {
        PartCategory partCategory = TestHelpers.makePartCategory(1, "Engine");

        assertTrue(repository.update(partCategory));
    }

    @Test
    void shouldNotUpdateMissing() {
        PartCategory partCategory = TestHelpers.makePartCategory(MISSING_ID, "Engine");

        assertFalse(repository.update(partCategory));
    }

    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(1));
        assertNull(repository.findById(1));
    }

    @Test
    void shouldNotDeleteMissing() {
        assertFalse(repository.deleteById(MISSING_ID));
    }

}
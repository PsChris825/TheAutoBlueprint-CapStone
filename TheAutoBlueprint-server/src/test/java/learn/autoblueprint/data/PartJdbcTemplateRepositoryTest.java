package learn.autoblueprint.data;

import learn.autoblueprint.TestHelpers;
import learn.autoblueprint.models.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PartJdbcTemplateRepositoryTest {

    private static final int MISSING_ID = 99;

    @Autowired
    PartJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state()");
    }

    @Test
    void findAll_shouldReturnParts() {
        List<Part> parts = repository.findAll();
        assertNotNull(parts);
        assertTrue(parts.size() > 0);
    }

    @Test
    void findByCarId_shouldReturnParts() {
        List<Part> parts = repository.findByCarId(1);
        assertNotNull(parts);
        assertTrue(parts.size() > 0);
    }

    @Test
    void findById_shouldReturnPart() {
        Part part = repository.findById(1);
        assertNotNull(part);
        assertEquals(1, part.getPartId());
    }

    @Test
    void add_shouldInsertPart() {
        Part part = TestHelpers.makeNewPart();
        Part result = repository.add(part);
        assertNotNull(result);
        assertNotNull(result.getPartId());
    }

    @Test
    void update_shouldModifyPart() {
        Part part = TestHelpers.createValidPart();
        part.setPartName("Updated Part");
        boolean success = repository.update(part);
        assertTrue(success);
    }

    @Test
    void deleteById_shouldRemovePart() {
        boolean success = repository.deleteById(1);
        assertTrue(success);
    }

    @Test
    void findById_shouldNotReturnPart() {
        Part part = repository.findById(MISSING_ID);
        assertNull(part);
    }

    @Test
    void update_shouldNotModifyPart() {
        Part part = TestHelpers.makePart(MISSING_ID);
        boolean success = repository.update(part);
        assertFalse(success);
    }

    @Test
    void deleteById_shouldNotRemovePart() {
        boolean success = repository.deleteById(MISSING_ID);
        assertFalse(success);
    }
}
package learn.autoblueprint.data;

import learn.autoblueprint.TestHelpers;
import learn.autoblueprint.models.PlanPart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PlanPartJdbcTemplateRepositoryTest {

    private static final int MISSING_ID = 99;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PlanPartJdbcTemplateRepository repository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("call set_known_good_state()");
    }

    @Test
    void findAll_shouldReturnAllPlanParts() {
        List<PlanPart> planParts = repository.findAll();
        assertEquals(2, planParts.size());
    }

    @Test
    void findAll_shouldNotReturnAnyPlanPartsWhenEmpty() {
        repository.deleteById(1);
        repository.deleteById(2);
        List<PlanPart> planParts = repository.findAll();
        assertTrue(planParts.isEmpty());
    }

    @Test
    void findById_shouldReturnPlanPart() {
        PlanPart planPart = repository.findById(1);
        assertNotNull(planPart);
        assertEquals(1, planPart.getPlanPartId());
    }

    @Test
    void findById_shouldNotReturnPlanPartWhenIdNotFound() {
        PlanPart planPart = repository.findById(999);
        assertNull(planPart);
    }

    @Test
    void findByPlanId_shouldReturnPlanParts() {
        List<PlanPart> planParts = repository.findByPlanId(1);
        assertFalse(planParts.isEmpty());
    }

    @Test
    void findByPlanId_shouldNotReturnPlanPartsWhenIdNotFound() {
        List<PlanPart> planParts = repository.findByPlanId(999);
        assertTrue(planParts.isEmpty());
    }

    @Test
    void add_shouldAddNewPlanPart() {
        PlanPart newPlanPart = TestHelpers.makeNewPlanPart();
        PlanPart addedPlanPart = repository.add(newPlanPart);
        assertNotNull(addedPlanPart);
        assertTrue(addedPlanPart.getPlanPartId() > 0);
    }

    @Test
    void add_shouldNotAddInvalidPlanPart() {
        PlanPart invalidPlanPart = new PlanPart(); // Missing required fields
        assertThrows(Exception.class, () -> repository.add(invalidPlanPart));
    }

    @Test
    void update_shouldUpdatePlanPart() {
        PlanPart planPart = repository.findById(1);
        planPart.setPrice(planPart.getPrice().add(BigDecimal.TEN));
        boolean success = repository.update(planPart);
        assertTrue(success);
    }

    @Test
    void update_shouldNotUpdateNonExistentPlanPart() {
        PlanPart nonExistentPlanPart = TestHelpers.makeNewPlanPart();
        nonExistentPlanPart.setPlanPartId(999);
        boolean success = repository.update(nonExistentPlanPart);
        assertFalse(success);
    }

    @Test
    void delete_shouldDeletePlanPart() {
        boolean success = repository.deleteById(1);
        assertTrue(success);
    }

    @Test
    void delete_shouldNotDeleteNonExistentPlanPart() {
        boolean success = repository.deleteById(999);
        assertFalse(success);
    }
}
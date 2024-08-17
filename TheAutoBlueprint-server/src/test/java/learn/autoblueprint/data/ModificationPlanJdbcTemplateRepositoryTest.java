package learn.autoblueprint.data;

import learn.autoblueprint.TestHelpers;
import learn.autoblueprint.models.ModificationPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ModificationPlanJdbcTemplateRepositoryTest {

    private static final int MISSING_ID = 99;

    @Autowired
    ModificationPlanJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state()");
    }

    @Test
    void findAll_ShouldReturnAllModificationPlans() {
        List<ModificationPlan> plans = repository.findAll();

        assertNotNull(plans);
        assertFalse(plans.isEmpty()); // Ensure the list is not empty

        // Check that each plan has the necessary fields set
        for (ModificationPlan plan : plans) {
            assertNotNull(plan.getPlanId());
            assertNotNull(plan.getPlanName());
            // Add additional assertions as needed
        }
    }

    @Test
    void findByAppUserId_ShouldReturnPlansForUser() {
        List<ModificationPlan> plans = repository.findByAppUserId(1);
        assertNotNull(plans);
        assertTrue(plans.size() > 0);
    }

    @Test
    void findByAppUserId_ShouldNotReturnPlansForNonExistentUser() {
        List<ModificationPlan> plans = repository.findByAppUserId(MISSING_ID);
        assertNotNull(plans);
        assertTrue(plans.isEmpty());
    }

    @Test
    void findById_ShouldReturnModificationPlan() {
        ModificationPlan plan = repository.findById(1);
        assertNotNull(plan);
        assertEquals(1, plan.getPlanId());
    }

    @Test
    void findById_ShouldNotReturnNonExistentModificationPlan() {
        ModificationPlan plan = repository.findById(MISSING_ID);
        assertNull(plan);
    }

    @Test
    void add_ShouldAddModificationPlan() {
        ModificationPlan plan = TestHelpers.createValidModificationPlan();
        plan.setPlanId(0); // Ensure the ID is not set
        ModificationPlan result = repository.add(plan);
        assertNotNull(result);
        assertTrue(result.getPlanId() > 0);
    }

    @Test
    void update_ShouldUpdateModificationPlan() {
        ModificationPlan plan = TestHelpers.createValidModificationPlan();
        plan.setPlanId(1);
        plan.setPlanName("Updated Plan Name");
        boolean success = repository.update(plan);
        assertTrue(success);
        ModificationPlan updatedPlan = repository.findById(1);
        assertEquals("Updated Plan Name", updatedPlan.getPlanName());
    }

    @Test
    void update_ShouldNotUpdateNonExistentModificationPlan() {
        ModificationPlan plan = TestHelpers.createValidModificationPlan();
        plan.setPlanId(MISSING_ID);
        boolean success = repository.update(plan);
        assertFalse(success);
    }

    @Test
    void deleteById_ShouldDeleteModificationPlan() {
        boolean success = repository.deleteById(1);
        assertTrue(success);
        ModificationPlan plan = repository.findById(1);
        assertNull(plan);
    }

    @Test
    void deleteById_ShouldNotDeleteNonExistentModificationPlan() {
        boolean success = repository.deleteById(MISSING_ID);
        assertFalse(success);
    }
}
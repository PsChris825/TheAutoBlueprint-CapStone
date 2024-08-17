package learn.autoblueprint.domain;

import learn.autoblueprint.TestHelpers;
import learn.autoblueprint.data.ModificationPlanRepository;
import learn.autoblueprint.models.ModificationPlan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ModificationPlanServiceTest {

    @Autowired
    private ModificationPlanService service;

    @MockBean
    private ModificationPlanRepository repository;

    @Test
    void shouldFindAll() {
        List<ModificationPlan> plans = Collections.singletonList(TestHelpers.createValidModificationPlan());
        when(repository.findAll()).thenReturn(plans);

        List<ModificationPlan> result = service.findAll();

        assertNotNull(result);
        assertEquals(plans, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldFindByAppUserId() {
        List<ModificationPlan> plans = Collections.singletonList(TestHelpers.createValidModificationPlan());
        when(repository.findByAppUserId(1)).thenReturn(plans);

        List<ModificationPlan> result = service.findByAppUserId(1);

        assertNotNull(result);
        assertEquals(plans, result);
        verify(repository, times(1)).findByAppUserId(1);
    }

    @Test
    void shouldFindById() {
        ModificationPlan plan = TestHelpers.createValidModificationPlan();
        when(repository.findById(1)).thenReturn(plan);

        ModificationPlan result = service.findById(1);

        assertNotNull(result);
        assertEquals(plan, result);
        verify(repository, times(1)).findById(1);
    }

    @Test
    void shouldAddValidModificationPlan() {
        ModificationPlan plan = TestHelpers.createValidModificationPlan();
        when(repository.add(plan)).thenReturn(plan);

        ModificationPlan result = service.add(plan);

        assertNotNull(result);
        assertEquals(plan, result);
        verify(repository, times(1)).add(plan);
    }

    @Test
    void shouldUpdateValidModificationPlan() {
        ModificationPlan plan = TestHelpers.createValidModificationPlan();
        when(repository.update(plan)).thenReturn(true);

        boolean result = service.update(plan);

        assertTrue(result);
        verify(repository, times(1)).update(plan);
    }

    @Test
    void shouldDeleteById() {
        when(repository.deleteById(1)).thenReturn(true);

        boolean result = service.delete(1);

        assertTrue(result);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void shouldNotAddInvalidModificationPlan() {
        ModificationPlan plan = TestHelpers.makeNewModificationPlan();
        plan.setPlanName(null); // Invalid plan

        ModificationPlan result = service.add(plan);

        assertNull(result);
        verify(repository, times(0)).add(plan);
    }

    @Test
    void shouldNotUpdateInvalidModificationPlan() {
        ModificationPlan plan = TestHelpers.makeNewModificationPlan();
        plan.setPlanName(null); // Invalid plan

        boolean result = service.update(plan);

        assertFalse(result);
        verify(repository, times(0)).update(plan);
    }

    @Test
    void shouldNotFindByInvalidAppUserId() {
        when(repository.findByAppUserId(-1)).thenReturn(Collections.emptyList());

        List<ModificationPlan> result = service.findByAppUserId(-1);

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByAppUserId(-1);
    }

    @Test
    void shouldNotFindByInvalidId() {
        when(repository.findById(-1)).thenReturn(null);

        ModificationPlan result = service.findById(-1);

        assertNull(result);
        verify(repository, times(1)).findById(-1);
    }
}
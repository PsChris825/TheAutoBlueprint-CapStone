package learn.autoblueprint.domain;

import learn.autoblueprint.data.PlanPartRepository;
import learn.autoblueprint.models.PlanPart;
import learn.autoblueprint.TestHelpers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PlanPartServiceTest {

    @Autowired
    PlanPartService service;

    @MockBean
    PlanPartRepository repository;

    @Test
    void findAll_shouldReturnAllPlanParts() {
        List<PlanPart> expected = List.of(TestHelpers.createValidPlanPart(), TestHelpers.createValidPlanPart());
        when(repository.findAll()).thenReturn(expected);

        List<PlanPart> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldNotReturnAnyPlanPartsWhenEmpty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<PlanPart> actual = service.findAll();
        assertTrue(actual.isEmpty());
    }

    @Test
    void findByPlanId_shouldReturnPlanParts() {
        List<PlanPart> expected = List.of(TestHelpers.createValidPlanPart(), TestHelpers.createValidPlanPart());
        when(repository.findByPlanId(1)).thenReturn(expected);

        List<PlanPart> actual = service.findByPlanId(1);
        assertEquals(expected, actual);
    }

    @Test
    void findByPlanId_shouldNotReturnPlanPartsWhenIdNotFound() {
        when(repository.findByPlanId(999)).thenReturn(Collections.emptyList());

        List<PlanPart> actual = service.findByPlanId(999);
        assertTrue(actual.isEmpty());
    }

    @Test
    void findById_shouldReturnPlanPart() {
        PlanPart expected = TestHelpers.createValidPlanPart();
        when(repository.findById(1)).thenReturn(expected);

        PlanPart actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void findById_shouldNotReturnPlanPartWhenIdNotFound() {
        when(repository.findById(999)).thenReturn(null);

        PlanPart actual = service.findById(999);
        assertNull(actual);
    }

    @Test
    void add_shouldAddNewPlanPart() {
        PlanPart planPart = TestHelpers.makeNewPlanPart();
        planPart.setPlanPartId(0);

        PlanPart addedPlanPart = TestHelpers.makeNewPlanPart();
        addedPlanPart.setPlanPartId(1);

        when(repository.add(planPart)).thenReturn(addedPlanPart);

        Result<PlanPart> result = service.add(planPart);
        assertTrue(result.isSuccess());
        assertEquals(addedPlanPart, result.getPayload());
    }

    @Test
    void add_shouldNotAddInvalidPlanPart() {
        PlanPart invalidPlanPart = new PlanPart(); // Missing required fields

        Result<PlanPart> result = service.add(invalidPlanPart);
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessages());
    }

    @Test
    void update_shouldUpdatePlanPart() {
        PlanPart planPart = TestHelpers.makeExistingPlanPart();
        planPart.setPrice(BigDecimal.TEN);
        when(repository.update(planPart)).thenReturn(true);

        Result<PlanPart> result = service.update(planPart);
        assertTrue(result.isSuccess());
    }

    @Test
    void update_shouldNotUpdateNonExistentPlanPart() {
        PlanPart nonExistentPlanPart = TestHelpers.makeNewPlanPart();
        nonExistentPlanPart.setPlanPartId(999);
        when(repository.update(nonExistentPlanPart)).thenReturn(false);

        Result<PlanPart> result = service.update(nonExistentPlanPart);
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessages());
    }

    @Test
    void deleteById_shouldDeletePlanPart() {
        when(repository.deleteById(1)).thenReturn(true);

        boolean success = service.deleteById(1);
        assertTrue(success);
    }

    @Test
    void deleteById_shouldNotDeleteNonExistentPlanPart() {
        when(repository.deleteById(999)).thenReturn(false);

        boolean success = service.deleteById(999);
        assertFalse(success);
    }
}
package learn.autoblueprint.data;

import learn.autoblueprint.models.PlanPart;

import java.util.List;

public interface PlanPartRepository {
    List<PlanPart> findAll();

    PlanPart findById(int planPartId);

    List<PlanPart> findByPlanId(int planId);

    PlanPart add(PlanPart planPart);

    boolean update(PlanPart planPart);

    boolean deleteById(int planPartId);
}

package learn.autoblueprint.data;


import learn.autoblueprint.models.ModificationPlan;
import learn.autoblueprint.models.PlanPart;

import java.util.List;

public interface ModificationPlanRepository {

    List<ModificationPlan> findAll();


    List<PlanPart> findPlanPartsByModificationPlanId(int planId);

    List<ModificationPlan> findByAppUserId(int appUserId);

    ModificationPlan findById(int planId);

    ModificationPlan add(ModificationPlan modificationPlan);

    boolean update(ModificationPlan modificationPlan);

    boolean deleteById(int planId);
}

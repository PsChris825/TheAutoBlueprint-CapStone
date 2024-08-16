package learn.autoblueprint.data;


import learn.autoblueprint.models.ModificationPlan;

import java.util.List;

public interface ModificationPlanRepository {

    List<ModificationPlan> findAll();

    List<ModificationPlan> findByAppUserId(int userId);

    ModificationPlan findById(int modificationPlanId);

    ModificationPlan add(ModificationPlan modificationPlan);

    boolean update(ModificationPlan modificationPlan);

    boolean deleteById(int modificationPlanId);
}

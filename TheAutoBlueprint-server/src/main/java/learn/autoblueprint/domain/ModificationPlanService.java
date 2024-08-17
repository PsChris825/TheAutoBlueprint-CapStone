package learn.autoblueprint.domain;

import learn.autoblueprint.data.ModificationPlanRepository;
import learn.autoblueprint.models.ModificationPlan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModificationPlanService {

    private final ModificationPlanRepository repository;

    public ModificationPlanService(ModificationPlanRepository repository) {
        this.repository = repository;
    }

    public List<ModificationPlan> findAll() {
        return repository.findAll();
    }

    public List<ModificationPlan> findByAppUserId(int appUserId) {
        return repository.findByAppUserId(appUserId);
    }

    public ModificationPlan findById(int planId) {
        return repository.findById(planId);
    }

    public ModificationPlan add(ModificationPlan modificationPlan) {
        Result<ModificationPlan> result = validate(modificationPlan);
        if (!result.isSuccess()) {
            return null;
        }
        modificationPlan.calculatePlanHours();
        modificationPlan.calculateTotalCost();
        return repository.add(modificationPlan);
    }

    public boolean update(ModificationPlan modificationPlan) {
        Result<ModificationPlan> result = validate(modificationPlan);
        if (!result.isSuccess()) {
            return false;
        }
        modificationPlan.calculatePlanHours();
        modificationPlan.calculateTotalCost();
        return repository.update(modificationPlan);
    }

    public boolean delete(int planId) {
        return repository.deleteById(planId);
    }

    private Result<ModificationPlan> validate(ModificationPlan modificationPlan) {
        Result<ModificationPlan> result = new Result<>();
        if (modificationPlan == null) {
            result.addMessage("Modification Plan cannot be null.");
            return result;
        }
        if (modificationPlan.getPlanName() == null || modificationPlan.getPlanName().isBlank()) {
            result.addMessage("Plan Name is required.");
        }
        if (modificationPlan.getPlanDescription() == null || modificationPlan.getPlanDescription().isBlank()) {
            result.addMessage("Plan Description is required.");
        }
        if (modificationPlan.getCarId() <= 0) {
            result.addMessage("Car Id is required.");
        }
        if (modificationPlan.getAppUserId() <= 0) {
            result.addMessage("App User Id is required.");
        }
        return result;
    }
}
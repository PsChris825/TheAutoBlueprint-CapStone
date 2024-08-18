package learn.autoblueprint.domain;

import learn.autoblueprint.data.PlanPartRepository;
import learn.autoblueprint.models.PlanPart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanPartService {

    private final PlanPartRepository repository;

    public PlanPartService(PlanPartRepository repository) {
        this.repository = repository;
    }

    public List<PlanPart> findAll() {
        return repository.findAll();
    }

    public List<PlanPart> findByPlanId(int planId) {
        return repository.findByPlanId(planId);
    }

    public PlanPart findById(int planPartId) {
        return repository.findById(planPartId);
    }

    public Result<PlanPart> add(PlanPart planPart) {
        Result<PlanPart> result = validate(planPart);

        if (!result.isSuccess()) {
            return result;
        }

        if (planPart.getPlanPartId() != 0) {
            result.addMessage("New plan part must not have id set.");
            return result;
        }

        planPart = repository.add(planPart);
        result.setPayload(planPart);
        return result;
    }

    public Result<PlanPart> update(PlanPart planPart) {
        Result<PlanPart> result = validate(planPart);

        if (!result.isSuccess()) {
            return result;
        }

        if (planPart.getPlanPartId() <= 0) {
            result.addMessage("Existing plan part must have id set.");
            return result;
        }

        if (!repository.update(planPart)) {
            String msg = String.format("Plan part id %s not found.", planPart.getPlanPartId());
            result.addMessage(msg);
        }

        return result;
    }

    public boolean deleteById(int planPartId) {
        return repository.deleteById(planPartId);
    }

    private Result<PlanPart> validate(PlanPart planPart) {
        Result<PlanPart> result = new Result<>();

        if (planPart == null) {
            result.addMessage("Plan part cannot be null.");
            return result;
        }

        if (planPart.getPlan() == null || planPart.getPlan().getPlanId() <= 0) {
            result.addMessage("Plan part must have a valid plan id.");
        }

        if (planPart.getPart() == null || planPart.getPart().getPartId() <= 0) {
            result.addMessage("Plan part must have a valid part id.");
        }

        return result;
    }
}
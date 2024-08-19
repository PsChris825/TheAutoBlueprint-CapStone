package learn.autoblueprint.domain;

import learn.autoblueprint.data.PlanPartRepository;
import learn.autoblueprint.models.PlanPart;
import learn.autoblueprint.domain.Result;
import learn.autoblueprint.domain.ResultType;
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

        if (planPart.getPlanPartId() != null && planPart.getPlanPartId() != 0) {
            result.addMessage("New plan part must not have id set.", ResultType.INVALID);
            return result;
        }

        planPart.setPlanPartId(null);
        planPart = repository.add(planPart);

        if (planPart.getPlanPartId() == null || planPart.getPlanPartId() <= 0) {
            result.addMessage("Failed to generate a valid planPartId.", ResultType.INVALID);
            return result;
        }

        result.setPayload(planPart);
        return result;
    }



    public Result<PlanPart> update(PlanPart planPart) {
        Result<PlanPart> result = validate(planPart);

        if (!result.isSuccess()) {
            return result;
        }

        if (planPart.getPlanPartId() <= 0) {
            result.addMessage("Existing plan part must have id set.", ResultType.INVALID);
            return result;
        }

        if (!repository.update(planPart)) {
            String msg = String.format("Plan part id %s not found.", planPart.getPlanPartId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int planPartId) {
        return repository.deleteById(planPartId);
    }

    private Result<PlanPart> validate(PlanPart planPart) {
        Result<PlanPart> result = new Result<>();

        if (planPart == null) {
            result.addMessage("Plan part cannot be null.", ResultType.INVALID);
            return result;
        }

        if (planPart.getPlanId() <= 0) {
            result.addMessage("Plan part must have a valid plan id.", ResultType.INVALID);
        }

        if (planPart.getPartId() <= 0) {
            result.addMessage("Plan part must have a valid part id.", ResultType.INVALID);
        }

        return result;
    }
}
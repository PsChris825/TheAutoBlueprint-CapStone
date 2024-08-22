package learn.autoblueprint.controllers;

import learn.autoblueprint.domain.PlanPartService;
import learn.autoblueprint.models.PlanPart;
import learn.autoblueprint.domain.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plan-part")
public class PlanPartController {

    private final PlanPartService service;

    public PlanPartController(PlanPartService service) {
        this.service = service;
    }

    @GetMapping
    public List<PlanPart> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanPart> findById(@PathVariable int id) {
        PlanPart planPart = service.findById(id);
        if (planPart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(planPart);
    }

    @GetMapping("/plan/{planId}")
    public List<PlanPart> findByPlanId(@PathVariable int planId) {
        return service.findByPlanId(planId);
    }

    @PostMapping
    public ResponseEntity<Result<PlanPart>> add(@RequestBody PlanPart planPart) {
        Result<PlanPart> result = service.add(planPart);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<PlanPart>> update(@PathVariable int id, @RequestBody PlanPart planPart) {
        planPart.setPlanPartId(id);
        Result<PlanPart> result = service.update(planPart);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        if (service.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
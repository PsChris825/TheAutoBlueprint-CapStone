package learn.autoblueprint.controllers;

import learn.autoblueprint.domain.ModificationPlanService;
import learn.autoblueprint.models.ModificationPlan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modification-plan")
public class ModificationPlanController {

    private final ModificationPlanService service;

    public ModificationPlanController(ModificationPlanService service) {
        this.service = service;
    }

    @GetMapping
    public List<ModificationPlan> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModificationPlan> findById(@PathVariable int id) {
        ModificationPlan plan = service.findById(id);
        if (plan == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @GetMapping("/appUser/{appUserId}")
    public List<ModificationPlan> findByAppUserId(@PathVariable int appUserId) {
        return service.findByAppUserId(appUserId);
    }

    @PostMapping
    public ResponseEntity<ModificationPlan> add(@RequestBody ModificationPlan modificationPlan) {
        ModificationPlan result = service.add(modificationPlan);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody ModificationPlan modificationPlan) {
        modificationPlan.setPlanId(id);
        boolean success = service.update(modificationPlan);
        if (!success) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean success = service.delete(id);
        if (!success) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
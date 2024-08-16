package learn.autoblueprint.controllers;


import learn.autoblueprint.domain.PartCategoryService;
import learn.autoblueprint.domain.Result;
import learn.autoblueprint.models.AppUser;
import learn.autoblueprint.models.PartCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/part-category")
public class PartCategoryController {

    private final PartCategoryService service;

    public PartCategoryController(PartCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<PartCategory> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PartCategory findById(int id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestParam PartCategory partCategory, @AuthenticationPrincipal AppUser user) {
        Result<PartCategory> result = service.add(partCategory);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestParam PartCategory partCategory, @AuthenticationPrincipal AppUser user) {
        Result<PartCategory> result = service.update(partCategory);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id, @AuthenticationPrincipal AppUser user) {
        if (service.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

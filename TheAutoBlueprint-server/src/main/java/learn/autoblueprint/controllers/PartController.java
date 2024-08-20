package learn.autoblueprint.controllers;

import learn.autoblueprint.domain.PartService;
import learn.autoblueprint.models.Part;
import learn.autoblueprint.domain.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
public class PartController {

    private final PartService service;

    public PartController(PartService service) {
        this.service = service;
    }

    @GetMapping
    public List<Part> findAll() {
        return service.findAll();
    }

    @GetMapping("/{partId}")
    public Part findById(@PathVariable int partId) {
        return service.findById(partId);
    }

    @GetMapping("/car/{carId}")
    public List<Part> findByCarId(@PathVariable int carId) {
        return service.findByCarId(carId);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Part>> findByCategoryId(@PathVariable int categoryId) {
        List<Part> parts = service.findByCategoryId(categoryId);
        if (parts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(parts);
    }

    @PostMapping
    public ResponseEntity<Result<Part>> add(@RequestBody Part part) {
        Result<Part> result = service.add(part);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{partId}")
    public ResponseEntity<Result<Part>> update(@PathVariable int partId, @RequestBody Part part) {
        part.setPartId(partId);
        Result<Part> result = service.update(part);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{partId}")
    public ResponseEntity<?> deleteById(@PathVariable int partId) {
        if (service.deleteById(partId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


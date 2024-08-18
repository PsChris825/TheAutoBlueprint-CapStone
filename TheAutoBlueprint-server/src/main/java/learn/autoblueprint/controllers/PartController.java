package learn.autoblueprint.controllers;

import learn.autoblueprint.domain.PartService;
import learn.autoblueprint.domain.Result;
import learn.autoblueprint.models.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
public class PartController {

    private final PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping
    public List<Part> findAll() {
        return partService.findAll();
    }

    @GetMapping("/car/{carId}")
    public List<Part> findByCarId(@PathVariable int carId) {
        return partService.findByCarId(carId);
    }

    @GetMapping("/{partId}")
    public Part findById(@PathVariable int partId) {
        return partService.findById(partId);
    }

    @PostMapping
    public Result<Part> add(@RequestBody Part part) {
        return partService.add(part);
    }

    @PutMapping("/{partId}")
    public Result<Part> update(@PathVariable int partId, @RequestBody Part part) {
        part.setPartId(partId);
        return partService.update(part);
    }

    @DeleteMapping("/{partId}")
    public boolean deleteById(@PathVariable int partId) {
        return partService.deleteById(partId);
    }
}

package learn.autoblueprint.controllers;

import learn.autoblueprint.domain.AutoPartsService;
import learn.autoblueprint.models.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/external-car")
public class ExternalCarController {

    private final AutoPartsService autoPartsService;

    @Autowired
    public ExternalCarController(AutoPartsService autoPartsService) {
        this.autoPartsService = autoPartsService;
    }

    @GetMapping("/{make}/{model}/{year}")
    public ResponseEntity<Car> getCarData(@PathVariable String make, @PathVariable String model, @PathVariable int year) {
        try {
            Car car = autoPartsService.getCarData(make, model, year);
            return ResponseEntity.ok(car);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}

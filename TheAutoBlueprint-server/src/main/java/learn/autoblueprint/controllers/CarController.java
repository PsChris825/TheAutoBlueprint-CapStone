package learn.autoblueprint.controllers;

import learn.autoblueprint.domain.CarService;
import learn.autoblueprint.domain.Result;
import learn.autoblueprint.domain.ResultType;
import learn.autoblueprint.models.AppUser;
import learn.autoblueprint.models.Car;
import learn.autoblueprint.models.Make;
import learn.autoblueprint.models.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> findById(@PathVariable int id) {
        Car car = service.findById(id);
        if (car == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(car);
        }
    }

    @GetMapping("/make/{make}")
    public ResponseEntity<List<Car>> findByMake(@PathVariable String make) {
        List<Car> cars = service.findByMake(make);
        if (cars.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(cars);
        }
    }

    @GetMapping("/model/{model}")
    public ResponseEntity<List<Car>> findByModel(@PathVariable String model) {
        List<Car> cars = service.findByModel(model);
        if (cars.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(cars);
        }
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<Car>> findByYear(@PathVariable int year) {
        List<Car> cars = service.findByYear(year);
        if (cars.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(cars);
        }
    }

    @GetMapping("/make/{make}/model/{model}/year/{year}")
    public ResponseEntity<List<Car>> findByMakeModelYear(@PathVariable String make, @PathVariable String model, @PathVariable int year) {
        List<Car> cars = service.findByMakeModelYear(make, model, year);
        if (cars.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(cars);
        }
    }

    @GetMapping("/makes")
    public ResponseEntity<List<Make>> findAllMakes() {
        List<Make> makes = service.findAllMakes();
        return ResponseEntity.ok().body(makes);
    }

    @GetMapping("/models")
    public ResponseEntity<List<Model>> findModelsByMake(@RequestParam String make) {
        List<Model> models = service.findModelsByMake(make);
        return ResponseEntity.ok().body(models);
    }

    @GetMapping("/years/{make}/{model}")
    public ResponseEntity<List<Integer>> getYearsByMakeAndModel(@PathVariable String make, @PathVariable String model) {
        return ResponseEntity.ok(service.findYearsByMakeAndModel(make, model));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Car car, @AuthenticationPrincipal AppUser user) {
        Result<Car> result = service.add(car);
        if (result.isSuccess()) {
            return ResponseEntity.ok().body(result.getPayload());
        } else {
            return ResponseEntity.badRequest().body(result.getMessages());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Car car, @AuthenticationPrincipal AppUser user) {
        if (car.getCarId() == null || id != car.getCarId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Car> result = service.update(car);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (result.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, @AuthenticationPrincipal AppUser user) {
        Result<Void> result = service.deleteById(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (result.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
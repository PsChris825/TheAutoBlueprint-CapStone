package learn.autoblueprint.controllers;


import learn.autoblueprint.domain.CarService;
import learn.autoblueprint.domain.Result;
import learn.autoblueprint.domain.ResultType;
import learn.autoblueprint.models.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Car car) {
        Result<Car> result = service.add(car);
        if (result.isSuccess()) {
            return ResponseEntity.ok().body(result.getPayload());
        } else {
            return ResponseEntity.badRequest().body(result.getMessages());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Car car) {
        if (id != car.getCarId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Car> result = service.update(car);
        if (result.isSuccess()) {
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (result.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
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

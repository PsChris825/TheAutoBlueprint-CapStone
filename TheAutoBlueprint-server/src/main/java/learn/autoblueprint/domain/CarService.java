package learn.autoblueprint.domain;

import learn.autoblueprint.data.CarRepository;
import learn.autoblueprint.models.Car;
import learn.autoblueprint.models.Make;
import learn.autoblueprint.models.Model;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class CarService {

    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Car findById(int id) {
        return repository.findById(id);
    }

    public Result<Car> add(Car car) {
        Result<Car> result = validate(car);

        if (!result.isSuccess()) {
            return result;
        }

        if (car.getCarId() != null && car.getCarId() != 0) {
            result.addMessage("New car must not have id set.");
            return result;
        }

        car.setCarId(0);
        car = repository.add(car);
        result.setPayload(car);
        return result;
    }

    public Result<Car> update(Car car) {
        Result<Car> result = validate(car);

        if (!result.isSuccess()) {
            return result;
        }

        if (car.getCarId() <= 0) {
            result.addMessage("Existing car must have id set.");
            return result;
        }

        if (!repository.update(car)) {
            String msg = String.format("Car id %s was not found.", car.getCarId());
            result.addMessage(msg);
        }

        return result;
    }

    public Result<Void> deleteById(int carId) {
        Result<Void> result = new Result<>();
        boolean success = repository.deleteById(carId);
        if (!success) {
            result.addMessage(ResultType.NOT_FOUND, "Car id " + carId + " not found.");
        }
        return result;
    }

    public List<Car> findByMake(String make) {
        return repository.findByMake(make);
    }

    public List<Car> findByModel(String model) {
        return repository.findByModel(model);
    }

    public List<Car> findByYear(int year) {
        return repository.findByYear(year);
    }

    public List<Make> findAllMakes() {
        return repository.findDistinctMakes();
    }

    public List<Model> findModelsByMake(String make) {
        return repository.findDistinctModelsByMake(make);
    }

    public List<Integer> findYearsByMakeAndModel(String make, String model) {
        return repository.findDistinctYearsByMakeAndModel(make, model);
    }

    private Result<Car> validate(Car car) {
        Result<Car> result = new Result<>();

        if (car == null) {
            result.addMessage("Car cannot be null.");
            return result;
        }

        if (car.getMake() == null || car.getMake().isBlank()) {
            result.addMessage("Make is required.");
        }

        if (car.getModel() == null || car.getModel().isBlank()) {
            result.addMessage("Model is required.");
        }

        if (car.getYear() == null) {
            result.addMessage("Year is required.");
        } else {
            int currentYear = Year.now().getValue();
            if (car.getYear() < 1885 || car.getYear() > currentYear + 1) {
                result.addMessage("Year must be between 1885 and " + (currentYear + 1) + ".");
            }
        }

        return result;
    }
}

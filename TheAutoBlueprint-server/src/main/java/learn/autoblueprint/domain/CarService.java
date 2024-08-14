package learn.autoblueprint.domain;

import learn.autoblueprint.data.CarRepository;
import learn.autoblueprint.models.Car;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car addCar(Car car) {
        validateCar(car);
        return carRepository.add(car);
    }

    public Car findCarById(int carId) {
        validateCarId(carId);
        return carRepository.findById(carId);
    }

    private void validateCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car must not be null");
        }
        if (car.getMake() == null || car.getMake().trim().isEmpty()) {
            throw new IllegalArgumentException("Car make must not be empty");
        }
        if (car.getModel() == null || car.getModel().trim().isEmpty()) {
            throw new IllegalArgumentException("Car model must not be empty");
        }
        if (car.getYear() < 1900 || car.getYear() > LocalDate.now().getYear() + 1) {
            throw new IllegalArgumentException("Car year must be between 1900 and " + (LocalDate.now().getYear() + 1));
        }

        if (car.getPower() != null && car.getPower() <= 0) {
            throw new IllegalArgumentException("Car power must be positive if specified");
        }
    }

    private void validateCarId(int carId) {
        if (carId <= 0) {
            throw new IllegalArgumentException("Car ID must be positive");
        }
    }
}
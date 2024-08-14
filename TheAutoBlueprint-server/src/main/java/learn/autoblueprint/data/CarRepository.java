package learn.autoblueprint.data;

import learn.autoblueprint.models.Car;

public interface CarRepository {
    Car findById(int carId);

    Car add(Car car);
}

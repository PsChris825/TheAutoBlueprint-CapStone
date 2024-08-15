package learn.autoblueprint.data;

import learn.autoblueprint.models.Car;
import org.springframework.transaction.annotation.Transactional;

public interface CarRepository {

    @Transactional
    Car findById(int carId);

    Car add(Car car);

    boolean update(Car car);

    boolean deleteById(int carId);
}

package learn.autoblueprint.data;

import learn.autoblueprint.models.Car;
import learn.autoblueprint.models.Make;
import learn.autoblueprint.models.Model;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CarRepository {

    @Transactional
    Car findById(int carId);

    Car add(Car car);

    boolean update(Car car);

    boolean deleteById(int carId);

    List<Car> findByMake(String make);

    List<Car> findByModel(String model);

    List<Car> findByYear(int year);

    List<Make> findDistinctMakes();

    List<Model> findDistinctModelsByMake(String make);

    List<Integer> findDistinctYearsByMakeAndModel(String make, String model);

    List<Car> findByMakeModelYear(String make, String model, int year);
}

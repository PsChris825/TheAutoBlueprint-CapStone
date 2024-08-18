package learn.autoblueprint.data;

import learn.autoblueprint.models.Part;

import java.util.List;

public interface PartRepository {

    List<Part> findAll();

    List<Part> findByCarId(int carId);

    Part findById(int partId);

    Part add(Part part);

    boolean update(Part part);

    boolean deleteById(int partId);
}

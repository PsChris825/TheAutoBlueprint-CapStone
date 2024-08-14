package learn.autoblueprint.data;

import learn.autoblueprint.data.mappers.CarMapper;
import learn.autoblueprint.models.Car;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CarJdbcTemplateRepository implements CarRepository{

    private final JdbcTemplate jdbcTemplate;


    public CarJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Car findById(int carId) {
        final String sql = "SELECT * FROM car WHERE car_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new CarMapper(), carId);
        } catch (EmptyResultDataAccessException e) {
            return null; // or throw a custom exception
        }
    }

    @Override
    public Car add(Car car) {
        final String sql = "INSERT INTO car (make, model, year, engine, power, drive_type, transmission_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.getEngine(),
                car.getPower(),
                car.getDriveType(),
                car.getTransmissionType());

        // Use appropriate method for your database to fetch the last inserted ID
        int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        car.setCarId(id);

        return car;
    }


}

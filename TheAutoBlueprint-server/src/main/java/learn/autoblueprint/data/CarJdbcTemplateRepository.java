package learn.autoblueprint.data;

import learn.autoblueprint.data.mappers.CarMapper;
import learn.autoblueprint.models.Car;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class CarJdbcTemplateRepository implements CarRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CarMapper carMapper = new CarMapper();

    public CarJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Car findById(int id) {
        final String sql = "SELECT car_id, make, model, year, engine, power, drive_type, transmission_type FROM car WHERE car_id = ?";
        return jdbcTemplate.queryForObject(sql, carMapper, id);
    }

    @Override
    public Car add(Car car) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("car")
                .usingGeneratedKeyColumns("car_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("make", car.getMake());
        args.put("model", car.getModel());
        args.put("year", car.getYear());
        args.put("engine", car.getEngine());
        args.put("power", car.getPower());
        args.put("drive_type", car.getDriveType());
        args.put("transmission_type", car.getTransmissionType());
        car.setCarId(insert.executeAndReturnKey(args).intValue());
        return car;
    }

    @Override
    public boolean update(Car car) {
        final String sql = """
                UPDATE car SET
                    make = ?,
                    model = ?,
                    year = ?,
                    engine = ?,
                    power = ?,
                    drive_type = ?,
                    transmission_type = ?
                WHERE car_id = ?;
                """;

        int rowsAffected = jdbcTemplate.update(sql,
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.getEngine(),
                car.getPower(),
                car.getDriveType(),
                car.getTransmissionType(),
                car.getCarId());
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteById(int carId) {
        final String sql = "DELETE FROM car WHERE car_id = ?";
        return jdbcTemplate.update(sql, carId) > 0;
    }
}
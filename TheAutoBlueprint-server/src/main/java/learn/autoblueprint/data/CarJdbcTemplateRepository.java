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
        final String sql = "SELECT car_id, make, model, year, engine, power, drive_type, transmission_type FROM carId WHERE car_id = ?";
        return jdbcTemplate.queryForObject(sql, carMapper, id);
    }

    @Override
    public Car add(Car carId) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("carId")
                .usingGeneratedKeyColumns("car_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("make", carId.getMake());
        args.put("model", carId.getModel());
        args.put("year", carId.getYear());
        args.put("engine", carId.getEngine());
        args.put("power", carId.getPower());
        args.put("drive_type", carId.getDriveType());
        args.put("transmission_type", carId.getTransmissionType());
        carId.setCarId(insert.executeAndReturnKey(args).intValue());
        return carId;
    }

    @Override
    public boolean update(Car carId) {
        final String sql = """
                UPDATE carId SET
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
                carId.getMake(),
                carId.getModel(),
                carId.getYear(),
                carId.getEngine(),
                carId.getPower(),
                carId.getDriveType(),
                carId.getTransmissionType(),
                carId.getCarId());
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM carId WHERE car_id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
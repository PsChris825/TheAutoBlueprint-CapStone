package learn.autoblueprint.data.mappers;

import learn.autoblueprint.models.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        Car car = new Car();
        car.setCarId(rs.getInt("car_id"));
        car.setMake(rs.getString("make"));
        car.setModel(rs.getString("model"));
        car.setYear(rs.getInt("year"));
        car.setEngine(rs.getString("engine"));
        car.setPower(rs.getInt("power"));
        car.setDriveType(rs.getString("drive_type"));
        car.setTransmissionType(rs.getString("transmission_type"));
        return car;
    }
}

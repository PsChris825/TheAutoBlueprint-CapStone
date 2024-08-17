package learn.autoblueprint.data.mappers;

import learn.autoblueprint.models.Part;
import learn.autoblueprint.models.PartCategory;
import learn.autoblueprint.models.Car;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PartMapper implements RowMapper<Part> {

    @Override
    public Part mapRow(ResultSet rs, int rowNum) throws SQLException {
        Part part = new Part();
        part.setPartId(rs.getInt("part_id"));
        part.setPartName(rs.getString("part_name"));
        part.setPartNumber(rs.getString("part_number"));
        part.setManufacturer(rs.getString("manufacturer"));
        part.setOEMNumber(rs.getString("OEM_number"));
        part.setWeight(rs.getBigDecimal("weight"));
        part.setDetails(rs.getString("details"));

        PartCategory category = new PartCategory();
        category.setCategoryId(rs.getInt("category_id"));
        part.setCategory(category);

        Car car = new Car();
        car.setCarId(rs.getInt("car_id"));
        part.setCar(car);

        return part;
    }
}
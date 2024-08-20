package learn.autoblueprint.data.mappers;

import learn.autoblueprint.models.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelMapper {

    public Model mapRow(ResultSet rs) throws SQLException {
        return new Model(rs.getString("model"));
    }
}

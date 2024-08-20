package learn.autoblueprint.data.mappers;

import learn.autoblueprint.models.Make;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MakeMapper {

    public Make mapRow(ResultSet rs) throws SQLException {
        return new Make(rs.getString("make"));
    }
}

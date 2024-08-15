package learn.autoblueprint.data.mappers;

import learn.autoblueprint.models.PartCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PartCategoryMapper implements RowMapper<PartCategory> {

    @Override
    public PartCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        PartCategory partCategory = new PartCategory();
        partCategory.setCategoryId(rs.getInt("category_id"));
        partCategory.setCategoryName(rs.getString("category_name"));
        return partCategory;
    }
}

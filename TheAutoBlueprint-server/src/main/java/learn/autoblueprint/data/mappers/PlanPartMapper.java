package learn.autoblueprint.data.mappers;

import learn.autoblueprint.models.PlanPart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanPartMapper implements RowMapper<PlanPart> {

    @Override
    public PlanPart mapRow(ResultSet rs, int rowNum) throws SQLException {
        PlanPart planPart = new PlanPart();
        planPart.setPlanPartId(rs.getInt("plan_part_id"));
        planPart.setPartId(rs.getInt("part_id"));
        planPart.setPlanId(rs.getInt("plan_id"));
        planPart.setPrice(rs.getBigDecimal("price"));
        planPart.setTutorialUrl(rs.getString("tutorial_url"));
        planPart.setSupplierUrl(rs.getString("supplier_url"));
        planPart.setTimeToInstall(rs.getInt("time_to_install"));
        return planPart;
    }
}
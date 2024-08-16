package learn.autoblueprint.data.mappers;

import learn.autoblueprint.models.Part;
import learn.autoblueprint.models.PlanPart;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanPartMapper implements RowMapper<PlanPart> {

    private final PartMapper partMapper = new PartMapper();

    @Override
    public PlanPart mapRow(ResultSet rs, int rowNum) throws SQLException {
        PlanPart planPart = new PlanPart();
        planPart.setPlanPartId(rs.getInt("plan_part_id"));

        Part part = partMapper.mapRow(rs, rowNum);
        planPart.setPart(part);

        planPart.setPrice(rs.getBigDecimal("price"));
        planPart.setTutorialUrl(rs.getString("tutorial_url"));
        planPart.setSupplierUrl(rs.getString("supplier_url"));
        return planPart;
    }
}
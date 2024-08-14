package learn.autoblueprint.data.mappers;

import learn.autoblueprint.models.ModificationPlan;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModificationPlanMapper implements RowMapper<ModificationPlan> {

    @Override
    public ModificationPlan mapRow(ResultSet rs, int rowNum) throws SQLException {
        ModificationPlan plan = new ModificationPlan();
        plan.setPlanId(rs.getInt("plan_id"));
        plan.setUserId(rs.getInt("user_id"));
        plan.setCarId(rs.getInt("car_id"));
        plan.setPlanDescription(rs.getString("plan_description"));
        plan.setPlanHoursOfCompletion(rs.getInt("plan_hours_of_completion"));
        plan.setBudget(rs.getBigDecimal("budget"));
        plan.setTotalCost(rs.getBigDecimal("total_cost"));
        plan.setCreatedAt(rs.getTimestamp("created_at"));
        plan.setUpdatedAt(rs.getTimestamp("updated_at"));
        return plan;
    }
}


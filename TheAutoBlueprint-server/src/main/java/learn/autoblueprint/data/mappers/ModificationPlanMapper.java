package learn.autoblueprint.data.mappers;

import learn.autoblueprint.models.ModificationPlan;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModificationPlanMapper implements RowMapper<ModificationPlan> {

    @Override
    public ModificationPlan mapRow(ResultSet rs, int rowNum) throws SQLException {
        ModificationPlan modificationPlan = new ModificationPlan();
        modificationPlan.setPlanId(rs.getInt("plan_id"));
        modificationPlan.setAppUserId(rs.getInt("app_user_id"));
        modificationPlan.setCarId(rs.getInt("car_id"));
        modificationPlan.setPlanName(rs.getString("plan_name"));
        modificationPlan.setPlanDescription(rs.getString("plan_description"));
        modificationPlan.setPlanHoursOfCompletion(rs.getInt("plan_hours_of_completion"));
        modificationPlan.setBudget(rs.getBigDecimal("budget"));
        modificationPlan.setTotalCost(rs.getBigDecimal("total_cost"));
        modificationPlan.setCostVersusBudget(rs.getBigDecimal("cost_versus_budget"));
        modificationPlan.setCreatedAt(rs.getTimestamp("created_at"));
        modificationPlan.setUpdatedAt(rs.getTimestamp("updated_at"));
        return modificationPlan;
    }
}
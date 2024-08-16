package learn.autoblueprint.data.mappers;

import learn.autoblueprint.models.AppUser;
import learn.autoblueprint.models.Car;
import learn.autoblueprint.models.ModificationPlan;
import learn.autoblueprint.models.PlanPart;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModificationPlanMapper implements RowMapper<ModificationPlan> {

    private final CarMapper carMapper = new CarMapper();
    private final PlanPartMapper planPartMapper = new PlanPartMapper();

    @Override
    public ModificationPlan mapRow(ResultSet rs, int rowNum) throws SQLException {
        ModificationPlan plan = new ModificationPlan();
        plan.setPlanId(rs.getInt("plan_id"));

        int appUserId = rs.getInt("app_user_id");
        String username = rs.getString("username");
        String password = rs.getString("password_hash");
        boolean enabled = rs.getBoolean("enabled");

        // Fetch roles
        List<String> roles = new ArrayList<>();
        do {
            roles.add(rs.getString("role_name"));
        } while (rs.next() && rs.getInt("app_user_id") == appUserId);

        AppUser appUser = new AppUser(appUserId, username, password, enabled, roles);
        plan.setAppUserId(appUser);

        Car car = carMapper.mapRow(rs, rowNum);
        plan.setCarId(car);

        List<PlanPart> planParts = new ArrayList<>();
        PlanPart planPart = planPartMapper.mapRow(rs, rowNum);
        planParts.add(planPart);
        plan.setPlanParts(planParts);

        plan.setPlanName(rs.getString("plan_name"));
        plan.setPlanDescription(rs.getString("plan_description"));
        plan.setPlanHoursOfCompletion(rs.getInt("plan_hours_of_completion"));
        plan.setBudget(rs.getBigDecimal("budget"));
        plan.setTotalCost(rs.getBigDecimal("total_cost"));
        plan.setCostVersusBudget(rs.getBigDecimal("cost_versus_budget"));
        plan.setCreatedAt(rs.getTimestamp("created_at"));
        plan.setUpdatedAt(rs.getTimestamp("updated_at"));
        return plan;
    }
}
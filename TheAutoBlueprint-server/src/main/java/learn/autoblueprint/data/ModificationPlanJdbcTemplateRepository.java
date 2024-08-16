package learn.autoblueprint.data;

import learn.autoblueprint.data.mappers.AppUserMapper;
import learn.autoblueprint.data.mappers.CarMapper;
import learn.autoblueprint.data.mappers.ModificationPlanMapper;
import learn.autoblueprint.models.ModificationPlan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Repository
public class ModificationPlanJdbcTemplateRepository implements ModificationPlanRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ModificationPlanMapper modificationPlanMapper = new ModificationPlanMapper();
    private final AppUserMapper appUserMapper = new AppUserMapper(Arrays.asList("ROLE_USER"));
    private final CarMapper carMapper = new CarMapper();

    public ModificationPlanJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ModificationPlan> findAll() {
        final String sql = "select mp.plan_id, mp.car_id, mp.app_user_id, mp.plan_name, mp.plan_description, mp.total_cost, mp.plan_hours_of_completion, " +
                "mp.budget, mp.cost_versus_budget, mp.created_at, mp.updated_at, " +
                "c.car_id, c.make, c.model, c.year " +
                "from modification_plan mp " +
                "join car c on mp.car_id = c.car_id;";
        return jdbcTemplate.query(sql, modificationPlanMapper);
    }

    @Override
    public List<ModificationPlan> findByAppUserId(int userId) {
        final String sql = "select mp.plan_id, mp.car_id, mp.app_user_id, mp.plan_name, mp.plan_description, mp.total_cost, mp.plan_hours_of_completion, " +
                "mp.budget, mp.cost_versus_budget, mp.created_at, mp.updated_at, " +
                "c.car_id, c.make, c.model, c.year " +
                "from modification_plan mp " +
                "join car c on mp.car_id = c.car_id " +
                "where mp.app_user_id = ?;";
        return jdbcTemplate.query(sql, modificationPlanMapper, userId);
    }

    @Override
    public ModificationPlan findById(int modificationPlanId) {
        final String sql = "select mp.plan_id, mp.car_id, mp.app_user_id, mp.plan_name, mp.plan_description, mp.total_cost, mp.plan_hours_of_completion, " +
                "mp.budget, mp.cost_versus_budget, mp.created_at, mp.updated_at, " +
                "c.car_id, c.make, c.model, c.year " +
                "from modification_plan mp " +
                "join car c on mp.car_id = c.car_id " +
                "where mp.plan_id = ?;";
        return jdbcTemplate.query(sql, modificationPlanMapper, modificationPlanId).stream().findFirst().orElse(null);
    }

    @Override
    public ModificationPlan add(ModificationPlan modificationPlan) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("modification_plan")
                .usingGeneratedKeyColumns("plan_id");

        HashMap<String, Object> values = new HashMap<>();
        values.put("car_id", modificationPlan.getCarId().getCarId());
        values.put("app_user_id", modificationPlan.getAppUserId().getAppUserId());
        values.put("plan_name", modificationPlan.getPlanName());
        values.put("plan_description", modificationPlan.getPlanDescription());
        values.put("total_cost", modificationPlan.getTotalCost());
        values.put("plan_hours_of_completion", modificationPlan.getPlanHoursOfCompletion());
        values.put("budget", modificationPlan.getBudget());
        values.put("cost_versus_budget", modificationPlan.getCostVersusBudget());

        Number newId = insert.executeAndReturnKey(values);
        modificationPlan.setPlanId(newId.intValue());
        return modificationPlan;
    }

    @Override
    public boolean update(ModificationPlan modificationPlan) {
        final String sql = "update modification_plan set " +
                "car_id = ?, " +
                "app_user_id = ?, " +
                "plan_name = ?, " +
                "plan_description = ?, " +
                "total_cost = ?, " +
                "plan_hours_of_completion = ?, " +
                "budget = ?, " +
                "cost_versus_budget = ? " +
                "where plan_id = ?;";
        int rowsAffected = jdbcTemplate.update(sql,
                modificationPlan.getCarId().getCarId(),
                modificationPlan.getAppUserId().getAppUserId(),
                modificationPlan.getPlanName(),
                modificationPlan.getPlanDescription(),
                modificationPlan.getTotalCost(),
                modificationPlan.getPlanHoursOfCompletion(),
                modificationPlan.getBudget(),
                modificationPlan.getCostVersusBudget(),
                modificationPlan.getPlanId());
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteById(int modificationPlanId) {
        return jdbcTemplate.update("delete from modification_plan where plan_id = ?;", modificationPlanId) > 0;
    }
}
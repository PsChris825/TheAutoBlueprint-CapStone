package learn.autoblueprint.data;

import learn.autoblueprint.data.mappers.ModificationPlanMapper;
import learn.autoblueprint.data.mappers.PlanPartMapper;
import learn.autoblueprint.models.ModificationPlan;
import learn.autoblueprint.models.PlanPart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ModificationPlanJdbcTemplateRepository implements ModificationPlanRepository {

    private final JdbcTemplate jdbcTemplate;

    public ModificationPlanJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public List<ModificationPlan> findAll() {
        final String sql = """
        SELECT mp.plan_id, mp.app_user_id, mp.car_id, mp.plan_name, mp.plan_description,
               mp.plan_hours_of_completion, mp.budget, mp.total_cost, mp.cost_versus_budget,
               mp.created_at, mp.updated_at, pp.plan_part_id, pp.part_id, pp.price, pp.time_to_install,
               pp.tutorial_url, pp.supplier_url, p.part_name, p.part_number, p.manufacturer,
               p.OEM_number, p.weight, p.details, p.category_id, p.car_id
        FROM modification_plan mp
        LEFT JOIN plan_part pp ON mp.plan_id = pp.plan_id
        LEFT JOIN part p ON pp.part_id = p.part_id
    """;

        List<ModificationPlan> plans = jdbcTemplate.query(sql, rs -> {
            Map<Integer, ModificationPlan> planMap = new HashMap<>();

            while (rs.next()) {
                int planId = rs.getInt("plan_id");
                ModificationPlan plan = planMap.get(planId);

                if (plan == null) {
                    plan = new ModificationPlan();
                    plan.setPlanId(planId);
                    plan.setAppUserId(rs.getInt("app_user_id"));
                    plan.setCarId(rs.getInt("car_id"));
                    plan.setPlanName(rs.getString("plan_name"));
                    plan.setPlanDescription(rs.getString("plan_description"));
                    plan.setPlanHoursOfCompletion(rs.getInt("plan_hours_of_completion"));
                    plan.setBudget(rs.getBigDecimal("budget"));
                    plan.setTotalCost(rs.getBigDecimal("total_cost"));
                    plan.setCostVersusBudget(rs.getBigDecimal("cost_versus_budget"));
                    plan.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
                    plan.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
                    plan.setPlanParts(new ArrayList<>());
                    planMap.put(planId, plan);
                }

                int planPartId = rs.getInt("plan_part_id");
                if (planPartId > 0) {
                    PlanPart planPart = new PlanPart();
                    planPart.setPlanPartId(planPartId);
                    planPart.setPartId(rs.getInt("part_id"));
                    planPart.setPlanId(planId);
                    planPart.setPrice(rs.getBigDecimal("price"));
                    planPart.setTimeToInstall(rs.getInt("time_to_install"));
                    planPart.setTutorialUrl(rs.getString("tutorial_url"));
                    planPart.setSupplierUrl(rs.getString("supplier_url"));

                    planMap.get(planId).getPlanParts().add(planPart);
                }
            }

            return new ArrayList<>(planMap.values());
        });

        for (ModificationPlan plan : plans) {
            plan.calculateTotalCost();
            plan.calculatePlanHours();
        }
        return plans;
    }

    @Override
    public List<PlanPart> findPlanPartsByModificationPlanId(int planId) {
        final String sql = "SELECT plan_part_id, plan_id, part_id, price, time_to_install FROM plan_part WHERE plan_id = ?";
        return jdbcTemplate.query(sql, new PlanPartMapper(), planId);
    }

    @Override
    public List<ModificationPlan> findByAppUserId(int appUserId) {
        final String sql = "SELECT * FROM modification_plan WHERE app_user_id = ?";
        return jdbcTemplate.query(sql, new ModificationPlanMapper(), appUserId);
    }

    @Override
    public ModificationPlan findById(int planId) {
        final String sql = "SELECT * FROM modification_plan WHERE plan_id = ?";
        List<ModificationPlan> plans = jdbcTemplate.query(sql, new ModificationPlanMapper(), planId);
        return plans.isEmpty() ? null : plans.get(0);
    }

    @Override
    public ModificationPlan add(ModificationPlan modificationPlan) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("modification_plan")
                .usingGeneratedKeyColumns("plan_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("app_user_id", modificationPlan.getAppUserId());
        parameters.put("car_id", modificationPlan.getCarId());
        parameters.put("plan_name", modificationPlan.getPlanName());
        parameters.put("plan_description", modificationPlan.getPlanDescription());
        parameters.put("plan_hours_of_completion", modificationPlan.getPlanHoursOfCompletion());
        parameters.put("budget", modificationPlan.getBudget());
        parameters.put("total_cost", modificationPlan.getTotalCost());
        parameters.put("cost_versus_budget", modificationPlan.getCostVersusBudget());
        parameters.put("created_at", modificationPlan.getCreatedAt());
        parameters.put("updated_at", modificationPlan.getUpdatedAt());

        Number newId = insert.executeAndReturnKey(parameters);
        modificationPlan.setPlanId(newId.intValue());
        return modificationPlan;
    }

    @Override
    public boolean update(ModificationPlan modificationPlan) {
        final String sql = "UPDATE modification_plan SET "
                + "app_user_id = ?, "
                + "car_id = ?, "
                + "plan_name = ?, "
                + "plan_description = ?, "
                + "plan_hours_of_completion = ?, "
                + "budget = ?, "
                + "total_cost = ?, "
                + "cost_versus_budget = ?, "
                + "created_at = ?, "
                + "updated_at = ? "
                + "WHERE plan_id = ?";

        return jdbcTemplate.update(sql,
                modificationPlan.getAppUserId(),
                modificationPlan.getCarId(),
                modificationPlan.getPlanName(),
                modificationPlan.getPlanDescription(),
                modificationPlan.getPlanHoursOfCompletion(),
                modificationPlan.getBudget(),
                modificationPlan.getTotalCost(),
                modificationPlan.getCostVersusBudget(),
                modificationPlan.getCreatedAt(),
                modificationPlan.getUpdatedAt(),
                modificationPlan.getPlanId()) > 0;
    }

    @Override
    public boolean deleteById(int planId) {
        final String sql = "DELETE FROM modification_plan WHERE plan_id = ?";
        return jdbcTemplate.update(sql, planId) > 0;
    }
}
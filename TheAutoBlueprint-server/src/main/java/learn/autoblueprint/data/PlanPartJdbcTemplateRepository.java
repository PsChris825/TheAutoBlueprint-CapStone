package learn.autoblueprint.data;

import learn.autoblueprint.data.mappers.PlanPartMapper;
import learn.autoblueprint.models.PlanPart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlanPartJdbcTemplateRepository implements PlanPartRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PlanPartMapper planPartMapper = new PlanPartMapper();

    public PlanPartJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PlanPart> findAll() {
        final String sql = """
                SELECT pp.*, p.part_name, p.part_number, p.manufacturer, p.OEM_number, p.weight, p.details,
                       pc.category_id, pc.category_name, c.car_id, c.make, c.model, c.year, c.engine, c.power, c.drive_type, c.transmission_type
                FROM plan_part pp
                JOIN part p ON pp.part_id = p.part_id
                JOIN part_category pc ON p.category_id = pc.category_id
                JOIN car c ON p.car_id = c.car_id
                """;
        return jdbcTemplate.query(sql, new PlanPartMapper());
    }

    @Override
    public PlanPart findById(int planPartId) {
        final String sql = """
                SELECT pp.*, p.part_name, p.part_number, p.manufacturer, p.OEM_number, p.weight, p.details,
                       pc.category_id, pc.category_name, c.car_id, c.make, c.model, c.year, c.engine, c.power, c.drive_type, c.transmission_type
                FROM plan_part pp
                JOIN part p ON pp.part_id = p.part_id
                JOIN part_category pc ON p.category_id = pc.category_id
                JOIN car c ON p.car_id = c.car_id
                WHERE pp.plan_part_id = ?
                """;
        List<PlanPart> planParts = jdbcTemplate.query(sql, new PlanPartMapper(), planPartId);
        return planParts.isEmpty() ? null : planParts.get(0);
    }

    @Override
    public List<PlanPart> findByPlanId(int planId) {
        final String sql = """
                SELECT pp.*, p.part_name, p.part_number, p.manufacturer, p.OEM_number, p.weight, p.details,
                       pc.category_id, pc.category_name, c.car_id, c.make, c.model, c.year, c.engine, c.power, c.drive_type, c.transmission_type
                FROM plan_part pp
                JOIN part p ON pp.part_id = p.part_id
                JOIN part_category pc ON p.category_id = pc.category_id
                JOIN car c ON p.car_id = c.car_id
                WHERE pp.plan_id = ?
                """;
        return jdbcTemplate.query(sql, new PlanPartMapper(), planId);
    }

    @Override
    public PlanPart add(PlanPart planPart) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("plan_part")
                .usingGeneratedKeyColumns("plan_part_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("part_id", planPart.getPart().getPartId());
        args.put("plan_id", planPart.getPlan().getPlanId());
        args.put("price", planPart.getPrice());
        args.put("tutorial_url", planPart.getTutorialUrl());
        args.put("supplier_url", planPart.getSupplierUrl());
        args.put("time_to_install", planPart.getTimeToInstall());

        planPart.setPlanPartId(insert.executeAndReturnKey(args).intValue());
        return planPart;
    }

    @Override
    public boolean update(PlanPart planPart) {
        final String sql = """
                UPDATE plan_part SET
                    part_id = ?,
                    plan_id = ?,
                    price = ?,
                    tutorial_url = ?,
                    supplier_url = ?,
                    time_to_install = ?
                WHERE plan_part_id = ?
                """;
        int rowsAffected = jdbcTemplate.update(sql,
                planPart.getPart().getPartId(),
                planPart.getPlan().getPlanId(),
                planPart.getPrice(),
                planPart.getTutorialUrl(),
                planPart.getSupplierUrl(),
                planPart.getTimeToInstall(),
                planPart.getPlanPartId());
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteById(int planPartId) {
        final String sql = "DELETE FROM plan_part WHERE plan_part_id = ?";
        return jdbcTemplate.update(sql, planPartId) > 0;
    }
}
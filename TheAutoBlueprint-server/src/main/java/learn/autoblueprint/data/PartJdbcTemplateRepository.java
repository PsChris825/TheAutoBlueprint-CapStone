package learn.autoblueprint.data;

import learn.autoblueprint.data.mappers.PartMapper;
import learn.autoblueprint.models.Part;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class PartJdbcTemplateRepository implements PartRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PartMapper partMapper = new PartMapper();

    public PartJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Part> findAll() {
        final String sql = """
                select
                    p.part_id,
                    p.part_name,
                    p.part_number,
                    p.manufacturer,
                    p.OEM_number,
                    p.weight,
                    p.details,
                    p.category_id,
                    p.car_id
                from part p
                order by p.part_number;
                """;

        return jdbcTemplate.query(sql, partMapper);
    }

    @Override
    public List<Part> findByCarId(int carId) {
        final String sql = """
                select
                    p.part_id,
                    p.part_name,
                    p.part_number,
                    p.manufacturer,
                    p.OEM_number,
                    p.weight,
                    p.details,
                    p.category_id,
                    p.car_id
                from part p
                where p.car_id = ?
                order by p.part_number;
                """;

        return jdbcTemplate.query(sql, partMapper, carId);
    }

    @Override
    public Part findById(int partId) {
        final String sql = """
                select
                    p.part_id,
                    p.part_name,
                    p.part_number,
                    p.manufacturer,
                    p.OEM_number,
                    p.weight,
                    p.details,
                    p.category_id,
                    p.car_id
                from part p
                where p.part_id = ?;
                """;

        return jdbcTemplate.query(sql, partMapper, partId).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Part add(Part part) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("part")
                .usingGeneratedKeyColumns("part_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("part_name", part.getPartName());
        args.put("part_number", part.getPartNumber());
        args.put("manufacturer", part.getManufacturer());
        args.put("OEM_number", part.getOEMNumber());
        args.put("weight", part.getWeight());
        args.put("details", part.getDetails());
        args.put("category_id", part.getCategoryId());
        args.put("car_id", part.getCarId());

        // Debugging logs
        System.out.println("Inserting part with args: " + args);

        Number key = insert.executeAndReturnKey(args);
        part.setPartId(key.intValue());
        return part;
    }

    @Override
    public boolean update(Part part) {
        final String sql = """
            update part set
                part_name = ?,
                part_number = ?,
                manufacturer = ?,
                OEM_number = ?,
                weight = ?,
                details = ?,
                category_id = ?,
                car_id = ?
            where part_id = ?;
            """;

        int rowsAffected = jdbcTemplate.update(sql,
                part.getPartName(),
                part.getPartNumber(),
                part.getManufacturer(),
                part.getOEMNumber(),
                part.getWeight(),
                part.getDetails(),
                part.getCategoryId(),
                part.getCarId(),
                part.getPartId());
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteById(int partId) {
        return jdbcTemplate.update("delete from part where part_id = ?", partId) > 0;
    }

    @Override
    public List<Part> findByCategoryId(int categoryId) {
        final String sql = """
                select
                    p.part_id,
                    p.part_name,
                    p.part_number,
                    p.manufacturer,
                    p.OEM_number,
                    p.weight,
                    p.details,
                    p.category_id,
                    p.car_id
                from part p
                where p.category_id = ?
                order by p.part_number;
                """;

        return jdbcTemplate.query(sql, partMapper, categoryId);
    }

}
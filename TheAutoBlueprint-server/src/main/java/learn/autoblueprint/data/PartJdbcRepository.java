package learn.autoblueprint.data;

import learn.autoblueprint.data.mappers.PartCategoryMapper;
import learn.autoblueprint.data.mappers.PartMapper;
import learn.autoblueprint.models.Part;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PartJdbcRepository implements PartRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PartMapper partMapper = new PartMapper();
    private final PartCategoryMapper partCategoryMapper = new PartCategoryMapper();

    public PartJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Part> findAll() {
        final String sql = """
                select
                    p.part_id,
                    p.part_number,
                    p.name,
                    p.description,
                    p.list_price,
                    p.category_id,
                    c.name as category_name
                from part p
                join category c on c.category_id = p.category_id
                order by p.part_number;
                """;

        return jdbcTemplate.query(sql, partMapper);
    }

}

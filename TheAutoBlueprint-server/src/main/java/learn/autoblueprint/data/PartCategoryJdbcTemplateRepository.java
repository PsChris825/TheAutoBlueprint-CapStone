package learn.autoblueprint.data;

import learn.autoblueprint.data.mappers.PartCategoryMapper;
import learn.autoblueprint.models.PartCategory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PartCategoryJdbcTemplateRepository implements PartCategoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PartCategoryMapper partCategoryMapper = new PartCategoryMapper();

    public PartCategoryJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PartCategory> findAll() {
        final String sql = """
                select
                    category_id,
                    category_name
                from part_category
                order by category_name;
                """;

        return jdbcTemplate.query(sql, partCategoryMapper);
    }

    @Override
    public PartCategory findById(int partCategoryId) {
        final String sql = """
                select
                    category_id,
                    category_name
                from part_category
                where category_id = ?;
                """;

        return jdbcTemplate.query(sql, partCategoryMapper, partCategoryId)
                .stream().findFirst().orElse(null);
    }

    @Override
    public PartCategory add(PartCategory partCategory) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("part_category")
                .usingGeneratedKeyColumns("category_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("category_name", partCategory.getCategoryName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        partCategory.setCategoryId(key.intValue());
        return partCategory;
    }

    @Override
    public boolean update(PartCategory partCategory) {
        final String sql = """
                update part_category set
                    category_name = ?
                where category_id = ?;
                """;

        int rowsAffected = jdbcTemplate.update(sql,
                partCategory.getCategoryName(),
                partCategory.getCategoryId());
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteById(int partCategoryId) {
        return jdbcTemplate.update("delete from part_category where category_id = ?", partCategoryId) > 0;
    }

    @Override
    public boolean existsByName(String categoryName) {
        final String sql = "SELECT COUNT(*) FROM part_category WHERE category_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, categoryName);
        return count != null && count > 0;
    }

}

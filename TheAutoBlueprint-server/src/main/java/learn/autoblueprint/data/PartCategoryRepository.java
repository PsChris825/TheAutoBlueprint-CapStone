package learn.autoblueprint.data;

import learn.autoblueprint.models.PartCategory;

import java.util.List;

public interface PartCategoryRepository {
    List<PartCategory> findAll();

    PartCategory findById(int partCategoryId);

    PartCategory add(PartCategory partCategory);

    boolean update(PartCategory partCategory);

    boolean deleteById(int categoryId);
}

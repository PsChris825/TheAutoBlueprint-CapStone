package learn.autoblueprint.domain;

import learn.autoblueprint.data.PartCategoryRepository;
import learn.autoblueprint.models.PartCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartCategoryService {

    private final PartCategoryRepository repository;

    public PartCategoryService(PartCategoryRepository repository) {
        this.repository = repository;
    }

    public List<PartCategory> findAll() {
        return repository.findAll();
    }

    public PartCategory findById(int partCategoryId) {
        return repository.findById(partCategoryId);
    }

    public Result<PartCategory> add(PartCategory partCategory) {
        Result<PartCategory> result = validate(partCategory);

        if (!result.isSuccess()) {
            return result;
        }

        if (partCategory.getCategoryId() != null) {
            result.addMessage("Part Category ID must not be set.");
            return result;
        }

        partCategory = repository.add(partCategory);
        result.setPayload(partCategory);
        return result;
    }

    public Result<PartCategory> update(PartCategory partCategory) {
        Result<PartCategory> result = validate(partCategory);

        if (!result.isSuccess()) {
            return result;
        }

        if (partCategory.getCategoryId() == null || partCategory.getCategoryId() <= 0) {
            result.addMessage("Part Category ID must be set.");
            return result;
        }

        if (!repository.update(partCategory)) {
            String msg = String.format("Part Category ID: %s, not found.", partCategory.getCategoryId());
            result.addMessage(msg);
        } else {
            result.setPayload(partCategory);
        }

        return result;
    }

    public boolean deleteById(int partCategoryId) {
        return repository.deleteById(partCategoryId);
    }

    private Result<PartCategory> validate(PartCategory partCategory) {
        Result<PartCategory> result = new Result<>();

        if (partCategory == null) {
            result.addMessage("Part Category cannot be null.");
            return result;
        }

        if (partCategory.getCategoryName() == null || partCategory.getCategoryName().isBlank()) {
            result.addMessage("Part Category name is required.");
        } else if (repository.existsByName(partCategory.getCategoryName())) {
            result.addMessage("Part Category name already exists.");
        }

        return result;
    }
}

package learn.autoblueprint.domain;

import learn.autoblueprint.data.PartRepository;
import learn.autoblueprint.models.Part;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartService {

    private final PartRepository partRepository;

    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    public List<Part> findAll() {
        return partRepository.findAll();
    }

    public List<Part> findByCarId(int carId) {
        return partRepository.findByCarId(carId);
    }

    public Part findById(int partId) {
        return partRepository.findById(partId);
    }

    public Result<Part> add(Part part) {
        Result<Part> result = validate(part);
        if (!result.isSuccess()) {
            return result;
        }

        if (part.getPartId() != null) {
            result.addMessage("New part must not have id set.", ResultType.INVALID);
            return result;
        }

        part = partRepository.add(part);
        result.setPayload(part);
        return result;
    }

    public Result<Part> update(Part part) {
        Result<Part> result = validate(part);
        if (!result.isSuccess()) {
            return result;
        }

        if (part.getPartId() == null) {
            result.addMessage("Existing part must have id set.", ResultType.INVALID);
            return result;
        }

        if (!partRepository.update(part)) {
            result.addMessage("Part ID " + part.getPartId() + " not found", ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int partId) {
        return partRepository.deleteById(partId);
    }

    public Result<Part> validate(Part part) {
        Result<Part> result = new Result<>();

        if (part == null) {
            result.addMessage("Part cannot be null.");
            return result;
        }

        if (part.getPartName() == null || part.getPartName().isBlank()) {
            result.addMessage("Part name is required.");
        }

        if (part.getPartNumber() == null || part.getPartNumber().isBlank()) {
            result.addMessage("Part number is required.");
        }

        if (part.getCar() == null || part.getCar().getCarId() == null || part.getCar().getCarId() <= 0) {
            result.addMessage("Car is required.");
        }

        return result;
    }
}
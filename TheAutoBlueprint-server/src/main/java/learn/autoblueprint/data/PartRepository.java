package learn.autoblueprint.data;

import learn.autoblueprint.models.Part;

import java.util.List;

public interface PartRepository {
    List<Part> findAll();
}

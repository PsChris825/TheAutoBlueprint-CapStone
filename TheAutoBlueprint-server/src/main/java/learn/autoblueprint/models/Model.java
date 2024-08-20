package learn.autoblueprint.models;

public class Model {
    private String model;

    public Model() {
    }

    public Model(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Model model1 = (Model) o;

        return model != null ? model.equals(model1.model) : model1.model == null;
    }

    @Override
    public int hashCode() {
        return model != null ? model.hashCode() : 0;
    }
}

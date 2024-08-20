package learn.autoblueprint.models;

public class Make {
    private String make;

    public Make() {
    }

    public Make(String make) {
        this.make = make;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Override
    public String toString() {
        return make;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Make make1 = (Make) o;

        return make != null ? make.equals(make1.make) : make1.make == null;
    }

    @Override
    public int hashCode() {
        return make != null ? make.hashCode() : 0;
    }
}

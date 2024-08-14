package learn.autoblueprint.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Car {

    private Integer carId;
    private String make;
    private String model;
    private Integer year;
    private String engine;
    private Integer power;
    private String driveType;
    private String transmissionType;

    public Car() {}

    public Car(Integer carId, String make, String model, Integer year, String engine, Integer power, String driveType, String transmissionType) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.engine = engine;
        this.power = power;
        this.driveType = driveType;
        this.transmissionType = transmissionType;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(carId, car.carId) && Objects.equals(make, car.make) && Objects.equals(model, car.model) && Objects.equals(year, car.year) && Objects.equals(engine, car.engine) && Objects.equals(power, car.power) && Objects.equals(driveType, car.driveType) && Objects.equals(transmissionType, car.transmissionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, make, model, year, engine, power, driveType, transmissionType);
    }
}



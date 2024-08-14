package learn.autoblueprint;

import learn.autoblueprint.models.Car;

public class CarBuilder {
    private Integer carId;
    private String make;
    private String model;
    private Integer year;
    private String engine;
    private Integer power;
    private String driveType;
    private String transmissionType;

    public CarBuilder setCarId(Integer carId) {
        this.carId = carId;
        return this;
    }

    public CarBuilder setMake(String make) {
        this.make = make;
        return this;
    }

    public CarBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    public CarBuilder setYear(Integer year) {
        this.year = year;
        return this;
    }

    public CarBuilder setEngine(String engine) {
        this.engine = engine;
        return this;
    }

    public CarBuilder setPower(Integer power) {
        this.power = power;
        return this;
    }

    public CarBuilder setDriveType(String driveType) {
        this.driveType = driveType;
        return this;
    }

    public CarBuilder setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
        return this;
    }

    public Car build() {
        return new Car(carId, make, model, year, engine, power, driveType, transmissionType);
    }
}

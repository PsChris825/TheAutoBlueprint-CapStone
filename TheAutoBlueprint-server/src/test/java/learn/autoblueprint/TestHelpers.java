package learn.autoblueprint;

import learn.autoblueprint.models.Car;

public class TestHelpers {

    public static Car createValidCar() {
        Car car = new Car();
        car.setCarId(1);
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setYear(2020);
        car.setEngine("1.8L");
        car.setPower(139);
        car.setDriveType("FWD");
        car.setTransmissionType("Automatic");
        return car;
    }
}

package learn.autoblueprint;

import learn.autoblueprint.models.Car;


public class TestHelpers {

    public static Car createCarWithMissingRequiredFields() {
        return new CarBuilder()
                .setEngine("1.8L I4")
                .setDriveType("FWD")
                .setTransmissionType("CVT")
                .build();
    }

    public static Car createValidCar() {
        return new CarBuilder()
                .setMake("Toyota")
                .setModel("Corolla")
                .setYear(2022)
                .setEngine("1.8L I4")
                .setPower(139)
                .setDriveType("FWD")
                .setTransmissionType("CVT")
                .build();
    }

    public static Car createCarWithInvalidYear() {
        return new CarBuilder()
                .setMake("Toyota")
                .setModel("Corolla")
                .setYear(1899)
                .setEngine("1.8L I4")
                .setPower(139)
                .setDriveType("FWD")
                .setTransmissionType("CVT")
                .build();
    }

    public static Car createCarWithValidYear() {
        return new CarBuilder()
                .setMake("Toyota")
                .setModel("Corolla")
                .setYear(2024)
                .setEngine("1.8L I4")
                .setPower(139)
                .setDriveType("FWD")
                .setTransmissionType("CVT")
                .build();
    }
}

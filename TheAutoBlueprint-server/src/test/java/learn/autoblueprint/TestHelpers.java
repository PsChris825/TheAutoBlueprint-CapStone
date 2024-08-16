package learn.autoblueprint;

import learn.autoblueprint.models.Car;
import learn.autoblueprint.models.PartCategory;

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

    public static Car makeNewCar() {
        Car car = new Car();
        car.setMake("New Make");
        car.setModel("New Model");
        car.setYear(2022);
        car.setEngine("2.0L");
        car.setPower(150);
        car.setDriveType("AWD");
        car.setTransmissionType("Automatic");
        return car;
    }

    public static Car makeCar(int id) {
        Car car = new Car();
        car.setCarId(id);
        car.setMake("Make " + id);
        car.setModel("Model " + id);
        car.setYear(2020 + id);
        car.setEngine("Engine " + id);
        car.setPower(100 + id);
        car.setDriveType("DriveType " + id);
        car.setTransmissionType("TransmissionType " + id);
        return car;
    }

    public static Car makeExistingCar() {
        return makeCar(3);
    }

    public static PartCategory makePartCategory(int id, String name) {
        PartCategory partCategory = new PartCategory();
        partCategory.setCategoryId(id);
        partCategory.setCategoryName(name);
        return partCategory;
    }

    public static PartCategory makePartCategory(int id) {
        PartCategory partCategory = new PartCategory();
        partCategory.setCategoryId(id);
        partCategory.setCategoryName("Category " + id);
        return partCategory;
    }

    public static PartCategory makeExistingPartCategory() {
        return makePartCategory(1, "Engine");
    }
}

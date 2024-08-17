package learn.autoblueprint;

import learn.autoblueprint.models.AppUser;
import learn.autoblueprint.models.Car;
import learn.autoblueprint.models.ModificationPlan;
import learn.autoblueprint.models.PartCategory;
import learn.autoblueprint.models.PlanPart;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;

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

    public static AppUser createValidAppUser() {
        return new AppUser(1, "testuser", "password", true, Collections.singletonList("ROLE_USER"));
    }

    public static ModificationPlan createValidModificationPlan() {
        ModificationPlan plan = new ModificationPlan();
        plan.setPlanId(1);
        plan.setAppUserId(1);
        plan.setCarId(1);
        plan.setPlanName("Test Plan");
        plan.setPlanDescription("Test Description");
        plan.setPlanHoursOfCompletion(5);
        plan.setBudget(BigDecimal.valueOf(1000));
        plan.setTotalCost(BigDecimal.valueOf(800));
        plan.setCostVersusBudget(BigDecimal.valueOf(200));
        plan.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        plan.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        PlanPart part = new PlanPart();
        part.setPrice(BigDecimal.valueOf(800));
        plan.setPlanParts(Collections.singletonList(part));
        return plan;
    }

        public static ModificationPlan makeNewModificationPlan() {
        ModificationPlan plan = new ModificationPlan();
        plan.setAppUserId(1);
        plan.setCarId(2);
        plan.setPlanName("New Plan");
        plan.setPlanDescription("New Description");
        plan.setPlanHoursOfCompletion(10);
        plan.setBudget(BigDecimal.valueOf(2000));
        plan.setTotalCost(BigDecimal.valueOf(1500));
        plan.setCostVersusBudget(BigDecimal.valueOf(500));
        plan.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        plan.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        plan.setPlanParts(Collections.singletonList(new PlanPart()));
        return plan;
    }

    public static ModificationPlan makeExistingModificationPlan() {
        return createValidModificationPlan();
    }
}
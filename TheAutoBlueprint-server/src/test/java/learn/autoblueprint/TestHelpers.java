package learn.autoblueprint;

import learn.autoblueprint.models.*;

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
        car.setCarId(2);
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
        plan.setPlanId(1); // Ensure planId is set
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

    public static Part createValidPart() {
        Part part = new Part();
        part.setPartId(1);
        part.setPartName("Valid Part");
        part.setPartNumber("VP123");
        part.setManufacturer("Valid Manufacturer");
        part.setOEMNumber("OEM123");
        part.setWeight(new BigDecimal("10.00"));
        part.setDetails("Valid Details");
        part.setCategory(makeExistingPartCategory());
        part.setCar(createValidCar());
        return part;
    }

    public static Part makeNewPart() {
        Part part = new Part();
        part.setPartId(null); // Ensure PartId is null for new parts
        part.setPartName("New Part");
        part.setPartNumber("NP123");
        part.setManufacturer("New Manufacturer");
        part.setOEMNumber("OEM123");
        part.setWeight(new BigDecimal("10.00"));
        part.setDetails("New Details");
        part.setCategory(makePartCategory(2, "New Category"));
        part.setCar(makeNewCar());
        return part;
    }

    public static Part makePart(int id) {
        Part part = new Part();
        part.setPartId(id);
        part.setPartName("Part " + id);
        part.setPartNumber("PN" + id);
        part.setManufacturer("Manufacturer " + id);
        part.setOEMNumber("OEM" + id);
        part.setWeight(new BigDecimal("10.00"));
        part.setDetails("Details " + id);
        part.setCategory(makePartCategory(id, "Category " + id));
        part.setCar(makeCar(id));
        return part;
    }

    public static Part makeExistingPart() {
        return makePart(3);
    }

    public static PlanPart createValidPlanPart() {
        PlanPart planPart = new PlanPart();
        planPart.setPlanPartId(1);
        planPart.setPart(createValidPart());
        planPart.setPlan(createValidModificationPlan());
        planPart.setPrice(BigDecimal.valueOf(100.00));
        planPart.setTutorialUrl("http://example.com/tutorial");
        planPart.setSupplierUrl("http://example.com/supplier");
        planPart.setTimeToInstall(120);
        return planPart;
    }

    public static PlanPart makeNewPlanPart() {
        PlanPart planPart = new PlanPart();
        planPart.setPlanPartId(1);
        planPart.setPart(makeNewPartWithId());
        planPart.setPlan(makeNewModificationPlan());
        planPart.setPrice(BigDecimal.valueOf(200.00));
        planPart.setTutorialUrl("http://example.com/new_tutorial");
        planPart.setSupplierUrl("http://example.com/new_supplier");
        planPart.setTimeToInstall(150);
        return planPart;
    }

    public static Part makeNewPartWithId() {
        Part part = new Part();
        part.setPartId(1);
        part.setPartName("New Part");
        part.setPartNumber("NP123");
        part.setManufacturer("New Manufacturer");
        part.setOEMNumber("OEM123");
        part.setWeight(new BigDecimal("10.00"));
        part.setDetails("New Details");
        part.setCategory(makePartCategory(2, "New Category"));
        part.setCar(makeNewCar());
        return part;
    }

    public static PlanPart makePlanPart(int id) {
        PlanPart planPart = new PlanPart();
        planPart.setPlanPartId(id);
        planPart.setPart(makePart(id));
        planPart.setPlan(makeModificationPlan(id));
        planPart.setPrice(BigDecimal.valueOf(100.00 + id));
        planPart.setTutorialUrl("http://example.com/tutorial" + id);
        planPart.setSupplierUrl("http://example.com/supplier" + id);
        planPart.setTimeToInstall(120 + id);
        return planPart;
    }

    private static ModificationPlan makeModificationPlan(int id) {
        ModificationPlan plan = new ModificationPlan();
        plan.setPlanId(id);
        plan.setAppUserId(1);
        plan.setCarId(id);
        plan.setPlanName("Plan " + id);
        plan.setPlanDescription("Description " + id);
        plan.setPlanHoursOfCompletion(5 + id);
        plan.setBudget(BigDecimal.valueOf(1000 + id * 100));
        plan.setTotalCost(BigDecimal.valueOf(800 + id * 100));
        plan.setCostVersusBudget(BigDecimal.valueOf(200 + id * 10));
        plan.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        plan.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        plan.setPlanParts(Collections.singletonList(new PlanPart()));
        return plan;
    }

    public static PlanPart makeExistingPlanPart() {
        return makePlanPart(3);
    }

    public static Comment createValidComment() {
        Comment comment = new Comment();
        comment.setCommentId(1);
        comment.setPostId(1);
        comment.setUserId(1);
        comment.setCommentText("This is a valid comment.");
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return comment;
    }

    public static Comment makeNewComment() {
        Comment comment = new Comment();
        comment.setCommentId(2);
        comment.setPostId(2);
        comment.setUserId(2);
        comment.setCommentText("This is a new comment.");
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return comment;
    }

    public static Comment makeComment(int id) {
        Comment comment = new Comment();
        comment.setCommentId(id);
        comment.setPostId(id);
        comment.setUserId(id);
        comment.setCommentText("Comment " + id);
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return comment;
    }

    public static Comment makeExistingComment() {
        return makeComment(3);
    }

    public static Post createValidPost() {
        Post post = new Post();
        post.setPostId(1);
        post.setUserId(1);
        post.setTitle("Valid Post Title");
        post.setPostDescription("This is a valid post description.");
        post.setImageUrl("http://example.com/image.jpg");
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return post;
    }

    public static Post makeNewPost() {
        Post post = new Post();
        post.setPostId(null); // Ensure PostId is null for new posts
        post.setUserId(2);
        post.setTitle("New Post Title");
        post.setPostDescription("This is a new post description.");
        post.setImageUrl("http://example.com/new_image.jpg");
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return post;
    }

    public static Post makePost(int id) {
        Post post = new Post();
        post.setPostId(id);
        post.setUserId(id);
        post.setTitle("Post Title " + id);
        post.setPostDescription("Post Description " + id);
        post.setImageUrl("http://example.com/image" + id + ".jpg");
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return post;
    }

    public static Post makeExistingPost() {
        return makePost(3);
    }
}
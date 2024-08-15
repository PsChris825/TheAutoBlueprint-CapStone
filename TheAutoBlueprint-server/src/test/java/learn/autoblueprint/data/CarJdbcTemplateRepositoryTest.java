package learn.autoblueprint.data;

import learn.autoblueprint.TestHelpers;
import learn.autoblueprint.models.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CarJdbcTemplateRepositoryTest {

    private static final int MISSING_ID = 99;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CarJdbcTemplateRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state()");
    }

    @Test
    void shouldFindById() {
        Car expected = TestHelpers.createValidCar();

        Car actual = repository.findById(1);

        assertEquals(expected, actual);
        assertEquals(1, actual.getCarId());
    }

    @Test
    void add() {
        Car car = new Car(null, "Honda", "Civic", 2021, "2.0L", 158, "FWD", "Automatic");
        Car result = repository.add(car);
        assertEquals("Honda", result.getMake());
        assertEquals("Civic", result.getModel());
        assertEquals(2021, result.getYear());
    }

    @Test
    void update() {
        Car car = repository.findById(1);
        car.setMake("Updated Make");
        boolean success = repository.update(car);
        assertEquals(true, success);
        Car updatedCar = repository.findById(1);
        assertEquals("Updated Make", updatedCar.getMake());
    }

    @Test
    void deleteById() {
        Car car = TestHelpers.createValidCar();
        repository.add(car);
        boolean success = repository.deleteById(car.getCarId());
        assertEquals(true, success);
        Car deletedCar = null;
        try {
            deletedCar = repository.findById(car.getCarId());
        } catch (Exception e) {
            // expected
        }
        assertEquals(null, deletedCar);
    }
}
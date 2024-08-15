package learn.autoblueprint.domain;

import learn.autoblueprint.TestHelpers;
import learn.autoblueprint.data.CarRepository;
import learn.autoblueprint.models.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CarServiceTest {

    @Autowired
    CarService service;

    @MockBean
    CarRepository repository;

    @Test
    void shouldFindById() {
        Car expected = TestHelpers.createValidCar();

        when(repository.findById(1)).thenReturn(expected);
        Car actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAddValidCar() {
        Car car = TestHelpers.createValidCar();
        car.setCarId(0);
        Car expected = TestHelpers.createValidCar();

        when(repository.add(car)).thenReturn(expected);

        Result<Car> actual = service.add(car);

        assertTrue(actual.isSuccess());
        assertEquals(expected, actual.getPayload());
    }

    @Test
    void shouldNotAddCarWithId() {
        Car car = TestHelpers.createValidCar();
        car.setCarId(1);

        Result<Car> actual = service.add(car);

        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
    }

    @Test
    void shouldNotAddCarWithInvalidMake() {
        Car car = TestHelpers.createValidCar();
        car.setMake(null);

        Result<Car> actual = service.add(car);

        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
    }

    @Test
    void shouldNotAddCarWithInvalidModel() {
        Car car = TestHelpers.createValidCar();
        car.setModel(null);

        Result<Car> actual = service.add(car);

        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
    }

    @Test
    void shouldNotAddCarWithInvalidYear() {
        Car car = TestHelpers.createValidCar();
        car.setYear(1880);

        Result<Car> actual = service.add(car);

        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
    }

    @Test
    void shouldNotAddCarWithNullYear() {
        Car car = TestHelpers.createValidCar();
        car.setYear(null);

        Result<Car> actual = service.add(car);

        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
    }

    @Test
    void shouldNotAddCarWithYearInFuture() {
        Car car = TestHelpers.createValidCar();
        car.setYear(2026);

        Result<Car> actual = service.add(car);

        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
    }

    @Test
    void shouldUpdateCar() {
        Car car = TestHelpers.createValidCar();
        car.setCarId(1);

        when(repository.update(car)).thenReturn(true);

        Result<Car> actual = service.update(car);

        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateCarWithInvalidMake() {
        Car car = TestHelpers.createValidCar();
        car.setMake(null);

        Result<Car> actual = service.update(car);

        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
    }

    @Test
    void shouldNotUpdateCarWithInvalidModel() {
        Car car = TestHelpers.createValidCar();
        car.setModel(null);

        Result<Car> actual = service.update(car);

        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
    }

    @Test
    void shouldNotUpdateCarWithInvalidYear() {
        Car car = TestHelpers.createValidCar();
        car.setYear(1880);

        Result<Car> actual = service.update(car);

        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
    }

    @Test
    void shouldNotUpdateCarWithNullYear() {
        Car car = TestHelpers.createValidCar();
        car.setYear(null);

        Result<Car> actual = service.update(car);

        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
    }

    @Test
    void shouldNotUpdateCarWithYearInFuture() {
        Car car = TestHelpers.createValidCar();
        car.setYear(2026);

        Result<Car> actual = service.update(car);

        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
    }

    @Test
    void shouldDeleteById() {
        when(repository.deleteById(1)).thenReturn(true);
        Result<Void> actual = service.deleteById(1);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotDeleteByInvalidId() {
        when(repository.deleteById(1)).thenReturn(false);
        Result<Void> actual = service.deleteById(1);
    }
}

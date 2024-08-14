package learn.autoblueprint.domain;

import learn.autoblueprint.data.CarRepository;
import learn.autoblueprint.models.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCarWithValidCar() {
        Car validCar = new Car(1, "Toyota", "Corolla", 2022, null, 139, "FWD", "CVT");
        when(carRepository.add(validCar)).thenReturn(validCar);

        assertDoesNotThrow(() -> carService.addCar(validCar));
    }

    @Test
    public void testAddCarWithOptionalFieldsNull() {
        Car validCar = new Car(1, "Toyota", "Corolla", 2022, null, null, null, null);
        when(carRepository.add(validCar)).thenReturn(validCar);

        assertDoesNotThrow(() -> carService.addCar(validCar));
    }

    @Test
    public void testFindCarByIdWithValidId() {
        Car validCar = new Car(1, "Toyota", "Corolla", 2022, null, 139, "FWD", "CVT");
        when(carRepository.findById(1)).thenReturn(validCar);

        assertDoesNotThrow(() -> carService.findCarById(1));
    }

    @Test
    public void testAddCarWithInvalidMake() {
        Car invalidCar = new Car(1, "", "Model", 2020, null, 139, "FWD", "CVT");
        assertThrows(IllegalArgumentException.class, () -> carService.addCar(invalidCar));
    }

    @Test
    public void testAddCarWithInvalidModel() {
        Car invalidCar = new Car(1, "Make", "", 2020, null, 139, "FWD", "CVT");
        assertThrows(IllegalArgumentException.class, () -> carService.addCar(invalidCar));
    }

    @Test
    public void testAddCarWithInvalidYearTooOld() {
        Car invalidCar = new Car(1, "Make", "Model", 1899, null, 139, "FWD", "CVT");
        assertThrows(IllegalArgumentException.class, () -> carService.addCar(invalidCar));
    }

    @Test
    public void testAddCarWithInvalidYearTooFuture() {
        Car invalidCar = new Car(1, "Make", "Model", LocalDate.now().getYear() + 2, null, 139, "FWD", "CVT");
        assertThrows(IllegalArgumentException.class, () -> carService.addCar(invalidCar));
    }

    @Test
    public void testAddCarWithInvalidPower() {
        Car invalidCar = new Car(1, "Make", "Model", 2020, null, -100, "FWD", "CVT");
        assertThrows(IllegalArgumentException.class, () -> carService.addCar(invalidCar));
    }

    @Test
    public void testFindCarByIdWithInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> carService.findCarById(-1));
    }
}

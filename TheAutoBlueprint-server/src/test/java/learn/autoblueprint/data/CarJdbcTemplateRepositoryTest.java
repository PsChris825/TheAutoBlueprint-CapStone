package learn.autoblueprint.data;

import learn.autoblueprint.models.Car;
import learn.autoblueprint.data.mappers.CarMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class CarJdbcTemplateRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CarJdbcTemplateRepository carRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        Car expectedCar = new Car(1, "Toyota", "Corolla", 2022, "1.8L I4", 139, "FWD", "CVT");
        when(jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), anyInt())).thenReturn(expectedCar);

        Car actualCar = carRepository.findById(1);

        assertEquals(expectedCar, actualCar);
    }

    @Test
    public void testAdd() {
        Car carToAdd = new Car(null, "Toyota", "Corolla", 2022, "1.8L I4", 139, "FWD", "CVT");
        Car addedCar = new Car(1, "Toyota", "Corolla", 2022, "1.8L I4", 139, "FWD", "CVT");
        when(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class)).thenReturn(1);

        Car resultCar = carRepository.add(carToAdd);

        assertEquals(addedCar, resultCar);
        verify(jdbcTemplate).update(any(String.class), any(), any(), any(), any(), any(), any(), any());
    }
}

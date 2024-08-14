package learn.autoblueprint.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.autoblueprint.TestHelpers;
import learn.autoblueprint.domain.CarService;
import learn.autoblueprint.models.Car;
import learn.autoblueprint.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(CarController.class)
@Import(TestSecurityConfig.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // Optional: any setup you need before tests
    }

    // Positive Tests
    @Test
    public void testAddCarSuccess() throws Exception {
        Car car = TestHelpers.createValidCar();
        when(carService.addCar(car)).thenReturn(car);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Corolla"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2022));
    }

    @Test
    public void testFindCarByIdSuccess() throws Exception {
        Car car = TestHelpers.createValidCar();
        when(carService.findCarById(1)).thenReturn(car);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/car/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Corolla"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2022));
    }

    // Negative Tests
    @Test
    public void testAddCarMissingRequiredFields() throws Exception {
        Car car = TestHelpers.createCarWithMissingRequiredFields();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);

    }

    @Test
    public void testFindCarByIdNotFound() throws Exception {
        when(carService.findCarById(999)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/car/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

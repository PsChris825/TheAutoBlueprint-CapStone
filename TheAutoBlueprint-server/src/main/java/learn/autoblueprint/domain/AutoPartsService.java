package learn.autoblueprint.domain;

import learn.autoblueprint.data.CarRepository;
import learn.autoblueprint.models.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AutoPartsService {

    private final CarRepository carRepository;
    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiKey;

    @Autowired
    public AutoPartsService(CarRepository carRepository,
                            RestTemplate restTemplate,
                            @Value("${api.base.url}") String apiUrl,
                            @Value("${RAPIDAPI_KEY}") String apiKey) {
        this.carRepository = carRepository;
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    public Car getCarData(String make, String model, int year) {
        // Check if the data is already in the database
        List<Car> cars = carRepository.findByMakeModelYear(make, model, year);
        if (!cars.isEmpty()) {
            return cars.get(0); // Return the first car if the list is not empty
        }

        // If not, fetch from the third-party API
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("API key is missing");
        }

        String url = apiUrl + "/cars/" + make + "/" + model + "/" + year;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Car[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Car[].class);

        if (response.getBody() != null && response.getBody().length > 0) {
            Car car = response.getBody()[0];
            carRepository.add(car); // Save the fetched data to the database
            return car;
        } else {
            throw new DataNotFoundException("Car data not found in API");
        }
    }
}

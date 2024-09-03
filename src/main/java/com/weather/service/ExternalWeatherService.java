package com.weather.service;

import com.weather.dao.WeatherDataRepository;
import com.weather.model.WeatherData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalWeatherService {

    private final RestTemplate restTemplate;
    private final WeatherDataRepository weatherDataRepository;
    @Value("${weather.api.url}")
    private String apiUrl;
    @Value("${weather.api.key}")
    private String apiKey;

    public String getWeatherByCity(String city) {
        String url = String.format("%s/weather?q=%s&appid=%s", apiUrl, city, apiKey);

        try {
            // Fetch the weather data as a raw JSON string
            String response = restTemplate.getForObject(url, String.class);

            // Save the response to the database
            saveWeatherData(response, city);

            return response;
        } catch (HttpClientErrorException e) {
            log.error("Failed to retrieve weather data for city: {}", city, e);
            throw new RuntimeException("Failed to retrieve weather data for the city: " + city, e);
        }
    }

    private void saveWeatherData(String jsonResponse, String city) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);

            WeatherData weatherData = new WeatherData();
            weatherData.setCity(city);
            weatherData.setLon(root.path("coord").path("lon").asDouble());
            weatherData.setLat(root.path("coord").path("lat").asDouble());
            weatherData.setWeatherMain(root.path("weather").get(0).path("main").asText());
            weatherData.setWeatherDescription(root.path("weather").get(0).path("description").asText());
            weatherData.setTemp(root.path("main").path("temp").asDouble());
            weatherData.setFeelsLike(root.path("main").path("feels_like").asDouble());
            weatherData.setTempMin(root.path("main").path("temp_min").asDouble());
            weatherData.setTempMax(root.path("main").path("temp_max").asDouble());
            weatherData.setPressure(root.path("main").path("pressure").asInt());
            weatherData.setHumidity(root.path("main").path("humidity").asInt());
            weatherData.setVisibility(root.path("visibility").asInt());
            weatherData.setWindSpeed(root.path("wind").path("speed").asDouble());
            weatherData.setWindDeg(root.path("wind").path("deg").asInt());
            weatherData.setCloudsAll(root.path("clouds").path("all").asInt());
            weatherData.setDt(root.path("dt").asLong());
            weatherData.setCountry(root.path("sys").path("country").asText());
            weatherData.setSunrise(root.path("sys").path("sunrise").asLong());
            weatherData.setSunset(root.path("sys").path("sunset").asLong());
            weatherData.setTimezone(root.path("timezone").asInt());
            weatherData.setWeatherId(root.path("weather").get(0).path("id").asInt());
            weatherData.setWeatherIcon(root.path("weather").get(0).path("icon").asText());

            weatherDataRepository.save(weatherData);
        } catch (Exception e) {
            log.error("Failed to parse and save weather data", e);
            throw new RuntimeException("Failed to parse and save weather data", e);
        }
    }
}

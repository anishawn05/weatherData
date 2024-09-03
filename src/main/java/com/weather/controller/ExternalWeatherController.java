package com.weather.controller;

import com.weather.service.ExternalWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/weatherData")
public class ExternalWeatherController {
    private final ExternalWeatherService externalWeatherService;


    // Fetch weather data from external API
    @GetMapping("/external")
    public ResponseEntity<String> getExternalWeatherData(@RequestParam String city) {
        log.info("Fetching external weather data for city: {}", city);

        // Fetch the raw JSON weather data from the external service
        String weatherData = externalWeatherService.getWeatherByCity(city);

        // Return the raw JSON data
        return ResponseEntity.ok(weatherData);
    }
}

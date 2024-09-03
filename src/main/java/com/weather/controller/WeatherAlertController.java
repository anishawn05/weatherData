package com.weather.controller;

import com.weather.model.WeatherAlert;
import com.weather.service.WeatherAlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/weatherAlert")
@RequiredArgsConstructor
@Slf4j
public class WeatherAlertController {

    private final WeatherAlertService weatherAlertService;

    // Retrieve all weather alerts
    @GetMapping("/findAll")
    public ResponseEntity<List<WeatherAlert>> getAllWeatherAlerts() {
        log.info("Fetching all weather alerts");
        List<WeatherAlert> weatherAlerts = weatherAlertService.getAllWeatherAlerts();
        return ResponseEntity.ok(weatherAlerts);
    }

    // Create new weather alert
    @PostMapping("/create")
    public ResponseEntity<WeatherAlert> createWeatherAlert(@Valid @RequestBody WeatherAlert weatherAlert) {
        log.info("Creating new weather alert: {}", weatherAlert.getAlertType());
        WeatherAlert savedWeatherAlert = weatherAlertService.createWeatherAlert(weatherAlert);
        URI location = URI.create("/weather/" + savedWeatherAlert.getId());
        return ResponseEntity.created(location).body(savedWeatherAlert);
    }

    // Update existing weather alert by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<WeatherAlert> updateWeatherAlert(@PathVariable Long id, @Valid @RequestBody WeatherAlert weatherAlert) {
        log.info("Updating weather alert with ID: {}", id);
        WeatherAlert updatedWeatherAlert = weatherAlertService.updateWeatherAlert(id, weatherAlert);
        return ResponseEntity.ok(updatedWeatherAlert);
    }

    // Delete weather alert by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWeatherAlert(@PathVariable Long id) {
        log.info("Deleting weather alert with ID: {}", id);

        // Handle not found case
        boolean isDeleted = weatherAlertService.deleteWeatherAlert(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}

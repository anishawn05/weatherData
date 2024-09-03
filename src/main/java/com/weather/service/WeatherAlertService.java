package com.weather.service;

import com.weather.dao.WeatherAlertRepository;
import com.weather.model.WeatherAlert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class WeatherAlertService {

    private final WeatherAlertRepository weatherAlertRepository;

    @Transactional
    public WeatherAlert createWeatherAlert(WeatherAlert weatherAlert) {
        log.info("Creating weather alert: {}", weatherAlert.getAlertType());
        return weatherAlertRepository.save(weatherAlert);
    }

    @Transactional(readOnly = true)
    public List<WeatherAlert> getAllWeatherAlerts() {
        return weatherAlertRepository.findAll();
    }

    @Transactional
    public WeatherAlert updateWeatherAlert(Long id, WeatherAlert weatherAlert) {
        WeatherAlert existingAlert = weatherAlertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weather alert not found for ID: " + id));

        log.info("Updating weather alert: {}", weatherAlert.getAlertType());
        existingAlert.setAlertType(weatherAlert.getAlertType());
        existingAlert.setDescription(weatherAlert.getDescription());
        existingAlert.setStartDateTime(weatherAlert.getStartDateTime());
        existingAlert.setEndDateTime(weatherAlert.getEndDateTime());
        existingAlert.setWeatherData(weatherAlert.getWeatherData());

        return weatherAlertRepository.save(existingAlert);
    }

    @Transactional
    public boolean deleteWeatherAlert(Long id) {
        log.info("Deleting weather alert with ID: {}", id);

        if (weatherAlertRepository.existsById(id)) {  // Check if entity exists
            weatherAlertRepository.deleteById(id);
            return true;
        } else {
            log.warn("Weather alert with ID {} not found.", id);
            return false; // Return false if entity doesn't exist
        }
    }

    @Transactional(readOnly = true)
    public WeatherAlert findWeatherById(Long id) {
        log.info("Fetching weather alert with ID: {}", id);

        return weatherAlertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weather alert not found for ID: " + id));
    }

}

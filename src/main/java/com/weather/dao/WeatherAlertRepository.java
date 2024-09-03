package com.weather.dao;

import com.weather.model.WeatherAlert;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherAlertRepository extends JpaRepository<WeatherAlert,Long> {
}

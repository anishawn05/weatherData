package com.weather.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "weather_data")
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private Double lon;
    private Double lat;
    private String weatherMain;
    private String weatherDescription;
    private Double temp;
    private Double feelsLike;
    private Double tempMin;
    private Double tempMax;
    private Integer pressure;
    private Integer humidity;
    private Integer visibility;
    private Double windSpeed;
    private Integer windDeg;
    private Integer cloudsAll;
    private Long dt;
    private String country;
    private Long sunrise;
    private Long sunset;
    private Integer timezone;
    private Integer weatherId;
    private String weatherIcon;

    // Getters and setters
    // Add getter and setter methods for all fields
}

package com.weather.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "weather_alerts")
public class WeatherAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Alert type is required")
    @Size(min = 3, max = 50, message = "Alert type must be between 3 and 50 characters")
    @Column(nullable = false)
    private String alertType;

    @NotNull(message = "Description is required")
    @Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Start date and time are required")
    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @NotNull(message = "End date and time are required")
    @Column(nullable = false)
    private LocalDateTime endDateTime;

    // Many-to-one relationship with WeatherData
    @NotNull(message = "Weather data is required")
    @ManyToOne
    @JoinColumn(name = "weather_data_id", nullable = false)
    private WeatherData weatherData;
}

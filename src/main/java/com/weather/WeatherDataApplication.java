package com.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WeatherDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherDataApplication.class, args);
    }

}

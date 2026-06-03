package de.fherfurt.FitnessTrackerSystem.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "de.fherfurt.FitnessTrackerSystem")
@EntityScan("de.fherfurt.FitnessTrackerSystem.models")
public class FitnessTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FitnessTrackerApplication.class, args);
    }
}

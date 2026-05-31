package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BodyMeasurement {
    private int bodyMeasurementId;
    private int userId;
    private float weight;
    private float height;
    private int bodyFatPercentage;
    private LocalDate measuredAt;
}

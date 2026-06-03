package de.fherfurt.FitnessTrackerSystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "body_measurement")
public class BodyMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bodyMeasurementId;
    @ManyToOne //"many form me belong to one"
    @JoinColumn(name = "user_id")
    private User user;
    private float weightInKg;
    private float heightInMeter;
    private int bodyFatPercentage;
    private LocalDate measuredAt;
}

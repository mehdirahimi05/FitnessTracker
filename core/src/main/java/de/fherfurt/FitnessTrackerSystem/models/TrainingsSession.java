package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Data model representing a specific training session within the fitness tracker.
 * This class supports comparison for sorting by date,
 * and deep cloning to maintain data integrity.
 *
 * @author Mehdi Rahimi
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingsSession{
    private LocalDate date;
    private int durationInMinute;
    private float distanceInKm;
    private int burnedCalories;
    private ActivityType activityType;
    private UserDetails user;

}
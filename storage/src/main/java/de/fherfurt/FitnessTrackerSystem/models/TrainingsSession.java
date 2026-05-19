package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private int trainingsSessionId;
    private User user;
    private LocalDate date;
    private int durationInMinute;
    private float distanceInKm;
    private int burnedCalories;
    private ActivityType activityType;
}
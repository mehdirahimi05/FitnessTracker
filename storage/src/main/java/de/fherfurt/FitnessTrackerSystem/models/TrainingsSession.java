package de.fherfurt.FitnessTrackerSystem.models;

import jakarta.persistence.*;
import lombok.*;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "trainings_session")
public class TrainingsSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trainingsSessionId;
    @ManyToOne // many from me belong to one
    private User user;
    private LocalDate date;
    private int durationInMinute;
    private int burnedCalories;
    @ManyToOne // many from me belong to one
    private ActivityType activityType;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    @ManyToOne // many from me belong to one
    private WorkoutPlan workoutPlan;
}
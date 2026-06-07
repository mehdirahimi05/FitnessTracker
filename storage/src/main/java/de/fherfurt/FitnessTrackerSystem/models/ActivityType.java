package de.fherfurt.FitnessTrackerSystem.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA entity representing a type of physical activity (e.g. {@code "Running"}, {@code "Fitness"}, {@code "Yoga"}).
 *
 * <p>Mapped to the {@code activity_type} table. Acts as a classification label
 * used by {@code TrainingsSession} and linked to {@code Exercise} entities
 * via the {@code exercise_activity_types} join table.</p>
 *
 * <p>The protected no-args constructor exists solely for JPA — use the
 * all-args constructor for programmatic instantiation.</p>
 *
 * @author Mehdi Rahimi
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "activity_type")
public class ActivityType {

    /**
     * Auto-generated primary key, assigned by the database on first persist.
     * Do not set manually.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int activityTypeId;

    /**
     * Human-readable name of this activity type (e.g. {@code "Running"}, {@code "Fitness"}, {@code "Yoga"}).
     * Used as a classification label across the system.
     */
    private String name;
}
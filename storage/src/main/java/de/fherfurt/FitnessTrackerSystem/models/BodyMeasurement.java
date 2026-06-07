package de.fherfurt.FitnessTrackerSystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * JPA entity representing a single body measurement snapshot for a user.
 *
 * <p>Mapped to the {@code body_measurement} table. Each record captures
 * a user's physical metrics at a specific point in time. A user can have
 * multiple measurements over time, allowing progress tracking.</p>
 *
 * <p>The protected no-args constructor exists solely for JPA — use the
 * all-args constructor for programmatic instantiation.</p>
 *
 * @author Mehdi Rahimi
 * @see User
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "body_measurement")
public class BodyMeasurement {

    /**
     * Auto-generated primary key, assigned by the database on first persist.
     * Do not set manually.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bodyMeasurementId;

    /**
     * The user this measurement belongs to.
     *
     * <p>Many measurements can belong to one user ({@code @ManyToOne}).
     * The foreign key column in the database is {@code user_id}.</p>
     *
     * @see User
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The user's body weight at the time of measurement, in kilograms.
     */
    private float weightInKg;

    /**
     * The user's height at the time of measurement, in meters (e.g. {@code 1.82}).
     *
     * <p><b>Note:</b> Height typically does not change between measurements for adults.
     * Consider whether this field should be stored once in {@link UserProfile} instead.</p>
     */
    private float heightInMeter;

    /**
     * The user's body fat percentage at the time of measurement (e.g. {@code 18} for 18%).
     * Stored as a whole number — fractional precision is not supported by this field.
     */
    private int bodyFatPercentage;

    /**
     * The date on which this measurement was recorded.
     * Used to build a chronological history of the user's physical progress.
     */
    private LocalDate measuredAt;
}
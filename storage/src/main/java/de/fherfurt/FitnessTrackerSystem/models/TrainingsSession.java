package de.fherfurt.FitnessTrackerSystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * JPA entity representing a single training session performed by a user.
 *
 * <p>Mapped to the {@code trainings_session} table. Each record captures
 * what was trained, when, for how long, and at what difficulty — linking
 * a {@link User} to an {@link ActivityType} and optionally to a
 * {@link WorkoutPlan} that was followed during the session.</p>
 *
 * <p>The protected no-args constructor exists solely for JPA — use the
 * all-args constructor for programmatic instantiation.</p>
 *
 * @author Mehdi Rahimi
 * @see User
 * @see ActivityType
 * @see WorkoutPlan
 * @see Difficulty
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "trainings_session")
public class TrainingsSession {

    /**
     * Auto-generated primary key, assigned by the database on first persist.
     * Do not set manually.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trainingsSessionId;

    /**
     * The user who performed this training session.
     *
     * <p>Many sessions can belong to one user ({@code @ManyToOne}).
     * The foreign key column in the database is {@code user_id}.</p>
     *
     * @see User
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The date on which this training session took place.
     */
    private LocalDate date;

    /**
     * Total duration of this training session, in minutes.
     */
    private int durationInMinute;

    /**
     * Estimated number of calories burned during this session, in kilocalories (kcal).
     */
    private int burnedCalories;

    /**
     * The type of activity performed during this session (e.g. {@code "Running"}, {@code "Fitness"}).
     *
     * <p>Many sessions can reference the same activity type ({@code @ManyToOne}).
     * The foreign key column in the database is {@code activity_type_id}.</p>
     *
     * @see ActivityType
     */
    @ManyToOne
    @JoinColumn(name = "activity_type_id")
    private ActivityType activityType;

    /**
     * The difficulty level of this training session (e.g. {@code EASY}, {@code MEDIUM}, {@code HARD}).
     * Persisted as its enum name string via {@code @Enumerated(EnumType.STRING)}.
     *
     * @see Difficulty
     */
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    /**
     * The workout plan followed during this session, if any.
     *
     * <p>Many sessions can reference the same workout plan ({@code @ManyToOne}).
     * The foreign key column in the database is {@code workout_plan_id}.
     * May be {@code null} if the session was performed without a predefined plan.</p>
     *
     * @see WorkoutPlan
     */
    @ManyToOne
    @JoinColumn(name = "workout_plan_id")
    private WorkoutPlan workoutPlan;
}
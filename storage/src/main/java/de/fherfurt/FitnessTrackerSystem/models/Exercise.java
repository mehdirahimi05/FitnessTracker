package de.fherfurt.FitnessTrackerSystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * JPA entity representing a physical exercise (e.g. {@code "Bench Press"}, {@code "Squat"}).
 *
 * <p>Mapped to the {@code exercise} table. An exercise can belong to multiple
 * {@link ActivityType} categories, and the same activity type can apply to multiple
 * exercises — resolved via the {@code exercise_activity_types} join table.</p>
 *
 * <p>Exercises are also referenced by {@code WorkoutPlanExercise}, which links them
 * to a {@code WorkoutPlan} with additional payload (sets, repetitions).</p>
 *
 * <p>The protected no-args constructor exists solely for JPA — use the
 * all-args constructor for programmatic instantiation.</p>
 *
 * @author Mehdi Rahimi
 * @see ActivityType
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "exercise")
public class Exercise {

    /**
     * Auto-generated primary key, assigned by the database on first persist.
     * Do not set manually.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int exerciseId;

    /**
     * The display name of this exercise (e.g. {@code "Bench Press"}, {@code "Deadlift"}).
     */
    private String exerciseName;

    /**
     * The activity type categories this exercise belongs to (e.g. {@code "Fitness"}, {@code "Yoga"}).
     *
     * <p>Modelled as a {@code @ManyToMany} relationship — one exercise can belong to multiple
     * activity types, and one activity type can cover multiple exercises.
     * The join table {@code exercise_activity_types} holds the mapping with:</p>
     * <ul>
     *   <li>{@code exercise_id} — FK referencing this entity's primary key.</li>
     *   <li>{@code activity_type_id} — FK referencing {@link ActivityType#getActivityTypeId()}.</li>
     * </ul>
     *
     * <p>May be empty but should not be {@code null} — initialize with an empty list
     * if no categories are assigned yet.</p>
     *
     * @see ActivityType
     */
    @ManyToMany
    @JoinTable(
            name = "exercise_activity_types",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_type_id")
    )
    private List<ActivityType> activityTypes;
}
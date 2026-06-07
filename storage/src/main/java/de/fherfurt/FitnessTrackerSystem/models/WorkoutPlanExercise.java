package de.fherfurt.FitnessTrackerSystem.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA entity representing a specific exercise entry within a {@link WorkoutPlan}.
 *
 * <p>Mapped to the {@code workout_plan_exercise} table. Acts as a join between
 * {@link WorkoutPlan} and {@link Exercise}, enriched with training parameters
 * (sets and repetitions) that define how the exercise should be performed
 * within the plan.</p>
 *
 * <p>The foreign key {@code workout_plan_id} is held on this side,
 * managed by the {@code @JoinColumn} defined in {@link WorkoutPlan#getExercises()}.</p>
 *
 * <p>The protected no-args constructor exists solely for JPA — use the
 * all-args constructor for programmatic instantiation.</p>
 *
 * @author Mehdi Rahimi
 * @see WorkoutPlan
 * @see Exercise
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "workout_plan_exercise")
public class WorkoutPlanExercise {

    /**
     * Auto-generated primary key, assigned by the database on first persist.
     * Do not set manually.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int workoutPlanExerciseId;

    /**
     * The exercise to be performed as part of the parent {@link WorkoutPlan}.
     *
     * <p>Many {@code WorkoutPlanExercise} entries can reference the same {@link Exercise}
     * ({@code @ManyToOne}). The foreign key column in the database is {@code exercise_id}.</p>
     *
     * @see Exercise
     */
    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    /**
     * The number of sets to perform for this exercise within the plan (e.g. {@code 3}).
     */
    private int sets;

    /**
     * The number of repetitions per set for this exercise within the plan (e.g. {@code 12}).
     */
    private int repetitions;
}
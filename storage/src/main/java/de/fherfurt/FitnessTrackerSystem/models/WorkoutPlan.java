package de.fherfurt.FitnessTrackerSystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * JPA entity representing a structured workout plan.
 *
 * <p>Mapped to the {@code workout_plan} table. A workout plan groups multiple
 * {@link WorkoutPlanExercise} entries, each defining a specific {@link Exercise}
 * with its target sets and repetitions.</p>
 *
 * <p>A plan can be referenced by multiple {@link TrainingsSession} records,
 * allowing the same plan to be reused across sessions.</p>
 *
 * <p>The protected no-args constructor exists solely for JPA — use the
 * all-args constructor for programmatic instantiation.</p>
 *
 * @author Mehdi Rahimi
 * @see WorkoutPlanExercise
 * @see TrainingsSession
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "workout_plan")
public class WorkoutPlan {

    /**
     * Auto-generated primary key, assigned by the database on first persist.
     * Do not set manually.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int workoutPlanId;

    /**
     * The display name of this workout plan (e.g. {@code "Push Day"}, {@code "Full Body"}).
     */
    private String name;

    /**
     * The exercises included in this workout plan, each with their own sets and repetitions.
     *
     * <p>Modelled as a {@code @OneToMany} relationship — one plan owns many
     * {@link WorkoutPlanExercise} entries. The foreign key column {@code workout_plan_id}
     * is held on the {@code workout_plan_exercise} side.</p>
     *
     * <p>{@code CascadeType.ALL} is applied — creating, updating, or deleting this
     * plan will propagate to all its associated {@link WorkoutPlanExercise} entries.</p>
     *
     * <p>Should not be {@code null} — initialize with an empty list if no exercises
     * have been assigned yet.</p>
     *
     * @see WorkoutPlanExercise
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "workout_plan_id")
    private List<WorkoutPlanExercise> exercises;
}
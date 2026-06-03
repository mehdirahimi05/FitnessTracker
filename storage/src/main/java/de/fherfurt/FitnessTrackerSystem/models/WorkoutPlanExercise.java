package de.fherfurt.FitnessTrackerSystem.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "workout_plan_exercise")
public class WorkoutPlanExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int workoutPlanExerciseId;
    @ManyToOne // many of me belong to many
    private Exercise exercise;
    private int sets;
    private int repetitions;

}

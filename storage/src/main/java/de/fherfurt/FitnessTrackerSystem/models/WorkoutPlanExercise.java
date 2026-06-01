package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WorkoutPlanExercise {
    private int workoutPlanExerciseId;
    private Exercise exercise;
    private int sets;
    private int repetitions;
}

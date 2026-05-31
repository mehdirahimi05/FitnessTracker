package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlanExercise;

import java.util.List;
import java.util.Optional;

public interface IWorkoutPlanExercise {
    void createWorkoutPlanExercise(WorkoutPlanExercise workoutPlanExercise);

    List<WorkoutPlanExercise> getAllWorkoutPlanExercise();

    Optional<WorkoutPlanExercise> getWorkoutPlanExerciseById(int workoutPlanExerciseId);

    void updateWorkoutPlanExercise(WorkoutPlanExercise workoutPlanExercise);

    void deleteWorkoutPlanExerciseById(int workoutPlanExerciseId);
}

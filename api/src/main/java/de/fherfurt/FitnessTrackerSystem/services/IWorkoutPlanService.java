package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;
import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlanExercise;

import java.util.List;
import java.util.Optional;

public interface IWorkoutPlanService {
    List<WorkoutPlan> getAllWorkoutPlan();

    Optional<WorkoutPlan> getWorkoutPlanById(int workoutPlanId);

    boolean checkIsOwnWorkoutPlan(int workoutPlanId);

    WorkoutPlan addWorkoutPlan(WorkoutPlan workoutPlan);

    void updateWorkoutPlan(WorkoutPlan workoutPlan);

    void deleteWorkoutPlan(int workoutPlanId);

    WorkoutPlanExercise addExerciseToWorkoutPlan(WorkoutPlan workoutPlan, Exercise exercise, int sets, int repetitions);

    void removeExerciseFromWorkoutPlan(WorkoutPlan workoutPlan, int workoutPlanExerciseId);
}

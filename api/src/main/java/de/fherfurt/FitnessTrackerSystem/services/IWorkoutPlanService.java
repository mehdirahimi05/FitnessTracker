package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;

import java.util.List;
import java.util.Optional;

public interface IWorkoutPlanService {
    List<WorkoutPlan> getAllWorkoutPlan();

    Optional<WorkoutPlan> getWorkoutPlanById(int workoutPlanId);

    boolean checkIsOwnWorkoutPlan(int workoutPlanId);

    void addWorkoutPlan(WorkoutPlan workoutPlan);

    void updateWorkoutPlan(WorkoutPlan workoutPlan);

    void deleteWorkoutPlan(int workoutPlanId);
}

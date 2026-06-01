package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;

import java.util.List;
import java.util.Optional;

public interface IWorkoutPlanRepository {
    void createWorkoutPlan(WorkoutPlan workoutPlan);

    List<WorkoutPlan> getAllWorkoutPlan();

    Optional<WorkoutPlan> getWorkoutPlanById(int workoutPlanId);

    void updateWorkoutPlan(WorkoutPlan workoutPlan);

    void deleteWorkoutPlanById(int workoutPlanId);
}

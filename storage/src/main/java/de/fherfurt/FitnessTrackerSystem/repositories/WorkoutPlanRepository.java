package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;

import java.util.List;
import java.util.Optional;

public class WorkoutPlanRepository implements IWorkoutPlan {
    @Override
    public void createWorkoutPlan(WorkoutPlan workoutPlan) {

    }

    @Override
    public List<WorkoutPlan> getAllWorkoutPlan() {
        return List.of();
    }

    @Override
    public Optional<WorkoutPlan> getWorkoutPlanById(int workoutPlanId) {
        return Optional.empty();
    }

    @Override
    public void updateWorkoutPlan(WorkoutPlan workoutPlan) {

    }

    @Override
    public void deleteWorkoutPlanById(int workoutPlanId) {

    }
}

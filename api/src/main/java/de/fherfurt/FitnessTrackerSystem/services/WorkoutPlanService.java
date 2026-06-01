package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;
import de.fherfurt.FitnessTrackerSystem.repositories.IWorkoutPlanRepository;

import java.util.List;
import java.util.Optional;

public class WorkoutPlanService implements IWorkoutPlanService {
    private final IWorkoutPlanRepository workoutPlanRepository;

    public WorkoutPlanService(IWorkoutPlanRepository workoutPlanRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
    }

    @Override
    public List<WorkoutPlan> getAllWorkoutPlan() {
        return workoutPlanRepository.getAllWorkoutPlan();
    }

    @Override
    public Optional<WorkoutPlan> getWorkoutPlanById(int workoutPlanId) {
        return workoutPlanRepository.getWorkoutPlanById(workoutPlanId);
    }

    @Override
    public boolean checkIsOwnWorkoutPlan(int workoutPlanId) {
        var workoutPlanToCheck = workoutPlanRepository.getWorkoutPlanById(workoutPlanId);
        if (workoutPlanToCheck.isEmpty()) {
            return false;
        }
        return workoutPlanToCheck.get().getWorkoutPlanId() == workoutPlanId;
    }

    @Override
    public void addWorkoutPlan(WorkoutPlan workoutPlan) {
        workoutPlanRepository.createWorkoutPlan(workoutPlan);
    }

    @Override
    public void updateWorkoutPlan(WorkoutPlan workoutPlan) {
        workoutPlanRepository.updateWorkoutPlan(workoutPlan);
    }

    @Override
    public void deleteWorkoutPlan(int workoutPlanId) {
        workoutPlanRepository.deleteWorkoutPlanById(workoutPlanId);
    }
}

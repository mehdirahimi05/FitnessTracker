package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkoutPlanRepository implements IWorkoutPlanRepository {
    @Getter
    private final List<WorkoutPlan> workoutPlanList;

    public WorkoutPlanRepository() {
        workoutPlanList = new ArrayList<>();
    }

    @Override
    public void createWorkoutPlan(WorkoutPlan workoutPlan) {
        if (workoutPlan == null) {
            throw new IllegalArgumentException("can not be null");
        }
        workoutPlanList.add(workoutPlan);
    }

    @Override
    public List<WorkoutPlan> getAllWorkoutPlan() {
        return new ArrayList<>(workoutPlanList);
    }

    @Override
    public Optional<WorkoutPlan> getWorkoutPlanById(int workoutPlanId) {
        return workoutPlanList.stream()
                .filter(workoutPlan -> workoutPlan.getWorkoutPlanId() == workoutPlanId)
                .findFirst();
    }

    @Override
    public void updateWorkoutPlan(WorkoutPlan workoutPlan) {
        if (workoutPlan == null) {
            throw new IllegalArgumentException("can not be null");
        }
        var existingWorkoutPlan = getWorkoutPlanById(workoutPlan.getWorkoutPlanId());
        if (existingWorkoutPlan.isEmpty()) {
            throw new IllegalStateException("does not exist");
        }
        workoutPlanList.set(workoutPlanList.indexOf(existingWorkoutPlan.get()), workoutPlan);
    }

    @Override
    public void deleteWorkoutPlanById(int workoutPlanId) {
        var foundWorkoutPlanId = getWorkoutPlanById(workoutPlanId);
        if (foundWorkoutPlanId.isEmpty()) {
            throw new IllegalStateException("does not exits");
        }
        workoutPlanList.remove(foundWorkoutPlanId.get());
    }
}

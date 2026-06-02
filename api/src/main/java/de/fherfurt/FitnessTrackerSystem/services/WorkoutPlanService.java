package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;
import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlanExercise;
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

    @Override
    public WorkoutPlanExercise addExerciseToWorkoutPlan(WorkoutPlan workoutPlan, Exercise exercise, int sets, int repetitions) {
        if (workoutPlan == null || exercise == null) {
            throw new IllegalArgumentException("can not be null");
        }
        int id = workoutPlan.getExercises().size() + 1;
        WorkoutPlanExercise newExercise = new WorkoutPlanExercise(id, exercise, sets, repetitions);
        workoutPlan.getExercises().add(newExercise);
        workoutPlanRepository.updateWorkoutPlan(workoutPlan);
        return newExercise;
    }

    @Override
    public void removeExerciseFromWorkoutPlan(WorkoutPlan workoutPlan, int workoutPlanExerciseId) {
        if (workoutPlan == null) {
            throw new IllegalArgumentException("can not be null");
        }

        workoutPlan.getExercises()
                .removeIf(workoutPlanExercise -> workoutPlanExercise.getWorkoutPlanExerciseId() == workoutPlanExerciseId);
        workoutPlanRepository.updateWorkoutPlan(workoutPlan);
    }
}

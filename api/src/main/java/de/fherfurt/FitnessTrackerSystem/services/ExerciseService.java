package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import de.fherfurt.FitnessTrackerSystem.repositories.IExerciseRepository;

import java.util.List;
import java.util.Optional;

public class ExerciseService implements IExerciseService {
    private final IExerciseRepository exerciseRepository;

    public ExerciseService(IExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Exercise> getAllExercise() {
        return exerciseRepository.getAllExercise();
    }

    @Override
    public Optional<Exercise> getExerciseById(int exerciseId) {
        return exerciseRepository.getExerciseById(exerciseId);
    }

    @Override
    public boolean checkIsOwnExercise(int exerciseId) {
        var exerciseToCheck = exerciseRepository.getExerciseById(exerciseId);
        if (exerciseToCheck.isEmpty()) {
            return false;
        }
        return exerciseToCheck.get().getExerciseId() == exerciseId;
    }

    @Override
    public void addExercise(Exercise exercise) {
        exerciseRepository.createExercise(exercise);
    }

    @Override
    public void updateExercise(Exercise exercise) {
        exerciseRepository.updateExercise(exercise);
    }

    @Override
    public void deleteExerciseById(int exerciseId) {
        exerciseRepository.deleteExerciseById(exerciseId);
    }
}

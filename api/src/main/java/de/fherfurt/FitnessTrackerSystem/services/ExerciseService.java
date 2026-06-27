package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import de.fherfurt.FitnessTrackerSystem.repositories.IExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService implements IExerciseService {
    private final IExerciseRepository exerciseRepository;

    public ExerciseService(IExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Exercise> getAllExercise() {
        return exerciseRepository.findAll();
    }

    @Override
    public Optional<Exercise> getExerciseById(int exerciseId) {
        return exerciseRepository.findById(exerciseId);
    }

    @Override
    public boolean checkIsOwnExercise(int exerciseId) {
        var exerciseToCheck = exerciseRepository.findById(exerciseId);
        if (exerciseToCheck.isEmpty()) {
            return false;
        }
        return exerciseToCheck.get().getExerciseId() == exerciseId;
    }

    @Override
    public Exercise addExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public void updateExercise(Exercise exercise) {
        exerciseRepository.save(exercise);
    }

    @Override
    public void deleteExerciseById(int exerciseId) {
        exerciseRepository.deleteById(exerciseId);
    }
}

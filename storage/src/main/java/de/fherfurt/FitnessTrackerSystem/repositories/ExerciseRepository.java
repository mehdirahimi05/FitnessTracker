package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.Exercise;

import java.util.List;
import java.util.Optional;

public class ExerciseRepository implements IExerciseRepository {
    @Override
    public void createExercise(Exercise exercise) {

    }

    @Override
    public List<Exercise> getAllExercise() {
        return List.of();
    }

    @Override
    public Optional<Exercise> getExerciseById(int exerciseId) {
        return Optional.empty();
    }

    @Override
    public void updateExercise(Exercise exercise) {

    }

    @Override
    public void deleteExerciseById(int exerciseId) {

    }
}

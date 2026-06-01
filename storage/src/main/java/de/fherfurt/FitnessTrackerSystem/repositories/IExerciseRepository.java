package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.Exercise;

import java.util.List;
import java.util.Optional;

public interface IExerciseRepository {
    void createExercise(Exercise exercise);

    List<Exercise> getAllExercise();

    Optional<Exercise> getExerciseById(int exerciseId);

    void updateExercise(Exercise exercise);

    void deleteExerciseById(int exerciseId);
}

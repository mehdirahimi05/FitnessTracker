package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.Exercise;

import java.util.List;
import java.util.Optional;

public interface IExerciseService {
    List<Exercise> getAllExercise();

    Optional<Exercise> getExerciseById(int exerciseId);

    boolean checkIsOwnExercise(int exerciseId);

    void addExercise(Exercise exercise);

    void updateExercise(Exercise exercise);

    void deleteExerciseById(int exerciseId);
}

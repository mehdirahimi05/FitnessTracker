package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExerciseRepository implements IExerciseRepository {
    @Getter
    private final List<Exercise> exerciseList;

    public ExerciseRepository() {
        exerciseList = new ArrayList<>();
    }

    @Override
    public void createExercise(Exercise exercise) {
        if (exercise == null) {
            throw new IllegalArgumentException("can not be null");
        }
        exerciseList.add(exercise);
    }

    @Override
    public List<Exercise> getAllExercise() {
        return new ArrayList<>(exerciseList);
    }

    @Override
    public Optional<Exercise> getExerciseById(int exerciseId) {
        return exerciseList.stream()
                .filter(exercise -> exercise.getExerciseId() == exerciseId)
                .findFirst();
    }

    @Override
    public void updateExercise(Exercise exercise) {
        if (exercise == null) {
            throw new IllegalArgumentException("can not be null");
        }
        var existingExercise = getExerciseById(exercise.getExerciseId());
        if (existingExercise.isEmpty()) {
            throw new IllegalStateException("does not exist");
        }
        exerciseList.set(exerciseList.indexOf(existingExercise.get()), exercise);
    }

    @Override
    public void deleteExerciseById(int exerciseId) {
        var foundExerciseId = getExerciseById(exerciseId);
        if (foundExerciseId.isEmpty()) {
            throw new IllegalStateException("does not exist");
        }
        exerciseList.remove(foundExerciseId.get());
    }
}

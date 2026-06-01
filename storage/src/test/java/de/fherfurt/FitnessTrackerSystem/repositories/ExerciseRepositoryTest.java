package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link ExerciseRepository} class
 *
 * @author Mehdi Rahimi
 */
public class ExerciseRepositoryTest {
    private ExerciseRepository exerciseRepository;

    @BeforeEach
    void setUp() {
        exerciseRepository = new ExerciseRepository();
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("create Exercise: Ignore null input and maintain empty list")
    void testCreateExerciseNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            exerciseRepository.createExercise(null);
        });
    }

    /**
     * verifies that a Exercise was added to the list
     */
    @Test
    @DisplayName("create Exercise: success")
    void testCreateExerciseSuccess() {
        // Arrange
        Exercise exercise = Constants.getFirstExercise();
        int expectedSizeOfExerciseList = 1;

        // Act
        exerciseRepository.createExercise(exercise);
        int actualSizeOfExerciseList = exerciseRepository.getExerciseList().size();

        // Assert
        assertEquals(expectedSizeOfExerciseList, actualSizeOfExerciseList);
    }

    /**
     * verifies that there is no Exercise in the list
     */
    @Test
    @DisplayName("Get null Exercises")
    void testGetNullExercise() {
        // Act
        List<Exercise> exerciseList = exerciseRepository.getAllExercise();

        // Assert
        assertEquals(0, exerciseList.size());
    }

    /**
     * verifies that there is 1 Exercise in the list
     */
    @Test
    @DisplayName("Get one Exercise")
    void testGetOneExercise() {
        // Arrange
        Exercise exercise = Constants.getFirstExercise();

        // Act
        exerciseRepository.createExercise(exercise);

        List<Exercise> exerciseList = exerciseRepository.getAllExercise();

        // Assert
        assertEquals(1, exerciseList.size());
        assertTrue(exerciseList.contains(exercise));
    }

    /**
     * verifies that there are multiple Exercises
     */
    @Test
    @DisplayName("Get all Exercises")
    void testGetAllExercises() {
        // Arrange
        Exercise exercise1 = Constants.getFirstExercise();
        Exercise exercise2 = Constants.getSecondExercise();

        // Act
        exerciseRepository.createExercise(exercise1);
        exerciseRepository.createExercise(exercise2);

        List<Exercise> exerciseList = exerciseRepository.getAllExercise();

        // Assert
        assertEquals(2, exerciseList.size());
        assertTrue(exerciseList.contains(exercise1));
        assertTrue(exerciseList.contains(exercise2));
    }

    /**
     * verifies that there is no Exercise id
     */
    @Test
    @DisplayName("Get Exercise by Id: empty")
    void testGetExerciseByIdIsEmpty() {
        // Act
        Optional<Exercise> result = exerciseRepository.getExerciseById(3);

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * verifies that there is a Exercise id
     */
    @Test
    @DisplayName("Get Exercise by Id: present")
    void testGetExerciseByIdIsPresent() {
        // Arrange
        Exercise exercise = Constants.getFirstExercise();
        int exerciseId = Constants.FIRST_EXERCISE_ID;

        // Act
        exerciseRepository.createExercise(exercise);
        Optional<Exercise> result = exerciseRepository.getExerciseById(exerciseId);

        // Assert
        assertTrue(result.isPresent());
    }

    /**
     * gets the Exercise by id
     */
    @Test
    @DisplayName("Get Exercise by Id")
    void testGetExerciseById() {
        // Arrange
        Exercise exercise = Constants.getFirstExercise();
        int exerciseId = Constants.FIRST_EXERCISE_ID;

        // Act
        exerciseRepository.createExercise(exercise);
        Optional<Exercise> result = exerciseRepository.getExerciseById(exerciseId);

        // Assert
        assertEquals(exercise, result.get());
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("Update Exercise: Ignore null input and maintain empty list")
    void testUpdateExerciseNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            exerciseRepository.updateExercise(null);
        });
    }

    /**
     * verifies that the IllegalStateException was thrown when Exercise list is empty
     */
    @Test
    @DisplayName("Update Exercise: ignore empty list and maintain the list")
    void testUpdateExerciseEmpty() {
        // Arrange
        Exercise exercise = Constants.getFirstExercise();

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            exerciseRepository.updateExercise(exercise);
        });
    }

    /**
     * updates Exercise
     */
    @Test
    @DisplayName("update Exercise: success")
    void testUpdateExerciseSuccess() {
        // Arrange
        Exercise exercise = Constants.getFirstExercise();
        int exerciseId = Constants.FIRST_EXERCISE_ID;

        exerciseRepository.createExercise(exercise);

        Exercise updatedExercise = new Exercise(
                exerciseId,
                Constants.SECOND_EXERCISE_NAME,
                List.of(Constants.getFirstActivityType())
        );

        // Act
        exerciseRepository.updateExercise(updatedExercise);
        Optional<Exercise> findExerciseList = exerciseRepository.getExerciseById(exerciseId);

        // Assert
        assertEquals(Constants.SECOND_EXERCISE_NAME, findExerciseList.get().getExerciseName());
        assertEquals(Constants.FIRST_ACTIVITY_TYPE_NAME, findExerciseList.get().getActivityTypes().get(0).getName());
    }

    /**
     * verifies that the IllegalStateException was thrown when Exercise list is empty
     */
    @Test
    @DisplayName("delete Exercise by id: ignore empty list and maintain the list")
    void testDeleteExerciseByIdEmpty() {
        // Arrange
        int exerciseId = Constants.FIRST_EXERCISE_ID;

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            exerciseRepository.deleteExerciseById(exerciseId);
        });
    }

    /**
     * delete Exercise by id
     */
    @Test
    @DisplayName("delete Exercise by id: success")
    void testDeleteExerciseByIdSuccess() {
        // Arrange
        Exercise exercise = Constants.getFirstExercise();
        exerciseRepository.createExercise(exercise);
        int exerciseId = Constants.FIRST_EXERCISE_ID;
        int expectedSizeOfExerciseList = 0;

        // Act
        exerciseRepository.deleteExerciseById(exerciseId);
        int actualSizeOfExerciseList = exerciseRepository.getExerciseList().size();

        // Assert
        assertEquals(expectedSizeOfExerciseList, actualSizeOfExerciseList);
    }
}
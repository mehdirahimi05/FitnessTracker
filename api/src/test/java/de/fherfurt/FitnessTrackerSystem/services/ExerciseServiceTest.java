package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import de.fherfurt.FitnessTrackerSystem.repositories.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link ExerciseService} class
 *
 * @author Mehdi Rahimi
 */
public class ExerciseServiceTest {
    private ExerciseRepository exerciseRepository;
    private ExerciseService exerciseService;

    @BeforeEach
    void setUp() {
        exerciseRepository = new ExerciseRepository();
        exerciseService = new ExerciseService(exerciseRepository);
    }

    /**
     * gets all Exercises
     */
    @Test
    @DisplayName("getAllExercise: Success")
    void testGetAllExerciseSuccess() {
        // Arrange
        Exercise exercise1 = Constants.getFirstExercise();
        Exercise exercise2 = Constants.getSecondExercise();

        exerciseRepository.createExercise(exercise1);
        exerciseRepository.createExercise(exercise2);

        // Act
        List<Exercise> exerciseList = exerciseService.getAllExercise();

        // Assert
        assertEquals(2, exerciseList.size());
        assertTrue(exerciseList.contains(exercise1));
        assertTrue(exerciseList.contains(exercise2));
    }

    /**
     * get Exercise by id
     */
    @Test
    @DisplayName("get Exercise by Id: success")
    void testGetExerciseByIdSuccess() {
        // Arrange
        Exercise exercise = Constants.getFirstExercise();
        int exerciseId = Constants.FIRST_EXERCISE_ID;
        exerciseRepository.createExercise(exercise);

        // Act
        Optional<Exercise> result = exerciseService.getExerciseById(exerciseId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(exercise, result.get());
    }

    /**
     * verifies that false is returned when the ExerciseId does not exist
     */
    @Test
    @DisplayName("checkIsOwnExercise: ExerciseId does not exist")
    void testCheckIsOwnExerciseNotFound() {
        // Arrange
        Exercise exercise = Constants.getFirstExercise();
        exerciseRepository.createExercise(exercise);

        // Act
        boolean result = exerciseService.checkIsOwnExercise(99);

        // Assert
        assertFalse(result);
    }

    /**
     * verifies that the ExerciseId exists
     */
    @Test
    @DisplayName("checkIsOwnExercise: ExerciseId does exist")
    void testCheckIsOwnExerciseSuccess() {
        // Arrange
        Exercise exercise = Constants.getFirstExercise();
        exerciseRepository.createExercise(exercise);

        // Act
        boolean result = exerciseService.checkIsOwnExercise(exercise.getExerciseId());

        // Assert
        assertTrue(result);
    }

    /**
     * adds Exercise
     */
    @Test
    @DisplayName("addExercise: success")
    void testAddExerciseSuccess() {
        // Arrange
        Exercise exercise = Constants.getFirstExercise();
        int expectedSize = 1;

        // Act
        exerciseService.addExercise(exercise);
        int actualSize = exerciseRepository.getExerciseList().size();

        // Assert
        assertEquals(expectedSize, actualSize);
    }

    /**
     * updates Exercise
     */
    @Test
    @DisplayName("update Exercise: Success")
    void testUpdateExerciseSuccess() {
        // Arrange
        Exercise exercise = Constants.getFirstExercise();
        int exerciseId = Constants.FIRST_EXERCISE_ID;
        exerciseRepository.createExercise(exercise);

        Exercise updatedExercise = new Exercise(exerciseId, Constants.SECOND_EXERCISE_NAME, List.of(Constants.getSecondActivityType()));

        // Act
        exerciseService.updateExercise(updatedExercise);
        Optional<Exercise> result = exerciseRepository.getExerciseById(exerciseId);

        // Assert
        assertEquals(exerciseId, result.get().getExerciseId());
        assertEquals(Constants.SECOND_EXERCISE_NAME, result.get().getExerciseName());
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
        int expectedSize = 0;

        // Act
        exerciseService.deleteExerciseById(exerciseId);
        int actualSize = exerciseRepository.getExerciseList().size();

        // Assert
        assertEquals(expectedSize, actualSize);
    }
}
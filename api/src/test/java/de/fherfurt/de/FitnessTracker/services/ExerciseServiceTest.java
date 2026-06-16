package de.fherfurt.de.FitnessTracker.services;

import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import de.fherfurt.FitnessTrackerSystem.repositories.IExerciseRepository;
import de.fherfurt.FitnessTrackerSystem.services.ExerciseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {
    @Mock
    IExerciseRepository exerciseRepository;

    @InjectMocks
    ExerciseService exerciseService;

    private Exercise exercise1 = new Exercise(1, "Bankdrücken", new ArrayList<>());
    private Exercise exercise2 = new Exercise(2, "Kniebeuge", new ArrayList<>());

    /**
     * verifies that no Exercise was found
     */
    @Test
    void testGetAllExerciseNull() {
        // Arrange
        when(exerciseRepository.findAll()).thenReturn(List.of());

        // Act
        List<Exercise> result = exerciseService.getAllExercise();

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * verifies that one Exercise was found
     */
    @Test
    void testGetAllExerciseOne() {
        // Arrange
        when(exerciseRepository.findAll()).thenReturn(List.of(exercise1));

        // Act
        List<Exercise> result = exerciseService.getAllExercise();

        // Assert
        assertEquals(1, result.size());
    }

    /**
     * verifies that more than one Exercise was found
     */
    @Test
    void testGetAllExercise() {
        // Arrange
        when(exerciseRepository.findAll()).thenReturn(List.of(exercise1, exercise2));

        // Act
        List<Exercise> result = exerciseService.getAllExercise();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testGetExerciseByIdSuccess() {
        // Arrange
        when(exerciseRepository.findById(1)).thenReturn(Optional.of(exercise1));

        // Act
        Optional<Exercise> result = exerciseService.getExerciseById(exercise1.getExerciseId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(exercise1, result.get());
    }

    @Test
    void testCheckIsOwnExerciseNotFound() {
        // Arrange
        when(exerciseRepository.findById(5)).thenReturn(Optional.empty());

        // Act
        boolean result = exerciseService.checkIsOwnExercise(5);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCheckIsOwnExerciseSuccess() {
        // Arrange
        when(exerciseRepository.findById(1)).thenReturn(Optional.of(exercise1));

        // Act
        boolean result = exerciseService.checkIsOwnExercise(exercise1.getExerciseId());

        // Assert
        assertTrue(result);
    }

    @Test
    void testAddExerciseSuccess() {
        // Act
        exerciseService.addExercise(exercise1);

        // Assert -> verify if save() was called
        verify(exerciseRepository).save(exercise1);
    }

    @Test
    void testUpdateExerciseSuccess() {
        // Act
        exerciseService.updateExercise(exercise1);

        // Assert -> verify if save() was called
        verify(exerciseRepository).save(exercise1);
    }

    @Test
    void testDeleteExerciseById() {
        // Act
        exerciseService.deleteExerciseById(exercise1.getExerciseId());

        // Assert -> verify if deleteById() was called
        verify(exerciseRepository).deleteById(exercise1.getExerciseId());
    }
}
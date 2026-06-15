package de.fherfurt.de.FitnessTracker.services;

import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;
import de.fherfurt.FitnessTrackerSystem.repositories.IWorkoutPlanRepository;
import de.fherfurt.FitnessTrackerSystem.services.WorkoutPlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkoutPlanServiceTest {
    @Mock
    IWorkoutPlanRepository workoutPlanRepository;

    @InjectMocks
    WorkoutPlanService workoutPlanService;

    private final WorkoutPlan workoutPlan1 = new WorkoutPlan(1, "Brust Training", new ArrayList<>());
    private final WorkoutPlan workoutPlan2 = new WorkoutPlan(2, "Rücken Training", new ArrayList<>());

    private final Exercise exercise1 = new Exercise(1, "Bankdrücken", new ArrayList<>());
    private final Exercise exercise2 = new Exercise(2, "Klimmzüge", new ArrayList<>());

    /**
     * verifies that no WorkoutPlan was found
     */
    @Test
    void testGetAllWorkoutPlanNull() {
        // Arrange
        when(workoutPlanRepository.findAll()).thenReturn(List.of());

        // Act
        List<WorkoutPlan> result = workoutPlanService.getAllWorkoutPlan();

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * verifies that one WorkoutPlan was found
     */
    @Test
    void testGetAllWorkoutPlanOne() {
        // Arrange
        when(workoutPlanRepository.findAll()).thenReturn(List.of(workoutPlan1));

        // Act
        List<WorkoutPlan> result = workoutPlanService.getAllWorkoutPlan();

        // Assert
        assertEquals(1, result.size());
    }

    /**
     * verifies that more than one WorkoutPlan was found
     */
    @Test
    void testGetAllWorkoutPlan() {
        // Arrange
        when(workoutPlanRepository.findAll()).thenReturn(List.of(workoutPlan1, workoutPlan2));

        // Act
        List<WorkoutPlan> result = workoutPlanService.getAllWorkoutPlan();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testGetWorkoutPlanByIdSuccess() {
        // Arrange
        when(workoutPlanRepository.findById(1)).thenReturn(Optional.of(workoutPlan1));

        // Act
        Optional<WorkoutPlan> result = workoutPlanService.getWorkoutPlanById(workoutPlan1.getWorkoutPlanId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(workoutPlan1, result.get());
    }

    @Test
    void testCheckIsOwnWorkoutPlanNotFound() {
        // Arrange
        when(workoutPlanRepository.findById(5)).thenReturn(Optional.empty());

        // Act
        boolean result = workoutPlanService.checkIsOwnWorkoutPlan(5);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCheckIsOwnWorkoutPlanSuccess() {
        // Arrange
        when(workoutPlanRepository.findById(1)).thenReturn(Optional.of(workoutPlan1));

        // Act
        boolean result = workoutPlanService.checkIsOwnWorkoutPlan(workoutPlan1.getWorkoutPlanId());

        // Assert
        assertTrue(result);
    }

    @Test
    void testAddWorkoutPlanSuccess() {
        // Act
        workoutPlanService.addWorkoutPlan(workoutPlan1);

        // Assert -> verify if save() was called
        verify(workoutPlanRepository).save(workoutPlan1);
    }

    @Test
    void testUpdateWorkoutPlanSuccess() {
        // Act
        workoutPlanService.updateWorkoutPlan(workoutPlan1);

        // Assert -> verify if save() was called
        verify(workoutPlanRepository).save(workoutPlan1);
    }

    @Test
    void testDeleteWorkoutPlanById() {
        // Act
        workoutPlanService.deleteWorkoutPlan(workoutPlan1.getWorkoutPlanId());

        // Assert -> verify if deleteById() was called
        verify(workoutPlanRepository).deleteById(workoutPlan1.getWorkoutPlanId());
    }

    /**
     * verifies that a IllegalArgumentException was thrown if 0 parameters are provided
     */
    @Test
    void testAddExerciseToWorkoutPlanNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            workoutPlanService.addExerciseToWorkoutPlan(null, null, 0, 0);
        });
    }

    /**
     * verifies that an Exercise was added to the WorkoutPlan
     */
    @Test
    void testAddExerciseToWorkoutPlanSuccess() {
        // Arrange
        when(workoutPlanRepository.save(any(WorkoutPlan.class))).thenReturn(workoutPlan1);

        // Act
        workoutPlanService.addExerciseToWorkoutPlan(workoutPlan1, exercise1, 3, 6);

        // Assert
        assertEquals(1, workoutPlan1.getExercises().size());
    }

    /**
     * verifies that a IllegalArgumentException was thrown if 0 parameters are provided
     */
    @Test
    void testRemoveExerciseFromWorkoutPlanNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            workoutPlanService.removeExerciseFromWorkoutPlan(null, 0);
        });
    }

    /**
     * verifies that an Exercise was removed from the WorkoutPlan
     */
    @Test
    void testRemoveExerciseFromWorkoutPlanSuccess() {
        // Arrange
        when(workoutPlanRepository.save(any(WorkoutPlan.class))).thenReturn(workoutPlan1);

        // Act
        workoutPlanService.addExerciseToWorkoutPlan(workoutPlan1, exercise1, 3, 6);
        workoutPlanService.removeExerciseFromWorkoutPlan(workoutPlan1, 1);

        // Assert
        assertEquals(0, workoutPlan1.getExercises().size());
    }
}
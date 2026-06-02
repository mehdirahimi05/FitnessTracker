package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;
import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlanExercise;
import de.fherfurt.FitnessTrackerSystem.repositories.WorkoutPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link WorkoutPlanService} class
 *
 * @author Mehdi Rahimi
 */
public class WorkoutPlanServiceTest {
    private WorkoutPlanRepository workoutPlanRepository;
    private WorkoutPlanService workoutPlanService;

    @BeforeEach
    void setUp() {
        workoutPlanRepository = new WorkoutPlanRepository();
        workoutPlanService = new WorkoutPlanService(workoutPlanRepository);
    }

    /**
     * gets all WorkoutPlans
     */
    @Test
    @DisplayName("getAllWorkoutPlan: Success")
    void testGetAllWorkoutPlanSuccess() {
        // Arrange
        WorkoutPlan workoutPlan1 = Constants.getFirstWorkoutPlan();
        WorkoutPlan workoutPlan2 = Constants.getSecondWorkoutPlan();

        workoutPlanRepository.createWorkoutPlan(workoutPlan1);
        workoutPlanRepository.createWorkoutPlan(workoutPlan2);

        // Act
        List<WorkoutPlan> workoutPlanList = workoutPlanService.getAllWorkoutPlan();

        // Assert
        assertEquals(2, workoutPlanList.size());
        assertTrue(workoutPlanList.contains(workoutPlan1));
        assertTrue(workoutPlanList.contains(workoutPlan2));
    }

    /**
     * get WorkoutPlan by id
     */
    @Test
    @DisplayName("get WorkoutPlan by Id: success")
    void testGetWorkoutPlanByIdSuccess() {
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();
        int workoutPlanId = Constants.FIRST_WORKOUT_PLAN_ID;
        workoutPlanRepository.createWorkoutPlan(workoutPlan);

        // Act
        Optional<WorkoutPlan> result = workoutPlanService.getWorkoutPlanById(workoutPlanId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(workoutPlan, result.get());
    }

    /**
     * verifies that false is returned when the WorkoutPlanId does not exist
     */
    @Test
    @DisplayName("checkIsOwnWorkoutPlan: WorkoutPlanId does not exist")
    void testCheckIsOwnWorkoutPlanNotFound() {
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();
        workoutPlanRepository.createWorkoutPlan(workoutPlan);

        // Act
        boolean result = workoutPlanService.checkIsOwnWorkoutPlan(99);

        // Assert
        assertFalse(result);
    }

    /**
     * verifies that the WorkoutPlanId exists
     */
    @Test
    @DisplayName("checkIsOwnWorkoutPlan: WorkoutPlanId does exist")
    void testCheckIsOwnWorkoutPlanSuccess() {
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();
        workoutPlanRepository.createWorkoutPlan(workoutPlan);

        // Act
        boolean result = workoutPlanService.checkIsOwnWorkoutPlan(workoutPlan.getWorkoutPlanId());

        // Assert
        assertTrue(result);
    }

    /**
     * adds WorkoutPlan
     */
    @Test
    @DisplayName("addWorkoutPlan: success")
    void testAddWorkoutPlanSuccess() {
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();
        int expectedSize = 1;

        // Act
        workoutPlanService.addWorkoutPlan(workoutPlan);
        int actualSize = workoutPlanRepository.getWorkoutPlanList().size();

        // Assert
        assertEquals(expectedSize, actualSize);
    }

    /**
     * updates WorkoutPlan
     */
    @Test
    @DisplayName("update WorkoutPlan: Success")
    void testUpdateWorkoutPlanSuccess() {
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();
        int workoutPlanId = Constants.FIRST_WORKOUT_PLAN_ID;
        workoutPlanRepository.createWorkoutPlan(workoutPlan);

        WorkoutPlan updatedWorkoutPlan = new WorkoutPlan(
                workoutPlanId,
                Constants.SECOND_WORKOUT_PLAN_NAME,
                new ArrayList<>()
        );

        // Act
        workoutPlanService.updateWorkoutPlan(updatedWorkoutPlan);
        Optional<WorkoutPlan> result = workoutPlanRepository.getWorkoutPlanById(workoutPlanId);

        // Assert
        assertEquals(workoutPlanId, result.get().getWorkoutPlanId());
        assertEquals(Constants.SECOND_WORKOUT_PLAN_NAME, result.get().getName());
    }

    /**
     * delete WorkoutPlan by id
     */
    @Test
    @DisplayName("delete WorkoutPlan by id: success")
    void testDeleteWorkoutPlanSuccess() {
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();
        workoutPlanRepository.createWorkoutPlan(workoutPlan);
        int workoutPlanId = Constants.FIRST_WORKOUT_PLAN_ID;
        int expectedSize = 0;

        // Act
        workoutPlanService.deleteWorkoutPlan(workoutPlanId);
        int actualSize = workoutPlanRepository.getWorkoutPlanList().size();

        // Assert
        assertEquals(expectedSize, actualSize);
    }

    /**
     * verifies that a IllegalArgumentException was thrown if 0 parameters are provided
     */
    @Test
    void testAddExerciseToWorkoutPlanNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            workoutPlanService.addExerciseToWorkoutPlan(null, null, 0, 0);
        });
    }

    /**
     * verifies that ab Exercise was added to the WorkoutPlan
     */
    @Test
    void testAddExerciseToWorkoutPlanSuccess(){
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();
        Exercise exercise = Constants.getFirstExercise();
        int sets = Constants.FIRST_SETS;
        int repetitions = Constants.FIRST_REPETITIONS;

        // Act
        workoutPlanRepository.createWorkoutPlan(workoutPlan);
        workoutPlanService.addExerciseToWorkoutPlan(workoutPlan, exercise, sets, repetitions);

        // Assert
        // 2 because workoutPlan has already a plan in constants
        assertEquals(2, workoutPlan.getExercises().size());

    }

    /**
     * verifies that a IllegalArgumentException was thrown if 0 parameters are provided
     */
    @Test
    void testRemoveExerciseFromWorkoutPlanNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            workoutPlanService.removeExerciseFromWorkoutPlan(null, 0);
        });
    }

    /**
     * verifies that an Exercise was removed from the WorkoutPlan
     */
    @Test
    void testRemoveExerciseFromWorkoutPlanSuccess(){
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();
        int workoutPlanExerciseId = Constants.FIRST_WORKOUT_PLAN_EXERCISE_ID;
        Exercise exercise = Constants.getFirstExercise();
        int sets = Constants.FIRST_SETS;
        int repetitions = Constants.FIRST_REPETITIONS;

        // Act
        workoutPlanRepository.createWorkoutPlan(workoutPlan);
        workoutPlanService.addExerciseToWorkoutPlan(workoutPlan, exercise, sets, repetitions);
        workoutPlanService.removeExerciseFromWorkoutPlan(workoutPlan, workoutPlanExerciseId);

        // Assert
        // 1 because workoutPlan has already a plan in constants and i removed only 1
        assertEquals(1, workoutPlan.getExercises().size());
    }
}
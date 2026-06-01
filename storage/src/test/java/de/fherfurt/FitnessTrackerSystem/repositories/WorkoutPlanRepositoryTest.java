package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link WorkoutPlanRepository} class
 *
 * @author Mehdi Rahimi
 */
public class WorkoutPlanRepositoryTest {
    private WorkoutPlanRepository workoutPlanRepository;

    @BeforeEach
    void setUp() {
        workoutPlanRepository = new WorkoutPlanRepository();
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("create WorkoutPlan: Ignore null input and maintain empty list")
    void testCreateWorkoutPlanNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            workoutPlanRepository.createWorkoutPlan(null);
        });
    }

    /**
     * verifies that a WorkoutPlan was added to the list
     */
    @Test
    @DisplayName("create WorkoutPlan: success")
    void testCreateWorkoutPlanSuccess() {
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();
        int expectedSizeOfWorkoutPlanList = 1;

        // Act
        workoutPlanRepository.createWorkoutPlan(workoutPlan);
        int actualSizeOfWorkoutPlanList = workoutPlanRepository.getWorkoutPlanList().size();

        // Assert
        assertEquals(expectedSizeOfWorkoutPlanList, actualSizeOfWorkoutPlanList);
    }

    /**
     * verifies that there is no WorkoutPlan in the list
     */
    @Test
    @DisplayName("Get null WorkoutPlans")
    void testGetNullWorkoutPlan() {
        // Act
        List<WorkoutPlan> workoutPlanList = workoutPlanRepository.getAllWorkoutPlan();

        // Assert
        assertEquals(0, workoutPlanList.size());
    }

    /**
     * verifies that there is 1 WorkoutPlan in the list
     */
    @Test
    @DisplayName("Get one WorkoutPlan")
    void testGetOneWorkoutPlan() {
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();

        // Act
        workoutPlanRepository.createWorkoutPlan(workoutPlan);

        List<WorkoutPlan> workoutPlanList = workoutPlanRepository.getAllWorkoutPlan();

        // Assert
        assertEquals(1, workoutPlanList.size());
        assertTrue(workoutPlanList.contains(workoutPlan));
    }

    /**
     * verifies that there are multiple WorkoutPlans
     */
    @Test
    @DisplayName("Get all WorkoutPlans")
    void testGetAllWorkoutPlans() {
        // Arrange
        WorkoutPlan workoutPlan1 = Constants.getFirstWorkoutPlan();
        WorkoutPlan workoutPlan2 = Constants.getSecondWorkoutPlan();

        // Act
        workoutPlanRepository.createWorkoutPlan(workoutPlan1);
        workoutPlanRepository.createWorkoutPlan(workoutPlan2);

        List<WorkoutPlan> workoutPlanList = workoutPlanRepository.getAllWorkoutPlan();

        // Assert
        assertEquals(2, workoutPlanList.size());
        assertTrue(workoutPlanList.contains(workoutPlan1));
        assertTrue(workoutPlanList.contains(workoutPlan2));
    }

    /**
     * verifies that there is no WorkoutPlan id
     */
    @Test
    @DisplayName("Get WorkoutPlan by Id: empty")
    void testGetWorkoutPlanByIdIsEmpty() {
        // Act
        Optional<WorkoutPlan> result = workoutPlanRepository.getWorkoutPlanById(3);

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * verifies that there is a WorkoutPlan id
     */
    @Test
    @DisplayName("Get WorkoutPlan by Id: present")
    void testGetWorkoutPlanByIdIsPresent() {
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();
        int workoutPlanId = Constants.FIRST_WORKOUT_PLAN_ID;

        // Act
        workoutPlanRepository.createWorkoutPlan(workoutPlan);
        Optional<WorkoutPlan> result = workoutPlanRepository.getWorkoutPlanById(workoutPlanId);

        // Assert
        assertTrue(result.isPresent());
    }

    /**
     * gets the WorkoutPlan by id
     */
    @Test
    @DisplayName("Get WorkoutPlan by Id")
    void testGetWorkoutPlanById() {
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();
        int workoutPlanId = Constants.FIRST_WORKOUT_PLAN_ID;

        // Act
        workoutPlanRepository.createWorkoutPlan(workoutPlan);
        Optional<WorkoutPlan> result = workoutPlanRepository.getWorkoutPlanById(workoutPlanId);

        // Assert
        assertEquals(workoutPlan, result.get());
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("Update WorkoutPlan: Ignore null input and maintain empty list")
    void testUpdateWorkoutPlanNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            workoutPlanRepository.updateWorkoutPlan(null);
        });
    }

    /**
     * verifies that the IllegalStateException was thrown when WorkoutPlan list is empty
     */
    @Test
    @DisplayName("Update WorkoutPlan: ignore empty list and maintain the list")
    void testUpdateWorkoutPlanEmpty() {
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            workoutPlanRepository.updateWorkoutPlan(workoutPlan);
        });
    }

    /**
     * updates WorkoutPlan
     */
    @Test
    @DisplayName("update WorkoutPlan: success")
    void testUpdateWorkoutPlanSuccess() {
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();
        int workoutPlanId = Constants.FIRST_WORKOUT_PLAN_ID;

        workoutPlanRepository.createWorkoutPlan(workoutPlan);

        WorkoutPlan updatedWorkoutPlan = new WorkoutPlan(
                workoutPlanId,
                Constants.SECOND_WORKOUT_PLAN_NAME,
                new ArrayList<>(),
                Constants.SECOND_TRAINING_DAYS
        );

        // Act
        workoutPlanRepository.updateWorkoutPlan(updatedWorkoutPlan);
        Optional<WorkoutPlan> result = workoutPlanRepository.getWorkoutPlanById(workoutPlanId);

        // Assert
        assertEquals(Constants.SECOND_WORKOUT_PLAN_NAME, result.get().getName());
        assertEquals(Constants.SECOND_TRAINING_DAYS, result.get().getTrainingDays());
    }

    /**
     * verifies that the IllegalStateException was thrown when WorkoutPlan list is empty
     */
    @Test
    @DisplayName("delete WorkoutPlan by id: ignore empty list and maintain the list")
    void testDeleteWorkoutPlanByIdEmpty() {
        // Arrange
        int workoutPlanId = Constants.FIRST_WORKOUT_PLAN_ID;

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            workoutPlanRepository.deleteWorkoutPlanById(workoutPlanId);
        });
    }

    /**
     * delete WorkoutPlan by id
     */
    @Test
    @DisplayName("delete WorkoutPlan by id: success")
    void testDeleteWorkoutPlanByIdSuccess() {
        // Arrange
        WorkoutPlan workoutPlan = Constants.getFirstWorkoutPlan();
        workoutPlanRepository.createWorkoutPlan(workoutPlan);
        int workoutPlanId = Constants.FIRST_WORKOUT_PLAN_ID;
        int expectedSizeOfWorkoutPlanList = 0;

        // Act
        workoutPlanRepository.deleteWorkoutPlanById(workoutPlanId);
        int actualSizeOfWorkoutPlanList = workoutPlanRepository.getWorkoutPlanList().size();

        // Assert
        assertEquals(expectedSizeOfWorkoutPlanList, actualSizeOfWorkoutPlanList);
    }
}
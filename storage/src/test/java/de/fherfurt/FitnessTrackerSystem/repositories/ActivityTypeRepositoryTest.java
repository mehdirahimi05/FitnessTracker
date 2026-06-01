package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link ActivityTypeRepository} class
 *
 * @author Mehdi Rahimi
 */
public class ActivityTypeRepositoryTest {
    private ActivityTypeRepository activityTypeRepository;

    @BeforeEach
    void setUp() {
        activityTypeRepository = new ActivityTypeRepository();
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("create ActivityType: Ignore null input and maintain empty list")
    void testCreateActivityTypeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            activityTypeRepository.createActivityType(null);
        });
    }

    /**
     * verifies that a ActivityType was added to the list
     */
    @Test
    @DisplayName("create ActivityType: success")
    void testCreateActivityTypeSuccess() {
        // Arrange
        ActivityType activityType = Constants.getFirstActivityType();
        int expectedSizeOfActivityTypeList = 1;

        // Act
        activityTypeRepository.createActivityType(activityType);
        int actualSizeOfActivityTypeList = activityTypeRepository.getActivityTypeList().size();

        // Assert
        assertEquals(expectedSizeOfActivityTypeList, actualSizeOfActivityTypeList);
    }

    /**
     * verifies that there is no ActivityType in the list
     */
    @Test
    @DisplayName("Get null ActivityTypes")
    void testGetNullActivityType() {
        // Act
        List<ActivityType> activityTypeList = activityTypeRepository.getAllActivityType();

        // Assert
        assertEquals(0, activityTypeList.size());
    }

    /**
     * verifies that there is 1 ActivityType in the list
     */
    @Test
    @DisplayName("Get one ActivityType")
    void testGetOneActivityType() {
        // Arrange
        ActivityType activityType = Constants.getFirstActivityType();

        // Act
        activityTypeRepository.createActivityType(activityType);

        List<ActivityType> activityTypeList = activityTypeRepository.getAllActivityType();

        // Assert
        assertEquals(1, activityTypeList.size());
        assertTrue(activityTypeList.contains(activityType));
    }

    /**
     * verifies that there are multiple ActivityTypes
     */
    @Test
    @DisplayName("Get all ActivityTypes")
    void testGetAllActivityTypes() {
        // Arrange
        ActivityType activityType1 = Constants.getFirstActivityType();
        ActivityType activityType2 = Constants.getSecondActivityType();

        // Act
        activityTypeRepository.createActivityType(activityType1);
        activityTypeRepository.createActivityType(activityType2);

        List<ActivityType> activityTypeList = activityTypeRepository.getAllActivityType();

        // Assert
        assertEquals(2, activityTypeList.size());
        assertTrue(activityTypeList.contains(activityType1));
        assertTrue(activityTypeList.contains(activityType2));
    }

    /**
     * verifies that there is no ActivityType id
     */
    @Test
    @DisplayName("Get ActivityType by Id: empty")
    void testGetActivityTypeByIdIsEmpty() {
        // Act
        Optional<ActivityType> result = activityTypeRepository.getActivityTypeById(3);

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * verifies that there is a ActivityType id
     */
    @Test
    @DisplayName("Get ActivityType by Id: present")
    void testGetActivityTypeByIdIsPresent() {
        // Arrange
        ActivityType activityType = Constants.getFirstActivityType();
        int activityTypeId = Constants.FIRST_ACTIVITY_TYPE_ID;

        // Act
        activityTypeRepository.createActivityType(activityType);
        Optional<ActivityType> result = activityTypeRepository.getActivityTypeById(activityTypeId);

        // Assert
        assertTrue(result.isPresent());
    }

    /**
     * gets the ActivityType by id
     */
    @Test
    @DisplayName("Get ActivityType by Id")
    void testGetActivityTypeById() {
        // Arrange
        ActivityType activityType = Constants.getFirstActivityType();
        int activityTypeId = Constants.FIRST_ACTIVITY_TYPE_ID;

        // Act
        activityTypeRepository.createActivityType(activityType);
        Optional<ActivityType> result = activityTypeRepository.getActivityTypeById(activityTypeId);

        // Assert
        assertEquals(activityType, result.get());
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("Update ActivityType: Ignore null input and maintain empty list")
    void testUpdateActivityTypeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            activityTypeRepository.updateActivityType(null);
        });
    }

    /**
     * verifies that the IllegalStateException was thrown when ActivityType list is empty
     */
    @Test
    @DisplayName("Update ActivityType: ignore empty list and maintain the list")
    void testUpdateActivityTypeEmpty() {
        // Arrange
        ActivityType activityType = Constants.getFirstActivityType();

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            activityTypeRepository.updateActivityType(activityType);
        });
    }

    /**
     * updates ActivityType
     */
    @Test
    @DisplayName("update ActivityType: success")
    void testUpdateActivityTypeSuccess() {
        // Arrange
        ActivityType activityType = Constants.getFirstActivityType();
        int activityTypeId = Constants.FIRST_ACTIVITY_TYPE_ID;

        activityTypeRepository.createActivityType(activityType);

        ActivityType updatedActivityType = new ActivityType(
                activityTypeId,
                Constants.SECOND_ACTIVITY_TYPE_NAME
        );

        // Act
        activityTypeRepository.updateActivityType(updatedActivityType);
        Optional<ActivityType> result = activityTypeRepository.getActivityTypeById(activityTypeId);

        // Assert
        assertEquals(Constants.SECOND_ACTIVITY_TYPE_NAME, result.get().getName());
    }

    /**
     * verifies that the IllegalStateException was thrown when ActivityType list is empty
     */
    @Test
    @DisplayName("delete ActivityType by id: ignore empty list and maintain the list")
    void testDeleteActivityTypeByIdEmpty() {
        // Arrange
        int activityTypeId = Constants.FIRST_ACTIVITY_TYPE_ID;

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            activityTypeRepository.deleteActivityTypeById(activityTypeId);
        });
    }

    /**
     * delete ActivityType by id
     */
    @Test
    @DisplayName("delete ActivityType by id: success")
    void testDeleteActivityTypeByIdSuccess() {
        // Arrange
        ActivityType activityType = Constants.getFirstActivityType();
        activityTypeRepository.createActivityType(activityType);
        int activityTypeId = Constants.FIRST_ACTIVITY_TYPE_ID;
        int expectedSizeOfActivityTypeList = 0;

        // Act
        activityTypeRepository.deleteActivityTypeById(activityTypeId);
        int actualSizeOfActivityTypeList = activityTypeRepository.getActivityTypeList().size();

        // Assert
        assertEquals(expectedSizeOfActivityTypeList, actualSizeOfActivityTypeList);
    }
}
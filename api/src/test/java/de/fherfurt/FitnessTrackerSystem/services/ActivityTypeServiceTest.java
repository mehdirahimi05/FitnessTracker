package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import de.fherfurt.FitnessTrackerSystem.repositories.ActivityTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link ActivityTypeService} class
 *
 * @author Mehdi Rahimi
 */
public class ActivityTypeServiceTest {
    private ActivityTypeRepository activityTypeRepository;
    private ActivityTypeService activityTypeService;

    @BeforeEach
    void setUp() {
        activityTypeRepository = new ActivityTypeRepository();
        activityTypeService = new ActivityTypeService(activityTypeRepository);
    }

    /**
     * gets all ActivityTypes
     */
    @Test
    @DisplayName("getAllActivityType: Success")
    void testGetAllActivityTypeSuccess() {
        // Arrange
        ActivityType activityType1 = Constants.getFirstActivityType();
        ActivityType activityType2 = Constants.getSecondActivityType();

        activityTypeRepository.createActivityType(activityType1);
        activityTypeRepository.createActivityType(activityType2);

        // Act
        List<ActivityType> activityTypeList = activityTypeService.getAllActivityType();

        // Assert
        assertEquals(2, activityTypeList.size());
        assertTrue(activityTypeList.contains(activityType1));
        assertTrue(activityTypeList.contains(activityType2));
    }

    /**
     * get ActivityType by id
     */
    @Test
    @DisplayName("get ActivityType by Id: success")
    void testGetActivityTypeByIdSuccess() {
        // Arrange
        ActivityType activityType = Constants.getFirstActivityType();
        int activityTypeId = Constants.FIRST_ACTIVITY_TYPE_ID;
        activityTypeRepository.createActivityType(activityType);

        // Act
        Optional<ActivityType> result = activityTypeService.getActivityTypeById(activityTypeId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(activityType, result.get());
    }

    /**
     * verifies that false is returned when the ActivityTypeId does not exist
     */
    @Test
    @DisplayName("checkIsOwnActivityType: ActivityTypeId does not exist")
    void testCheckIsOwnActivityTypeNotFound() {
        // Arrange
        ActivityType activityType = Constants.getFirstActivityType();
        activityTypeRepository.createActivityType(activityType);

        // Act
        boolean result = activityTypeService.checkIsOwnActivityType(99);

        // Assert
        assertFalse(result);
    }

    /**
     * verifies that the ActivityTypeId exists
     */
    @Test
    @DisplayName("checkIsOwnActivityType: ActivityTypeId does exist")
    void testCheckIsOwnActivityTypeSuccess() {
        // Arrange
        ActivityType activityType = Constants.getFirstActivityType();
        activityTypeRepository.createActivityType(activityType);

        // Act
        boolean result = activityTypeService.checkIsOwnActivityType(activityType.getActivityTypeId());

        // Assert
        assertTrue(result);
    }

    /**
     * adds ActivityType
     */
    @Test
    @DisplayName("addActivityType: success")
    void testAddActivityTypeSuccess() {
        // Arrange
        ActivityType activityType = Constants.getFirstActivityType();
        int expectedSize = 1;

        // Act
        activityTypeService.addActivityType(activityType);
        int actualSize = activityTypeRepository.getActivityTypeList().size();

        // Assert
        assertEquals(expectedSize, actualSize);
    }

    /**
     * updates ActivityType
     */
    @Test
    @DisplayName("update ActivityType: Success")
    void testUpdateActivityTypeSuccess() {
        // Arrange
        ActivityType activityType = Constants.getFirstActivityType();
        int activityTypeId = Constants.FIRST_ACTIVITY_TYPE_ID;
        activityTypeRepository.createActivityType(activityType);

        ActivityType updatedActivityType = new ActivityType(activityTypeId, Constants.SECOND_ACTIVITY_TYPE_NAME);

        // Act
        activityTypeService.updateActivityType(updatedActivityType);
        Optional<ActivityType> result = activityTypeRepository.getActivityTypeById(activityTypeId);

        // Assert
        assertEquals(activityTypeId, result.get().getActivityTypeId());
        assertEquals(Constants.SECOND_ACTIVITY_TYPE_NAME, result.get().getName());
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
        int expectedSize = 0;

        // Act
        activityTypeService.deleteActivityTypeById(activityTypeId);
        int actualSize = activityTypeRepository.getActivityTypeList().size();

        // Assert
        assertEquals(expectedSize, actualSize);
    }
}
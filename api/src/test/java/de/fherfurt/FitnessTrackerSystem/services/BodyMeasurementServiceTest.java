package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;
import de.fherfurt.FitnessTrackerSystem.repositories.BodyMeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link BodyMeasurementService} class
 *
 * @author Mehdi Rahimi
 */
public class BodyMeasurementServiceTest {
    private BodyMeasurementRepository bodyMeasurementRepository;
    private BodyMeasurementService bodyMeasurementService;

    @BeforeEach
    void setUp() {
        bodyMeasurementRepository = new BodyMeasurementRepository();
        bodyMeasurementService = new BodyMeasurementService(bodyMeasurementRepository);
    }

    /**
     * gets all BodyMeasurements
     */
    @Test
    @DisplayName("getAllBodyMeasurement: Success")
    void testGetAllBodyMeasurementSuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement1 = Constants.getFirstBodyMeasurement();
        BodyMeasurement bodyMeasurement2 = Constants.getSecondBodyMeasurement();

        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement1);
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement2);

        // Act
        List<BodyMeasurement> bodyMeasurementList = bodyMeasurementService.getAllBodyMeasurement();

        // Assert
        assertEquals(2, bodyMeasurementList.size());
        assertTrue(bodyMeasurementList.contains(bodyMeasurement1));
        assertTrue(bodyMeasurementList.contains(bodyMeasurement2));
    }

    /**
     * get BodyMeasurement by id
     */
    @Test
    @DisplayName("get BodyMeasurement by Id: success")
    void testGetBodyMeasurementByIdSuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement();
        int bodyMeasurementId = Constants.FIRST_BODY_MEASUREMENT_ID;
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);

        // Act
        Optional<BodyMeasurement> result = bodyMeasurementService.getBodyMeasurementById(bodyMeasurementId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(bodyMeasurement, result.get());
    }

    /**
     * verifies that false is returned when the BodyMeasurementId does not exist
     */
    @Test
    @DisplayName("checkIsOwnBodyMeasurement: BodyMeasurementId does not exist")
    void testCheckIsOwnBodyMeasurementNotFound() {
        // Arrange
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement();
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);

        // Act
        boolean result = bodyMeasurementService.checkIsOwnBodyMeasurement(99);

        // Assert
        assertFalse(result);
    }

    /**
     * verifies that the BodyMeasurementId exists
     */
    @Test
    @DisplayName("checkIsOwnBodyMeasurement: BodyMeasurementId does exist")
    void testCheckIsOwnBodyMeasurementSuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement();
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);

        // Act
        boolean result = bodyMeasurementService.checkIsOwnBodyMeasurement(bodyMeasurement.getBodyMeasurementId());

        // Assert
        assertTrue(result);
    }

    /**
     * adds BodyMeasurement
     */
    @Test
    @DisplayName("addBodyMeasurement: success")
    void testAddBodyMeasurementSuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement();
        int expectedSize = 1;

        // Act
        bodyMeasurementService.addBodyMeasurement(bodyMeasurement);
        int actualSize = bodyMeasurementRepository.getBodyMeasurementList().size();

        // Assert
        assertEquals(expectedSize, actualSize);
    }

    /**
     * updates BodyMeasurement
     */
    @Test
    @DisplayName("update BodyMeasurement: Success")
    void testUpdateBodyMeasurementSuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement();
        int bodyMeasurementId = Constants.FIRST_BODY_MEASUREMENT_ID;
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);

        BodyMeasurement updatedBodyMeasurement = new BodyMeasurement(
                bodyMeasurementId,
                Constants.SECOND_USER_ID,
                Constants.SECOND_WEIGHT,
                Constants.SECOND_HEIGHT,
                Constants.SECOND_BODY_FAT_PERCENTAGE,
                Constants.SECOND_MEASURED_AT
        );

        // Act
        bodyMeasurementService.updateBodyMeasurement(updatedBodyMeasurement);
        Optional<BodyMeasurement> result = bodyMeasurementRepository.getBodyMeasurementById(bodyMeasurementId);

        // Assert
        assertEquals(bodyMeasurementId, result.get().getBodyMeasurementId());
        assertEquals(Constants.SECOND_WEIGHT, result.get().getWeight());
        assertEquals(Constants.SECOND_BODY_FAT_PERCENTAGE, result.get().getBodyFatPercentage());
        assertEquals(Constants.SECOND_MEASURED_AT, result.get().getMeasuredAt());
    }

    /**
     * delete BodyMeasurement by id
     */
    @Test
    @DisplayName("delete BodyMeasurement by id: success")
    void testDeleteBodyMeasurementSuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement();
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);
        int bodyMeasurementId = Constants.FIRST_BODY_MEASUREMENT_ID;
        int expectedSize = 0;

        // Act
        bodyMeasurementService.deleteBodyMeasurement(bodyMeasurementId);
        int actualSize = bodyMeasurementRepository.getBodyMeasurementList().size();

        // Assert
        assertEquals(expectedSize, actualSize);
    }
}
package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.repositories.BodyMeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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

    private User mehdi;

    @BeforeEach
    void setUp() {
        bodyMeasurementRepository = new BodyMeasurementRepository();
        bodyMeasurementService = new BodyMeasurementService(bodyMeasurementRepository);

        mehdi = Constants.getFirstUser();
    }

    /**
     * gets all BodyMeasurements
     */
    @Test
    @DisplayName("getAllBodyMeasurement: Success")
    void testGetAllBodyMeasurementSuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement1 = Constants.getFirstBodyMeasurement(mehdi);
        BodyMeasurement bodyMeasurement2 = Constants.getSecondBodyMeasurement(mehdi);

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
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement(mehdi);
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
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement(mehdi);
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
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement(mehdi);
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
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement(mehdi);
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
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement(mehdi);
        int bodyMeasurementId = Constants.FIRST_BODY_MEASUREMENT_ID;
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);

        BodyMeasurement updatedBodyMeasurement = new BodyMeasurement(
                bodyMeasurementId,
                Constants.getFirstUser(),
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
        assertEquals(Constants.SECOND_WEIGHT, result.get().getWeightInKg());
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
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement(mehdi);
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);
        int bodyMeasurementId = Constants.FIRST_BODY_MEASUREMENT_ID;
        int expectedSize = 0;

        // Act
        bodyMeasurementService.deleteBodyMeasurement(bodyMeasurementId);
        int actualSize = bodyMeasurementRepository.getBodyMeasurementList().size();

        // Assert
        assertEquals(expectedSize, actualSize);
    }

    /**
     * verifies that a IllegalArgumentException was thrown if 0 parameters are provided
     */
    @Test
    void testCalculateBmiNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            bodyMeasurementService.calculateBmi(0, 0);
        });
    }

    /**
     * calculates BMI successfully
     */
    @Test
    void testCalculateBmiSuccess() {
        // Arrange
        float weightInKg = Constants.FIRST_WEIGHT;
        float heightInMeters = Constants.FIRST_HEIGHT;
        float expectedBmi = 24.69f;

        // Act
        float actualBmi = bodyMeasurementService.calculateBmi(weightInKg, heightInMeters);

        // Assert
        assertEquals(expectedBmi, actualBmi, 0.01f);
    }

    /**
     * verifies that a IllegalArgumentException was thrown if userId is 0
     */
    @Test
    void testGetLatestBodyMeasurementNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            bodyMeasurementService.getLatestBodyMeasurement(null);
        });
    }

    /**
     * verifies that a IllegalStateException was thrown if the filter is empty
     */
    @Test
    void testGetLatestBodyMeasurementIsEmpty() {
        // Arrange
        User user = Constants.getFirstUser();

        assertThrows(IllegalStateException.class, () -> {
            bodyMeasurementService.getLatestBodyMeasurement(user);
        });
    }

    /**
     * verifies that the LatestBodyMeasurement was filtered successfully
     */
    @Test
    void testGetLatestBodyMeasurementSuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement1 = Constants.getFirstBodyMeasurement(mehdi);
        BodyMeasurement bodyMeasurement2 = Constants.getSecondBodyMeasurement(mehdi);

        LocalDate measuredAt2 = Constants.SECOND_MEASURED_AT;

        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement1);
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement2);

        // Act
        Optional<BodyMeasurement> result = bodyMeasurementService.getLatestBodyMeasurement(mehdi);

        // Assert
        assertEquals(measuredAt2, result.get().getMeasuredAt());
    }

    /**
     * verifies that a IllegalArgumentException was thrown if 0 parameters are provided
     */
    @Test
    void testGetBodyMeasurementHistoryNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            bodyMeasurementService.getBodyMeasurementHistory(null, null, Constants.FIRST_DATE);
        });
    }

    /**
     * verifies that IllegalStateException was thrown when filter is empty
     */
    @Test
    void testGetBodyMeasurementHistoryIsEmpty() {
        // Arrange
        User user = Constants.getFirstUser();
        LocalDate startDate = Constants.FIRST_DATE;
        LocalDate endDate = LocalDate.of(2026, 04, 30);

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            bodyMeasurementService.getBodyMeasurementHistory(user, startDate, endDate);
        });
    }

    /**
     * verifies that the BodyMeasurementHistory was filtered successfully
     */
    @Test
    void testGetBodyMeasurementHistorySuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement1 = Constants.getFirstBodyMeasurement(mehdi);
        BodyMeasurement bodyMeasurement2 = Constants.getSecondBodyMeasurement(mehdi);

        LocalDate startDate = Constants.FIRST_DATE;
        LocalDate endDate = LocalDate.of(2026, 04, 30);

        // Act
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement1);
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement2);

        List<BodyMeasurement> bodyMeasurementList = bodyMeasurementService.getBodyMeasurementHistory(mehdi, startDate, endDate);

        // Assert
        assertEquals(2, bodyMeasurementList.size());
        assertTrue(bodyMeasurementList.contains(bodyMeasurement1));
        assertTrue(bodyMeasurementList.contains(bodyMeasurement2));
    }

    /**
     * verifies that a IllegalArgumentException was thrown if 0 parameters are provided
     */
    @Test
    void testGetWeightProgressNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            bodyMeasurementService.getWeightProgress(null, null, Constants.FIRST_DATE);
        });
    }

    /**
     * verifies that IllegalStateException was thrown when filter is empty
     */
    @Test
    void testGetWeightProgressIsEmpty() {
        // Arrange
        User user = Constants.getFirstUser();
        LocalDate startDate = Constants.FIRST_DATE;
        LocalDate endDate = LocalDate.of(2026, 04, 30);

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            bodyMeasurementService.getWeightProgress(user, startDate, endDate);
        });
    }

    /**
     * verifies that WeightProgress was filters successfully
     */
    @Test
    void testGetWeightProgressSuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement1 = Constants.getFirstBodyMeasurement(mehdi);
        BodyMeasurement bodyMeasurement2 = Constants.getSecondBodyMeasurement(mehdi);

        LocalDate startDate = Constants.FIRST_DATE;
        LocalDate endDate = LocalDate.of(2026, 04, 30);

        float weight1 = Constants.FIRST_WEIGHT;
        float weight2 = Constants.SECOND_WEIGHT;

        // Act
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement1);
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement2);

        List<Float> bodyMeasurementList = bodyMeasurementService.getWeightProgress(mehdi, startDate, endDate);


        // Assert
        assertEquals(2, bodyMeasurementList.size());
        assertTrue(bodyMeasurementList.contains(weight1));
        assertTrue(bodyMeasurementList.contains(weight1));

    }

    /**
     * verifies that a IllegalArgumentException was thrown if 0 parameters are provided
     */
    @Test
    void testGetBodyFatPercentageProgressNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            bodyMeasurementService.getBodyFatPercentageProgress(null, null, Constants.FIRST_DATE);
        });
    }

    /**
     * verifies that IllegalStateException was thrown when filter is empty
     */
    @Test
    void testGetBodyFatPercentageProgressIsEmpty() {
        // Arrange
        User user = Constants.getFirstUser();
        LocalDate startDate = Constants.FIRST_DATE;
        LocalDate endDate = LocalDate.of(2026, 04, 30);

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            bodyMeasurementService.getBodyFatPercentageProgress(user, startDate, endDate);
        });
    }

    /**
     * verifies that BodyFatPercentageProgress was filters successfully
     */
    @Test
    void testGetBodyFatPercentageProgressSuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement1 = Constants.getFirstBodyMeasurement(mehdi);
        BodyMeasurement bodyMeasurement2 = Constants.getSecondBodyMeasurement(mehdi);

        LocalDate startDate = Constants.FIRST_DATE;
        LocalDate endDate = LocalDate.of(2026, 04, 30);

        int bodyFatPercentage1 = Constants.FIRST_BODY_FAT_PERCENTAGE;
        int bodyFatPercentage2 = Constants.SECOND_BODY_FAT_PERCENTAGE;

        // Act
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement1);
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement2);

        List<Integer> bodyMeasurementList = bodyMeasurementService.getBodyFatPercentageProgress(mehdi, startDate, endDate);


        // Assert
        assertEquals(2, bodyMeasurementList.size());
        assertTrue(bodyMeasurementList.contains(bodyFatPercentage1));
        assertTrue(bodyMeasurementList.contains(bodyFatPercentage2));

    }
}
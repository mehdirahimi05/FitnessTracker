package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;
import de.fherfurt.FitnessTrackerSystem.models.User;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link BodyMeasurementRepository} class
 *
 * @author Mehdi Rahimi
 */
public class BodyMeasurementRepositoryTest {
    private BodyMeasurementRepository bodyMeasurementRepository;
    private User mehdi;

    @BeforeEach
    void setUp() {
        bodyMeasurementRepository = new BodyMeasurementRepository();
        mehdi = Constants.getFirstUser();
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("create BodyMeasurement: Ignore null input and maintain empty list")
    void testCreateBodyMeasurementNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            bodyMeasurementRepository.createBodyMeasurement(null);
        });
    }

    /**
     * verifies that a BodyMeasurement was added to the list
     */
    @Test
    @DisplayName("create BodyMeasurement: success")
    void testCreateBodyMeasurementSuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement(mehdi);
        int expectedSizeOfBodyMeasurementList = 1;

        // Act
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);
        int actualSizeOfBodyMeasurementList = bodyMeasurementRepository.getBodyMeasurementList().size();

        // Assert
        assertEquals(expectedSizeOfBodyMeasurementList, actualSizeOfBodyMeasurementList);
    }

    /**
     * verifies that there is no BodyMeasurement in the list
     */
    @Test
    @DisplayName("Get null BodyMeasurements")
    void testGetNullBodyMeasurement() {
        // Act
        List<BodyMeasurement> bodyMeasurementList = bodyMeasurementRepository.getAllBodyMeasurement();

        // Assert
        assertEquals(0, bodyMeasurementList.size());
    }

    /**
     * verifies that there is 1 BodyMeasurement in the list
     */
    @Test
    @DisplayName("Get one BodyMeasurement")
    void testGetOneBodyMeasurement() {
        // Arrange
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement(mehdi);

        // Act
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);

        List<BodyMeasurement> bodyMeasurementList = bodyMeasurementRepository.getAllBodyMeasurement();

        // Assert
        assertEquals(1, bodyMeasurementList.size());
        assertTrue(bodyMeasurementList.contains(bodyMeasurement));
    }

    /**
     * verifies that there are multiple BodyMeasurements
     */
    @Test
    @DisplayName("Get all BodyMeasurements")
    void testGetAllBodyMeasurements() {
        // Arrange
        BodyMeasurement bodyMeasurement1 = Constants.getFirstBodyMeasurement(mehdi);
        BodyMeasurement bodyMeasurement2 = Constants.getSecondBodyMeasurement(mehdi);

        // Act
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement1);
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement2);

        List<BodyMeasurement> bodyMeasurementList = bodyMeasurementRepository.getAllBodyMeasurement();

        // Assert
        assertEquals(2, bodyMeasurementList.size());
        assertTrue(bodyMeasurementList.contains(bodyMeasurement1));
        assertTrue(bodyMeasurementList.contains(bodyMeasurement2));
    }

    /**
     * verifies that there is no BodyMeasurement id
     */
    @Test
    @DisplayName("Get BodyMeasurement by Id: empty")
    void testGetBodyMeasurementByIdIsEmpty() {
        // Act
        Optional<BodyMeasurement> result = bodyMeasurementRepository.getBodyMeasurementById(3);

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * verifies that there is a BodyMeasurement id
     */
    @Test
    @DisplayName("Get BodyMeasurement by Id: present")
    void testGetBodyMeasurementByIdIsPresent() {
        // Arrange
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement(mehdi);
        int bodyMeasurementId = Constants.FIRST_BODY_MEASUREMENT_ID;

        // Act
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);
        Optional<BodyMeasurement> result = bodyMeasurementRepository.getBodyMeasurementById(bodyMeasurementId);

        // Assert
        assertTrue(result.isPresent());
    }

    /**
     * gets the BodyMeasurement by id
     */
    @Test
    @DisplayName("Get BodyMeasurement by Id")
    void testGetBodyMeasurementById() {
        // Arrange
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement(mehdi);
        int bodyMeasurementId = Constants.FIRST_BODY_MEASUREMENT_ID;

        // Act
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);
        Optional<BodyMeasurement> result = bodyMeasurementRepository.getBodyMeasurementById(bodyMeasurementId);

        // Assert
        assertEquals(bodyMeasurement, result.get());
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("Update BodyMeasurement: Ignore null input and maintain empty list")
    void testUpdateBodyMeasurementNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            bodyMeasurementRepository.updateBodyMeasurement(null);
        });
    }

    /**
     * verifies that the IllegalStateException was thrown when BodyMeasurement list is empty
     */
    @Test
    @DisplayName("Update BodyMeasurement: ignore empty list and maintain the list")
    void testUpdateBodyMeasurementEmpty() {
        // Arrange
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement(mehdi);

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            bodyMeasurementRepository.updateBodyMeasurement(bodyMeasurement);
        });
    }

    /**
     * updates BodyMeasurement
     */
    @Test
    @DisplayName("update BodyMeasurement: success")
    void testUpdateBodyMeasurementSuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement(mehdi);
        int bodyMeasurementId = Constants.FIRST_BODY_MEASUREMENT_ID;

        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);

        BodyMeasurement updatedBodyMeasurement = new BodyMeasurement(
                bodyMeasurementId,
                mehdi,
                Constants.SECOND_WEIGHT,
                Constants.SECOND_HEIGHT,
                Constants.SECOND_BODY_FAT_PERCENTAGE,
                Constants.SECOND_MEASURED_AT
        );

        // Act
        bodyMeasurementRepository.updateBodyMeasurement(updatedBodyMeasurement);
        Optional<BodyMeasurement> result = bodyMeasurementRepository.getBodyMeasurementById(bodyMeasurementId);

        // Assert
        assertEquals(mehdi, result.get().getUser());
        assertEquals(Constants.SECOND_WEIGHT, result.get().getWeightInKg());
        assertEquals(Constants.SECOND_HEIGHT, result.get().getHeightInMeter());
        assertEquals(Constants.SECOND_BODY_FAT_PERCENTAGE, result.get().getBodyFatPercentage());
        assertEquals(Constants.SECOND_MEASURED_AT, result.get().getMeasuredAt());
    }

    /**
     * verifies that the IllegalStateException was thrown when BodyMeasurement list is empty
     */
    @Test
    @DisplayName("delete BodyMeasurement by id: ignore empty list and maintain the list")
    void testDeleteBodyMeasurementByIdEmpty() {
        // Arrange
        int bodyMeasurementId = Constants.FIRST_BODY_MEASUREMENT_ID;

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            bodyMeasurementRepository.deleteBodyMeasurementById(bodyMeasurementId);
        });
    }

    /**
     * delete BodyMeasurement by id
     */
    @Test
    @DisplayName("delete BodyMeasurement by id: success")
    void testDeleteBodyMeasurementByIdSuccess() {
        // Arrange
        BodyMeasurement bodyMeasurement = Constants.getFirstBodyMeasurement(mehdi);
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);
        int bodyMeasurementId = Constants.FIRST_BODY_MEASUREMENT_ID;
        int expectedSizeOfBodyMeasurementList = 0;

        // Act
        bodyMeasurementRepository.deleteBodyMeasurementById(bodyMeasurementId);
        int actualSizeOfBodyMeasurementList = bodyMeasurementRepository.getBodyMeasurementList().size();

        // Assert
        assertEquals(expectedSizeOfBodyMeasurementList, actualSizeOfBodyMeasurementList);
    }
}
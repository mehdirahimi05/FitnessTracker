package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.models.UserRole;
import de.fherfurt.FitnessTrackerSystem.repositories.IBodyMeasurementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BodyMeasurementServiceTest {
    @Mock
    IBodyMeasurementRepository bodyMeasurementRepository;

    @InjectMocks
    BodyMeasurementService bodyMeasurementService;

    private User mehdi = new User(1, "mehdi", "pass", UserRole.USER, null);

    private BodyMeasurement bodyMeasurement1 = new BodyMeasurement(
            1,
            mehdi,
            80,
            1.80f,
            23,
            LocalDate.of(2026, 1, 1)
    );

    private BodyMeasurement bodyMeasurement2 = new BodyMeasurement(
            2,
            mehdi,
            78,
            1.80f,
            22,
            LocalDate.of(2026, 1, 14)
    );

    /**
     * verifies that no BodyMeasurement was found
     */
    @Test
    void testGetAllBodyMeasurementNull() {
        // Arrange
        when(bodyMeasurementRepository.findAll()).thenReturn(List.of());

        // Act
        List<BodyMeasurement> result = bodyMeasurementService.getAllBodyMeasurement();

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * verifies that one BodyMeasurement was found
     */
    @Test
    void testGetAllBodyMeasurementOne() {
        // Arrange
        when(bodyMeasurementRepository.findAll()).thenReturn(List.of(bodyMeasurement1));

        // Act
        List<BodyMeasurement> result = bodyMeasurementService.getAllBodyMeasurement();

        // Assert
        assertEquals(1, result.size());
    }

    /**
     * verifies that more than one BodyMeasurement was found
     */
    @Test
    void testGetAllBodyMeasurement() {
        // Arrange
        when(bodyMeasurementRepository.findAll()).thenReturn(List.of(bodyMeasurement1, bodyMeasurement2));

        // Act
        List<BodyMeasurement> result = bodyMeasurementService.getAllBodyMeasurement();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testGetBodyMeasurementByIdSuccess() {
        // Arrange
        when(bodyMeasurementRepository.findById(1)).thenReturn(Optional.of(bodyMeasurement1));

        // Act
        Optional<BodyMeasurement> result = bodyMeasurementService.getBodyMeasurementById(bodyMeasurement1.getBodyMeasurementId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(bodyMeasurement1, result.get());
    }

    @Test
    void testCheckIsOwnBodyMeasurementNotFound() {
        // Arrange
        when(bodyMeasurementRepository.findById(5)).thenReturn(Optional.empty());

        // Act
        boolean result = bodyMeasurementService.checkIsOwnBodyMeasurement(5);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCheckIsOwnBodyMeasurementSuccess() {
        // Arrange
        when(bodyMeasurementRepository.findById(1)).thenReturn(Optional.of(bodyMeasurement1));

        // Act
        boolean result = bodyMeasurementService.checkIsOwnBodyMeasurement(bodyMeasurement1.getBodyMeasurementId());

        // Assert
        assertTrue(result);
    }

    @Test
    void testAddBodyMeasurementSuccess() {
        // Act
        bodyMeasurementService.addBodyMeasurement(bodyMeasurement1);

        // Assert -> verify if save() was called
        verify(bodyMeasurementRepository).save(bodyMeasurement1);
    }

    @Test
    void testUpdateBodyMeasurementSuccess() {
        // Act
        bodyMeasurementService.updateBodyMeasurement(bodyMeasurement1);

        // Assert -> verify if save() was called
        verify(bodyMeasurementRepository).save(bodyMeasurement1);
    }

    @Test
    void testDeleteBodyMeasurementById() {
        // Act
        bodyMeasurementService.deleteBodyMeasurement(bodyMeasurement1.getBodyMeasurementId());

        // Assert -> verify if deleteById() was called
        verify(bodyMeasurementRepository).deleteById(bodyMeasurement1.getBodyMeasurementId());
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
        float weightInKg = 80;
        float heightInMeters = 1.80f;
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
        assertThrows(IllegalStateException.class, () -> {
            bodyMeasurementService.getLatestBodyMeasurement(mehdi);
        });
    }

    /**
     * verifies that the LatestBodyMeasurement was filtered successfully
     */
    @Test
    void testGetLatestBodyMeasurementSuccess() {
        // Arrange
        when(bodyMeasurementRepository.findAll()).thenReturn(List.of(bodyMeasurement1, bodyMeasurement2));

        LocalDate measuredAt2 = LocalDate.of(2026, 1, 14);

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
            bodyMeasurementService.getBodyMeasurementHistory(null, null, null);
        });
    }

    /**
     * verifies that IllegalStateException was thrown when filter is empty
     */
    @Test
    void testGetBodyMeasurementHistoryIsEmpty() {
        // Arrange
        LocalDate startDate = LocalDate.of(2026, 01, 1);
        LocalDate endDate = LocalDate.of(2026, 01, 14);

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            bodyMeasurementService.getBodyMeasurementHistory(mehdi, startDate, endDate);
        });
    }

    /**
     * verifies that the BodyMeasurementHistory was filtered successfully
     */
    @Test
    void testGetBodyMeasurementHistorySuccess() {
        // Arrange
        when(bodyMeasurementRepository.findAll()).thenReturn(List.of(bodyMeasurement1, bodyMeasurement2));

        LocalDate startDate = LocalDate.of(2026, 01, 1);
        LocalDate endDate = LocalDate.of(2026, 01, 14);

        // Act
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
            bodyMeasurementService.getWeightProgress(null, null, null);
        });
    }

    /**
     * verifies that IllegalStateException was thrown when filter is empty
     */
    @Test
    void testGetWeightProgressIsEmpty() {
        // Arrange
        LocalDate startDate = LocalDate.of(2026, 01, 1);
        LocalDate endDate = LocalDate.of(2026, 01, 14);

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            bodyMeasurementService.getWeightProgress(mehdi, startDate, endDate);
        });
    }

    /**
     * verifies that WeightProgress was filters successfully
     */
    @Test
    void testGetWeightProgressSuccess() {
        // Arrange
        when(bodyMeasurementRepository.findAll()).thenReturn(List.of(bodyMeasurement1, bodyMeasurement2));

        LocalDate startDate = LocalDate.of(2026, 01, 1);
        LocalDate endDate = LocalDate.of(2026, 01, 14);

        float weight1 = 80;
        float weight2 = 78;

        // Act
        List<Float> bodyMeasurementList = bodyMeasurementService.getWeightProgress(mehdi, startDate, endDate);

        // Assert
        assertEquals(2, bodyMeasurementList.size());
        assertTrue(bodyMeasurementList.contains(weight1));
        assertTrue(bodyMeasurementList.contains(weight2));
    }

    /**
     * verifies that a IllegalArgumentException was thrown if 0 parameters are provided
     */
    @Test
    void testGetBodyFatPercentageProgressNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            bodyMeasurementService.getBodyFatPercentageProgress(null, null, null);
        });
    }

    /**
     * verifies that IllegalStateException was thrown when filter is empty
     */
    @Test
    void testGetBodyFatPercentageProgressIsEmpty() {
        // Arrange
        LocalDate startDate = LocalDate.of(2026, 01, 1);
        LocalDate endDate = LocalDate.of(2026, 01, 14);

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            bodyMeasurementService.getBodyFatPercentageProgress(mehdi, startDate, endDate);
        });
    }

    /**
     * verifies that BodyFatPercentageProgress was filters successfully
     */
    @Test
    void testGetBodyFatPercentageProgressSuccess() {
        // Arrange
        when(bodyMeasurementRepository.findAll()).thenReturn(List.of(bodyMeasurement1, bodyMeasurement2));

        LocalDate startDate = LocalDate.of(2026, 01, 1);
        LocalDate endDate = LocalDate.of(2026, 01, 14);

        int bodyFatPercentage1 = 23;
        int bodyFatPercentage2 = 22;

        // Act
        List<Integer> bodyMeasurementList = bodyMeasurementService.getBodyFatPercentageProgress(mehdi, startDate, endDate);

        // Assert
        assertEquals(2, bodyMeasurementList.size());
        assertTrue(bodyMeasurementList.contains(bodyFatPercentage1));
        assertTrue(bodyMeasurementList.contains(bodyFatPercentage2));

    }

}
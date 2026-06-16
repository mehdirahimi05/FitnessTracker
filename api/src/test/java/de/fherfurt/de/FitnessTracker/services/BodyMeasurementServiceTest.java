package de.fherfurt.de.FitnessTracker.services;

import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.models.UserRole;
import de.fherfurt.FitnessTrackerSystem.repositories.IBodyMeasurementRepository;
import de.fherfurt.FitnessTrackerSystem.services.BodyMeasurementService;
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
}
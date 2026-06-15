package de.fherfurt.de.FitnessTracker.services;

import de.fherfurt.FitnessTrackerSystem.models.*;
import de.fherfurt.FitnessTrackerSystem.repositories.ITrainingsSessionRepository;
import de.fherfurt.FitnessTrackerSystem.services.TrainingsSessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TrainingsSessionServiceTest {
    @Mock
    ITrainingsSessionRepository trainingsSessionRepository;

    @InjectMocks
    TrainingsSessionService trainingsSessionService;

    private User mehdi = new User(1, "mehdi", "pass", UserRole.USER, null);

    private TrainingsSession trainingsSession1 = new TrainingsSession(
            1,
            mehdi,
            LocalDate.of(2026, 3, 20),
            60,
            500,
            new ActivityType(1, "Krafttraining"),
            Difficulty.MEDIUM,
            new WorkoutPlan(1, "Brust Training", new ArrayList<>())
    );

    private TrainingsSession trainingsSession2 = new TrainingsSession(
            2,
            mehdi,
            LocalDate.of(2026, 3, 21),
            45,
            400,
            new ActivityType(1, "Krafttraining"),
            Difficulty.EASY,
            new WorkoutPlan(1, "Brust Training", new ArrayList<>())
    );

    /**
     * verifies that no TrainingsSession was found
     */
    @Test
    void testGetAllTrainingsSessionNull(){
        // Arrange
        when(trainingsSessionRepository.findAll()).thenReturn(List.of());

        // Act
        List<TrainingsSession> result = trainingsSessionService.getAllTrainingsSessions();

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * verifies that one trainingsSession was found
     */
    @Test
    void testGetAllTrainingsSessionOne(){
        // Arrange
        when(trainingsSessionRepository.findAll()).thenReturn(List.of(trainingsSession1));

        // Act
        List<TrainingsSession> result = trainingsSessionService.getAllTrainingsSessions();

        // Assert
        assertEquals(1, result.size());
    }

    /**
     * verifies that more than one trainingsSession was found
     */
    @Test
    void testGetAllTrainingsSession(){
        // Arrange
        when(trainingsSessionRepository.findAll()).thenReturn(List.of(trainingsSession1, trainingsSession2));

        // Act
        List<TrainingsSession> result = trainingsSessionService.getAllTrainingsSessions();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testGetTrainingsSessionByIdSuccess(){
        // Arrange
        when(trainingsSessionRepository.findById(1)).thenReturn(Optional.of(trainingsSession1));

        // Act
        Optional<TrainingsSession> result = trainingsSessionService.getTrainingsSessionById(trainingsSession1.getTrainingsSessionId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(trainingsSession1, result.get());
    }

    @Test
    void testCheckIsOwnTrainingsSessionNotFound(){
        // Arrange
        when(trainingsSessionRepository.findById(5)).thenReturn(Optional.empty());

        // Act
        boolean result = trainingsSessionService.checkIsOwnTrainingsSession(5);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCheckIsOwnTrainingsSessionSuccess(){
        // Arrange
        when(trainingsSessionRepository.findById(1)).thenReturn(Optional.of(trainingsSession1));

        // Act
        boolean result = trainingsSessionService.checkIsOwnTrainingsSession(trainingsSession1.getTrainingsSessionId());

        // Assert
        assertTrue(result);
    }

    @Test
    void testAddTrainingsSessionSuccess(){
        // Arrange
        trainingsSessionService.addTrainingsSession(trainingsSession1);

        // Assert -> verify if save() was called
        verify(trainingsSessionRepository).save(trainingsSession1);
    }

    @Test
    void testUpdateTrainingsSessionSuccess(){
        // Arrange
        trainingsSessionService.deleteTrainingsSessionById(trainingsSession1.getTrainingsSessionId());

        // Assert -> verify if deleteById() was called
        verify(trainingsSessionRepository).deleteById(trainingsSession1.getTrainingsSessionId());
    }


}

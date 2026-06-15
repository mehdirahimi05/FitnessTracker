package de.fherfurt.de.FitnessTracker.services;

import de.fherfurt.FitnessTrackerSystem.models.*;
import de.fherfurt.FitnessTrackerSystem.repositories.ITrainingsSessionRepository;
import de.fherfurt.FitnessTrackerSystem.services.TrainingsSessionService;
import de.fherfurt.FitnessTrackerSystem.services.utils.TrainingsSessionFilter;
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

    private TrainingsSession trainingsSession3 = new TrainingsSession(
            3,
            mehdi,
            LocalDate.of(2026, 3, 27),
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
        // Act
        trainingsSessionService.addTrainingsSession(trainingsSession1);

        // Assert -> verify if save() was called
        verify(trainingsSessionRepository).save(trainingsSession1);
    }

    @Test
    void testUpdateTrainingsSessionSuccess(){
        // Act
        trainingsSessionService.updateTrainingsSession(trainingsSession1);

        // Assert -> verify if save() was called
        verify(trainingsSessionRepository).save(trainingsSession1);
    }

    @Test
    void testDeleteTrainingsSessionById(){
        // Act
        trainingsSessionService.deleteTrainingsSessionById(trainingsSession1.getTrainingsSessionId());

        // Assert -> verify if deleteById() was called
        verify(trainingsSessionRepository).deleteById(trainingsSession1.getTrainingsSessionId());
    }

    /**
     * verifies that the most active user by total time was found successfully
     */
    @Test
    void testGetMostActiveUserByAmountOfTrainingsSessionsSuccess() {
        // Arrange
        when(trainingsSessionRepository.findAll()).thenReturn(List.of(trainingsSession1, trainingsSession2));

        // Act
        User findWinner = trainingsSessionService.getMostActiveUserByAmountOfTrainingsSessions(LocalDate.of(2026, 03, 20), LocalDate.of(2026, 03, 31));

        // Assert
        assertEquals(mehdi, findWinner);
    }

    /**
     * verifies that the list filter is null if null parameters are provides
     */
    @Test
    void testFilterTrainingsSession() {
        // Arrange
        when(trainingsSessionRepository.findAll()).thenReturn(List.of(trainingsSession1));

        // Act
        List<TrainingsSession> trainingsSessionFilter1 = trainingsSessionService.filterTrainingsSession(TrainingsSessionFilter.builder().withStartDate(null).build());
        List<TrainingsSession> trainingsSessionFilter2 = trainingsSessionService.filterTrainingsSession(TrainingsSessionFilter.builder().withEndDate(null).build());
        List<TrainingsSession> trainingsSessionFilter4 = trainingsSessionService.filterTrainingsSession(TrainingsSessionFilter.builder().withUser(null).build());
        List<TrainingsSession> trainingsSessionFilter5 = trainingsSessionService.filterTrainingsSession(TrainingsSessionFilter.builder().withMinBurnedCalories(0).build());

        // Assert
        assertEquals(1, trainingsSessionFilter1.size());
        assertEquals(1, trainingsSessionFilter2.size());
        assertEquals(1, trainingsSessionFilter4.size());
        assertEquals(1, trainingsSessionFilter5.size());
    }

    /**
     * verifies that the list ist filtert successfully
     */
    @Test
    void testFilterTrainingsSessionSuccess() {
        // Arrange
        when(trainingsSessionRepository.findAll()).thenReturn(List.of(trainingsSession1));

        // Act
        List<TrainingsSession> trainingsSessionFilter1 = trainingsSessionService.filterTrainingsSession(TrainingsSessionFilter.builder().withStartDate(LocalDate.of(2026, 3, 20)).build());
        List<TrainingsSession> trainingsSessionFilter2 = trainingsSessionService.filterTrainingsSession(TrainingsSessionFilter.builder().withEndDate(LocalDate.of(2026, 3, 21)).build());
        List<TrainingsSession> trainingsSessionFilter4 = trainingsSessionService.filterTrainingsSession(TrainingsSessionFilter.builder().withUser(mehdi).build());
        List<TrainingsSession> trainingsSessionFilter5 = trainingsSessionService.filterTrainingsSession(TrainingsSessionFilter.builder().withMinBurnedCalories(500).build());
        List<TrainingsSession> trainingsSessionFilter6 = trainingsSessionService.filterTrainingsSession(TrainingsSessionFilter.builder().withMaxBurnedCalories(1200).build());

        // Assert
        assertEquals(1, trainingsSessionFilter1.size());
        assertEquals(1, trainingsSessionFilter2.size());
        assertEquals(1, trainingsSessionFilter4.size());
        assertEquals(1, trainingsSessionFilter5.size());
        assertEquals(1, trainingsSessionFilter6.size());
    }

    /**
     * verifies that a IllegalArgumentException was thrown when 0 parameters are provided
     */
    @Test
    void testGetTrainingsSessionStreakUserNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            trainingsSessionService.getTrainingSessionStreak(null);
        });
    }

    /**
     * verifies that a IllegalStateException was thrown when filterSession is Empty
     */
    @Test
    void testGetTrainingsSessionStreakIsEmpty() {
        // Act
        assertThrows(IllegalStateException.class, () -> {
            trainingsSessionService.getTrainingSessionStreak(mehdi);
        });
    }

    /**
     * verifies that the streak is 1
     */
    @Test
    void testGetTrainingsSessionStreakOne() {
        // Arrange
        when(trainingsSessionRepository.findAll()).thenReturn(List.of(trainingsSession1));

        // Act
        int result = trainingsSessionService.getTrainingSessionStreak(mehdi);

        // Assert
        assertEquals(1, result);
    }

    /**
     * verifies that the streak is more than 1 without break
     */
    @Test
    void testGetTrainingsSessionStreakWithoutBreak() {
        // Arrange
        when(trainingsSessionRepository.findAll()).thenReturn(List.of(trainingsSession1, trainingsSession2, trainingsSession3));

        // Act
        int result = trainingsSessionService.getTrainingSessionStreak(mehdi);

        // Assert
        assertEquals(2, result);
    }

    /**
     * verifies that the streak is more than 1 with brake
     */
    @Test
    void testGetTrainingsSessionStreakWitBreak() {
        // Arrange
        when(trainingsSessionRepository.findAll()).thenReturn(List.of(trainingsSession1, trainingsSession2, trainingsSession3));

        // Act
        int result = trainingsSessionService.getTrainingSessionStreak(mehdi);

        // Assert
        assertEquals(2, result);
    }

    /**
     * verifies that a IllegalArgumentException was thrown when 0 parameters are provided
     */
    @Test
    void testGetDailyTrainingsSessionSummaryNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            trainingsSessionService.getDailyTrainingsSessionSummary(null, null);
        });
    }

    /**
     * verifies that a IllegalStateException was thrown when filterSession is Empty
     */
    @Test
    void testGetDailyTrainingsSessionSummaryIsEmpty() {
        // Act
        assertThrows(IllegalStateException.class, () -> {
            trainingsSessionService.getDailyTrainingsSessionSummary(mehdi, trainingsSession1.getDate());
        });
    }

    /**
     * verifies that DailyTrainingsSessionSummary was summed successfully
     */
    @Test
    void testGetDailyTrainingsSessionSummarySuccess() {
        // Arrange
        // I changed the date of trainingsSession2 to be the same like trainingsSession1
        trainingsSession2.setDate(LocalDate.of(2026, 03, 20));
        when(trainingsSessionRepository.findAll()).thenReturn(List.of(trainingsSession1, trainingsSession2));

        // Act
        TrainingsSessionSummary summaryList = trainingsSessionService.getDailyTrainingsSessionSummary(mehdi, trainingsSession1.getDate());

        // Assert
        assertTrue(summaryList.getTrainingsSessions().contains(trainingsSession1));
        assertTrue(summaryList.getTrainingsSessions().contains(trainingsSession2));
        assertEquals(trainingsSession1.getDurationInMinute() + trainingsSession2.getDurationInMinute(), summaryList.getTotalDurationInMinutes());
        assertEquals(trainingsSession1.getBurnedCalories() + trainingsSession2.getBurnedCalories(), summaryList.getTotalCaloriesBurned());
    }

}

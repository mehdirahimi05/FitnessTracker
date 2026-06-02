package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.*;
import de.fherfurt.FitnessTrackerSystem.repositories.TrainingsSessionRepository;
import de.fherfurt.FitnessTrackerSystem.services.utils.TrainingsSessionFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link TrainingsSessionService} class
 *
 * @author Mehdi Rahimi
 */
public class TrainingsSessionServiceTest {
    private TrainingsSessionRepository trainingsSessionRepository;
    private TrainingsSessionService trainingsSessionService;

    private User mehdi;
    private User ammar;

    @BeforeEach
    void setUp() {
        trainingsSessionRepository = new TrainingsSessionRepository();
        trainingsSessionService = new TrainingsSessionService(trainingsSessionRepository);

        mehdi = Constants.getFirstUser();
        ammar = Constants.getSecondUser();
    }

    /**
     * gets all trainingsSession
     */
    @Test
    @DisplayName("getAllTrainingsSessions: Success")
    void testGetAllTrainingsSessionsSuccess() {
        // Arrange
        TrainingsSession trainingsSession1 = Constants.getFirstTrainingsSession();
        TrainingsSession trainingsSession2 = Constants.getSecondTrainingsSession();

        trainingsSessionRepository.createTrainingsSession(trainingsSession1);
        trainingsSessionRepository.createTrainingsSession(trainingsSession2);

        // Act
        List<TrainingsSession> trainingsSessionList = trainingsSessionService.getAllTrainingsSessions();

        // Assert
        assertEquals(2, trainingsSessionList.size());
        assertTrue(trainingsSessionList.contains(trainingsSession1));
        assertTrue(trainingsSessionList.contains(trainingsSession2));
    }

    /**
     * get TrainingsSession  by id
     */
    @Test
    @DisplayName("get TrainingsSession by Id: success")
    void testGetTrainingsSessionByIdSuccess() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();
        int trainingsSessionId = Constants.FIRST_TRAININGS_SESSION_ID;
        trainingsSessionRepository.createTrainingsSession(trainingsSession);

        // Act
        Optional<TrainingsSession> trainingsSessionList = trainingsSessionService.getTrainingsSessionById(trainingsSessionId);

        // Assert
        assertTrue(trainingsSessionList.isPresent());
        assertEquals(trainingsSession, trainingsSessionList.get());
    }

    /**
     * verifies that false is returned when the TrainingsSessionId does not exists
     */
    @Test
    @DisplayName("checkIsOwnTrainingsSession: TrainingsSessionId does not exists")
    void testCheckIsOwnTrainingsSessionNotFound() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();
        trainingsSessionRepository.createTrainingsSession(trainingsSession);

        // Act
        boolean trainingsSessionList = trainingsSessionService.checkIsOwnTrainingsSession(5);

        // Assert
        assertFalse(trainingsSessionList);
    }

    /**
     * verifies that the TrainingsSessionId exists
     */
    @Test
    @DisplayName("checkIsOwnTrainingsSession : TrainingsSessionId does  exists")
    void testCheckIsOwnTrainingsSessionSuccess() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();
        trainingsSessionRepository.createTrainingsSession(trainingsSession);

        // Act
        boolean trainingsSessionList = trainingsSessionService.checkIsOwnTrainingsSession(trainingsSession.getTrainingsSessionId());

        // Assert
        assertTrue(trainingsSessionList);
    }

    /**
     * added TrainingsSession
     */
    @Test
    @DisplayName("TrainingsSession: success")
    void testAddTrainingsSessionSuccess() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();
        int expectedSizeOfTrainingsSession = 1;

        // Act
        trainingsSessionService.addTrainingsSession(trainingsSession);
        int actualSizeOfTrainingsSession = trainingsSessionRepository.getTrainingsSessionList().size();

        // Assert
        assertEquals(expectedSizeOfTrainingsSession, actualSizeOfTrainingsSession);
    }

    /**
     * updates trainingsSession
     */
    @Test
    @DisplayName("update TrainingsSession: Success")
    void testUpdateTrainingsSessionSuccess() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();
        int trainingsSessionId = Constants.FIRST_TRAININGS_SESSION_ID;
        User user = Constants.getSecondUser();
        ActivityType activityType = Constants.getSecondActivityType();
        WorkoutPlan workoutPlan = Constants.getSecondWorkoutPlan();

        trainingsSessionRepository.createTrainingsSession(trainingsSession);

        TrainingsSession updatedTrainingsSession = new TrainingsSession(
                trainingsSessionId,
                user,
                Constants.SECOND_DATE,
                Constants.FIRST_DURATION_IN_MINUTE,
                Constants.FIRST_BURNED_CALORIES,
                activityType,
                Constants.FIRST_DIFFICULTY,
                workoutPlan

        );

        // Act
        trainingsSessionService.updateTrainingsSession(updatedTrainingsSession);
        Optional<TrainingsSession> findTrainingsSessionList = trainingsSessionRepository.getTrainingsSessionById(trainingsSessionId);

        // Assert
        assertEquals(trainingsSessionId, findTrainingsSessionList.get().getTrainingsSessionId());
        assertEquals(user, findTrainingsSessionList.get().getUser());
        assertEquals(Constants.SECOND_DATE, findTrainingsSessionList.get().getDate());
        assertEquals(Constants.FIRST_DURATION_IN_MINUTE, findTrainingsSessionList.get().getDurationInMinute());
        assertEquals(Constants.FIRST_BURNED_CALORIES, findTrainingsSessionList.get().getBurnedCalories());
        assertEquals(activityType, findTrainingsSessionList.get().getActivityType());
        assertEquals(Constants.FIRST_DIFFICULTY, findTrainingsSessionList.get().getDifficulty());
        assertEquals(workoutPlan, findTrainingsSessionList.get().getWorkoutPlan());
    }

    /**
     * delete trainingsSession by id
     */
    @Test
    @DisplayName("delete trainingsSession by id: success")
    void testDeleteTrainingsSessionByIdSuccess() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();
        trainingsSessionRepository.createTrainingsSession(trainingsSession);
        int trainingsSessionId = Constants.FIRST_TRAININGS_SESSION_ID;
        int expectedSizeOfTrainingsSessionList = 0;

        // Act
        trainingsSessionService.deleteTrainingsSessionById(trainingsSessionId);
        int actualSizeOfTrainingsSessionList = trainingsSessionRepository.getTrainingsSessionList().size();

        // Assert
        assertEquals(expectedSizeOfTrainingsSessionList, actualSizeOfTrainingsSessionList);

    }


    /**
     * verifies that the most active user by total time was found successfully
     */
    @Test
    void testGetMostActiveUserByAmountOfTrainingsSessionsSuccess() {
        // Arrange
        TrainingsSession trainingsSession1 = Constants.getFirstTrainingsSession(mehdi);
        TrainingsSession trainingsSession2 = Constants.getFirstTrainingsSession(mehdi);
        TrainingsSession trainingsSession3 = Constants.getSecondTrainingsSession(ammar);

        trainingsSessionRepository.createTrainingsSession(trainingsSession1);
        trainingsSessionRepository.createTrainingsSession(trainingsSession2);
        trainingsSessionRepository.createTrainingsSession(trainingsSession3);

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
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();
        trainingsSessionRepository.createTrainingsSession(trainingsSession);

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
    @DisplayName("Filter List: Success")
    void testFilterTrainingsSessionSuccess() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession(mehdi);
        trainingsSessionRepository.createTrainingsSession(trainingsSession);

        // Act
        List<TrainingsSession> trainingsSessionFilter1 = trainingsSessionService.filterTrainingsSession(TrainingsSessionFilter.builder().withStartDate(Constants.FIRST_DATE).build());
        List<TrainingsSession> trainingsSessionFilter2 = trainingsSessionService.filterTrainingsSession(TrainingsSessionFilter.builder().withEndDate(LocalDate.of(2026, 3, 31)).build());
        List<TrainingsSession> trainingsSessionFilter4 = trainingsSessionService.filterTrainingsSession(TrainingsSessionFilter.builder().withUser(mehdi).build());
        List<TrainingsSession> trainingsSessionFilter5 = trainingsSessionService.filterTrainingsSession(TrainingsSessionFilter.builder().withMinBurnedCalories(Constants.FIRST_BURNED_CALORIES).build());
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
        // Arrange
        User user = Constants.getFirstUser();

        // Act
        assertThrows(IllegalStateException.class, () -> {
            trainingsSessionService.getTrainingSessionStreak(user);
        });
    }

    /**
     * verifies that the streak is 1
     */
    @Test
    void testGetTrainingsSessionStreakOne() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession(mehdi);
        trainingsSessionRepository.createTrainingsSession(trainingsSession);

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
        TrainingsSession trainingsSession1 = Constants.getFirstTrainingsSession(mehdi);
        TrainingsSession trainingsSession2 = Constants.getSecondTrainingsSession(mehdi);
        trainingsSessionRepository.createTrainingsSession(trainingsSession1);
        trainingsSessionRepository.createTrainingsSession(trainingsSession2);

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
        TrainingsSession trainingsSession1 = Constants.getFirstTrainingsSession(mehdi);
        TrainingsSession trainingsSession2 = Constants.getSecondTrainingsSession(mehdi);
        TrainingsSession trainingsSession3 = Constants.getFirstTrainingsSession(mehdi);
        trainingsSession3.setDate(LocalDate.of(2026, 3, 25));
        trainingsSessionRepository.createTrainingsSession(trainingsSession1);
        trainingsSessionRepository.createTrainingsSession(trainingsSession2);
        trainingsSessionRepository.createTrainingsSession(trainingsSession3);

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
        // Arrange
        User user = Constants.getFirstUser();
        LocalDate date = Constants.FIRST_DATE;

        // Act
        assertThrows(IllegalStateException.class, () -> {
            trainingsSessionService.getDailyTrainingsSessionSummary(user, date);
        });
    }

    /**
     * verifies that DailyTrainingsSessionSummary was summed successfully
     */
    @Test
    void testGetDailyTrainingsSessionSummarySuccess() {
        // Arrange
        User user = Constants.getFirstUser();
        LocalDate date = Constants.FIRST_DATE;
        TrainingsSession trainingsSession1 = Constants.getFirstTrainingsSession(user);
        TrainingsSession trainingsSession2 = Constants.getSecondTrainingsSession(user);
        trainingsSession2.setDate(Constants.FIRST_DATE); // same date like trainingsSession1


        // Act
        trainingsSessionRepository.createTrainingsSession(trainingsSession1);
        trainingsSessionRepository.createTrainingsSession(trainingsSession2);

        TrainingsSessionSummary summaryList = trainingsSessionService.getDailyTrainingsSessionSummary(user, date);

        // Assert
        assertTrue(summaryList.getTrainingsSessions().contains(trainingsSession1));
        assertTrue(summaryList.getTrainingsSessions().contains(trainingsSession2));
        assertEquals(Constants.FIRST_DURATION_IN_MINUTE + Constants.SECOND_DURATION_IN_MINUTE, summaryList.getTotalDurationInMinutes());
        assertEquals(Constants.FIRST_BURNED_CALORIES + Constants.SECOND_BURNED_CALORIES, summaryList.getTotalCaloriesBurned());
    }

}

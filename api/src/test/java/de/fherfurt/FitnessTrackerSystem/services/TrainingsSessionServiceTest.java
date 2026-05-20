package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.repositories.TrainingsSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link TrainingsSessionService} class
 * @author Mehdi Rahimi
 */
public class TrainingsSessionServiceTest {
    private TrainingsSessionRepository trainingsSessionRepository;
    private TrainingsSessionService trainingsSessionService;

    @BeforeEach
    void setUp(){
        trainingsSessionRepository = new TrainingsSessionRepository();
        trainingsSessionService = new TrainingsSessionService(trainingsSessionRepository);
    }

    /**
     * gets all trainingsSession
     */
    @Test
    @DisplayName("getAllTrainingsSessions: Success")
    void testGetAllTrainingsSessionsSuccess(){
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
    void testGetTrainingsSessionByIdSuccess(){
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
    void testCheckIsOwnTrainingsSessionNotFound(){
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
    void testCheckIsOwnTrainingsSessionSuccess(){
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
    void testAddTrainingsSessionSuccess(){
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
    @DisplayName("update TrainingsSession : Success")
    void testUpdateTrainingsSessionSuccess(){
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();
        int trainingsSessionId = Constants.FIRST_TRAININGS_SESSION_ID;
        User user = Constants.getSecondUser();
        trainingsSessionRepository.createTrainingsSession(trainingsSession);

        TrainingsSession updatedTrainingsSession = new TrainingsSession(1, user, Constants.SECOND_DATE, 45, 20, 300, ActivityType.SWIMMING);

        // Act
        trainingsSessionService.updateTrainingsSession(updatedTrainingsSession);
        Optional<TrainingsSession> findTrainingsSessionList = trainingsSessionRepository.getTrainingsSessionById(trainingsSessionId);

        // Assert
        assertEquals(user, findTrainingsSessionList.get().getUser());
        assertEquals(Constants.SECOND_DATE, findTrainingsSessionList.get().getDate());
        assertEquals(45, findTrainingsSessionList.get().getDurationInMinute());
        assertEquals(20, findTrainingsSessionList.get().getDistanceInKm());
        assertEquals(300, findTrainingsSessionList.get().getBurnedCalories());
        assertEquals(ActivityType.SWIMMING, findTrainingsSessionList.get().getActivityType());
    }

    /**
     * delete trainingsSession by id
     */
    @Test
    @DisplayName("delete trainingsSession by id: success")
    void testDeleteTrainingsSessionByIdSuccess(){
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

}

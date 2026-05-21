package de.fherfurt.FitnessTrackerSystem.repositories;


import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TrainingsSessionRepositoryTest {
    private TrainingsSessionRepository trainingsSessionRepository;


    @BeforeEach
    void setup() {
        trainingsSessionRepository = new TrainingsSessionRepository();
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("create trainingsSession : Ignore null input and maintain empty list")
    void testCreateTrainingsSessionNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            trainingsSessionRepository.createTrainingsSession(null);
        });
    }

    /**
     * verifies that a trainingsSession was added to the list
     */
    @Test
    @DisplayName("create trainingsSession: success")
    void testCreateTrainingsSessionSuccess() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();
        int expectedSizeOfTrainingsSessionList = 1;

        // Act
        trainingsSessionRepository.createTrainingsSession(trainingsSession);
        int actualSizeOfTrainingsSessionList = trainingsSessionRepository.getTrainingsSessionList().size();

        // Assert
        assertEquals(expectedSizeOfTrainingsSessionList, actualSizeOfTrainingsSessionList);
    }

    /**
     * verifies that there is no trainingsSessions in the list
     */
    @Test
    @DisplayName("Get null trainingsSessions")
    void testGetNullTrainingsSession() {
        // Act
        List<TrainingsSession> trainingsSessionList = trainingsSessionRepository.getAllTrainingsSessions();

        // Assert
        assertEquals(0, trainingsSessionList.size());
    }

    /**
     * verifies that there is 1 trainingsSessions in the list
     */
    @Test
    @DisplayName("Get one trainingsSessions")
    void testGetOneTrainingsSession() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();

        // Act
        trainingsSessionRepository.createTrainingsSession(trainingsSession);

        List<TrainingsSession> trainingsSessionList = trainingsSessionRepository.getAllTrainingsSessions();

        // Assert
        assertEquals(1, trainingsSessionList.size());
        assertTrue(trainingsSessionList.contains(trainingsSession));

    }

    /**
     * verifies that there is multiple trainingsSessions
     */
    @Test
    @DisplayName("Get all trainingsSessions")
    void testGetAllTrainingsSession() {
        // Arrange
        TrainingsSession trainingsSession1 = Constants.getFirstTrainingsSession();
        TrainingsSession trainingsSession2 = Constants.getSecondTrainingsSession();

        // Act
        trainingsSessionRepository.createTrainingsSession(trainingsSession1);
        trainingsSessionRepository.createTrainingsSession(trainingsSession2);

        List<TrainingsSession> trainingsSessionList = trainingsSessionRepository.getAllTrainingsSessions();

        // Assert
        assertEquals(2, trainingsSessionList.size());
        assertTrue(trainingsSessionList.contains(trainingsSession1));
        assertTrue(trainingsSessionList.contains(trainingsSession2));
    }

    /**
     * verifies that there is no trainingsSession id
     */
    @Test
    @DisplayName("Get trainingsSession by Id: empty")
    void testGetTrainingsSessionByIdIsEmpty() {
        // Act
        Optional<TrainingsSession> trainingsSessionList = trainingsSessionRepository.getTrainingsSessionById(3);

        // Assert
        assertTrue(trainingsSessionList.isEmpty());
    }

    /**
     * verifies that there is a trainingsSession id
     */
    @Test
    @DisplayName("Get trainingsSession by Id : present")
    void testGetTrainingsSessionByIdIsPresent() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();
        int trainingsSessionId = Constants.FIRST_TRAININGS_SESSION_ID;

        // Act
        trainingsSessionRepository.createTrainingsSession(trainingsSession);
        Optional<TrainingsSession> trainingsSessionList = trainingsSessionRepository.getTrainingsSessionById(trainingsSessionId);

        // Assert
        assertTrue(trainingsSessionList.isPresent());
    }

    /**
     * gets the trainingsSession id
     */
    @Test
    @DisplayName("Get trainingsSession by Id")
    void testGetTrainingsSessionById() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();
        int trainingsSessionId = Constants.FIRST_TRAININGS_SESSION_ID;

        // Act
        trainingsSessionRepository.createTrainingsSession(trainingsSession);
        Optional<TrainingsSession> trainingsSessionList = trainingsSessionRepository.getTrainingsSessionById(trainingsSessionId);

        // Assert
        assertEquals(trainingsSession, trainingsSessionList.get());
    }

    /**
     * verifies that the IllegalStateException was thrown when user list is empty
     */
    @Test
    @DisplayName("Update trainingsSession: ignore empty list and maintain the list")
    void testUpdateTrainingsSessionEmpty() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            trainingsSessionRepository.updateTrainingsSession(trainingsSession);
        });
    }

    /**
     * updates trainingsSession
     */
    @Test
    @DisplayName("update TrainingsSession : Success")
    void testUpdateTrainingsSessionSuccess() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();
        int trainingsSessionId = Constants.FIRST_TRAININGS_SESSION_ID;
        User user = Constants.getSecondUser();
        trainingsSessionRepository.createTrainingsSession(trainingsSession);

        TrainingsSession updatedTrainingsSession = new TrainingsSession(1, user, Constants.SECOND_DATE, 45, 20, 300, ActivityType.SWIMMING);

        // Act
        trainingsSessionRepository.updateTrainingsSession(updatedTrainingsSession);
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
     * verifies that the IllegalStateException was thrown when user list is empty
     */
    @Test
    @DisplayName("delete TrainingsSession by id: ignore empty list and maintain the list")
    void testDeleteTrainingsSessionByIdEmpty() {
        // Arrange
        int trainingsSessionId = Constants.FIRST_TRAININGS_SESSION_ID;

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            trainingsSessionRepository.deleteTrainingsSessionById(trainingsSessionId);
        });
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
        trainingsSessionRepository.deleteTrainingsSessionById(trainingsSessionId);
        int actualSizeOfTrainingsSessionList = trainingsSessionRepository.getTrainingsSessionList().size();

        // Assert
        assertEquals(expectedSizeOfTrainingsSessionList, actualSizeOfTrainingsSessionList);

    }
}


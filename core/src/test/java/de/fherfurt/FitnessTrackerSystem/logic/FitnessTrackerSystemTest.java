package de.fherfurt.FitnessTrackerSystem.logic;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.logic.filter.TrainingsSessionFilter;
import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for the {@link FitnessTrackerSystem} class.
 * This class verifies the core logic including session management,
 * statistical calculations, and filtering capabilities.
 * @author Mehdi Rahimi
 */
class FitnessTrackerSystemTest {

    private FitnessTrackerSystem fitnessTrackerSystem;
    private UserDetails mehdi;
    private UserDetails ammar;

    @BeforeEach
    void setUp(){
        fitnessTrackerSystem = new FitnessTrackerSystem();

        mehdi = Constants.getFirstUser();
        ammar = Constants.getSecondUser();

    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("Add Session: Ignore null input and maintain empty list")
    void testAddTrainingsSessionNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            fitnessTrackerSystem.addTrainingsSession(null);
        });

    }

    /**
     * Verifies that a valid training session is correctly added to the system.
     * The internal list must contain the session, and its size must increase to 1.
     */
    @Test
    @DisplayName("Add Session: Successfully store a valid session in the list")
    void testAddTrainingsSessionSuccess(){
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession(mehdi);
        int expectedSizeOfTrainingsSessionList = 1;

        // Act
        fitnessTrackerSystem.addTrainingsSession(trainingsSession);

        // Assert
        int actualSizeOfTrainingsSessionList = fitnessTrackerSystem.getTrainingsSessionList().size();
        assertEquals(expectedSizeOfTrainingsSessionList, actualSizeOfTrainingsSessionList);
        assertTrue(fitnessTrackerSystem.getTrainingsSessionList().contains(trainingsSession));
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("Set Session: null")
    void testSetTrainingsSessionNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            fitnessTrackerSystem.setTrainingsSession(null, 0, 0.0f);
        });
    }

    /**
     * Verifies that the IllegalStateException was thrown when null is provided
     */
    @Test
    @DisplayName("Set Session: not exist")
    void testSetTrainingsSessionNotExist(){
        assertThrows(IllegalStateException.class, () -> {
            fitnessTrackerSystem.setTrainingsSession(Constants.getFirstTrainingsSession(), Constants.FIRST_DURATION_IN_MINUTE, Constants.FIRST_DISTANCE_IN_KM);
        });
    }

    /**
     * Ensures that attempting to update a session succeeded
     * if there is a session in the list
     * *Values are retrieved from {@link Constants} to avoid magic numbers.
     */
    @Test
    @DisplayName("Set Session: Successfully updated a valid session in the list")
    void testSetTrainingsSessionSuccess() {
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession(mehdi);
        fitnessTrackerSystem.addTrainingsSession(trainingsSession);

        // Act
        int newDurationInMinute = 30;
        float newDistanceInKm = 5.0f;
        fitnessTrackerSystem.setTrainingsSession(trainingsSession, newDurationInMinute, newDistanceInKm);

        // Assert
        assertEquals(newDurationInMinute, trainingsSession.getDurationInMinute());
        assertEquals(newDistanceInKm, trainingsSession.getDistanceInKm());
        }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("Remove Session: Null")
    void testRemoveTrainingsSessionNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            fitnessTrackerSystem.removeTrainingsSession(null);
        });
    }

    /**
     * Verifies that the  IllegalStateException was thrown when null is provided
     */
    @Test
    @DisplayName("Remove Session: not exist")
    void testRemoveTrainingsSessionNotExist(){

        assertThrows(IllegalStateException.class, () -> {
            fitnessTrackerSystem.removeTrainingsSession(Constants.getFirstTrainingsSession());
        });
    }

    /**
     * Ensures that attempting to remove a session which exists success
     */
    @Test
    @DisplayName("Remove Session: successeded to remove the Session")
    void testRemoveTrainingsSessionSuccess(){
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession(mehdi);
        fitnessTrackerSystem.addTrainingsSession(trainingsSession);

        // Act
        fitnessTrackerSystem.removeTrainingsSession(trainingsSession);

        // Assert
        int actualSizeOfTrainingsSessionList = fitnessTrackerSystem.getTrainingsSessionList().size();

        assertEquals(0, actualSizeOfTrainingsSessionList);
    }

    /**
     * Verifies that the total training time is 0 if null parameters are provided.
     * This indirectly tests the null-handling of the private filter method.
     */
    @Test
    @DisplayName("Total Time: Return 0 on null parameters")
    void testCalculateTotalTrainingTimeNull() {
        // Act
        int totalTimeInMinutes = fitnessTrackerSystem.calculateTotalTrainingTimeInMinutes(null, null, null);

        // Assert
        assertEquals(0, totalTimeInMinutes);
    }

    /**
     * verifies that the total training time is correctly calculated
     * for a user in a specific time range
     */
    @Test
    @DisplayName("Total Time: Successfully calculated")
    void testCalculateTotalTrainingTimeSuccess(){
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession(mehdi);
        UserDetails user = Constants.getFirstUser();
        LocalDate startDate = LocalDate.of(2026, 3, 20);
        LocalDate endDate = LocalDate.of(2026, 03, 31);

        fitnessTrackerSystem.addTrainingsSession(trainingsSession);
        // Act
        int totalTimeInMinutes = fitnessTrackerSystem.calculateTotalTrainingTimeInMinutes(user, startDate, endDate);

        // Assert
        assertEquals(Constants.FIRST_DURATION_IN_MINUTE, totalTimeInMinutes);
    }

    /**
     * Verifies that the total distance is 0 if null parameters are provided.
     * This indirectly tests the null-handling of the private filter method.
     */
    @Test
    @DisplayName("Total Time: Return 0 on null parameters")
    void testCalculateTotalDistanceInKmNull(){
        // Act
        float totalDistanceInKm = fitnessTrackerSystem.calculateTotalDistanceInKm(ammar, null, null);

        // Assert
        assertEquals(0, totalDistanceInKm);
    }


    /**
     * verifies that the total Distance is correctly calculated
     * for a user in a specific time range
     */
    @Test
    @DisplayName("Total Distance: Successfully calculated")
    void testCalculateTotalDistanceInKmSuccess(){
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession(mehdi);
        LocalDate startDate = LocalDate.of(2026, 3, 20);
        LocalDate endDate = LocalDate.of(2026, 03, 31);

        fitnessTrackerSystem.addTrainingsSession(trainingsSession);

        // Act
        float totalDistanceInKm = fitnessTrackerSystem.calculateTotalDistanceInKm(mehdi, startDate, endDate);

        // Assert
        assertEquals(Constants.FIRST_DISTANCE_IN_KM, totalDistanceInKm);
    }

    /**
     * verifies that the average speed is 0 if null parameters are provided
     */
    @Test
    @DisplayName("Average Speed:Return 0 on null parameters")
    void testCalculateAverageSpeedInKmHNull(){
        assertEquals(0, fitnessTrackerSystem.calculateAverageSpeedInKmH(null, null, null));
    }

    /**
     * verifies that the average speed is 0 if time is 0
     */
    @Test
    @DisplayName("Average Speed:Return 0 on null time")
    void testCalculateAverageSpeedInKmHTimeIsNull(){
        // Arrange
        UserDetails user = Constants.getFirstUser();
        LocalDate startDate = LocalDate.of(2026, 3, 20);
        LocalDate endDate = LocalDate.of(2026, 03, 31);

        // Act
        float totalTimeInMinutes = fitnessTrackerSystem.calculateAverageSpeedInKmH(user, startDate, endDate);

        // Assert
        assertEquals(0, totalTimeInMinutes);
    }

    /**
     * verifies that the average speed is 0 if time is 0
     */
    @Test
    @DisplayName("Average Speed: calculated successfully")
    void testCalculateAverageSpeedInKmHTimeIsSuccess(){
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession(mehdi);
        LocalDate startDate = LocalDate.of(2026, 3, 20);
        LocalDate endDate = LocalDate.of(2026, 03, 31);

        fitnessTrackerSystem.addTrainingsSession(trainingsSession);

        // Act
        float totalTimeInHours = Constants.FIRST_DURATION_IN_MINUTE  / 60.0f;
        float expectedAverageSpeedInKmH = Constants.FIRST_DISTANCE_IN_KM / totalTimeInHours;

        float actualAverageSpeedInKmH = fitnessTrackerSystem.calculateAverageSpeedInKmH(mehdi, startDate, endDate);

        // Assert
        assertEquals(expectedAverageSpeedInKmH, actualAverageSpeedInKmH);
    }

    /**
     * verifies that the most active user by total time is null
     * if null parameters are provided
     */
    @Test
    @DisplayName("Most active User by Time: null")
    void testFindMostActiveUserByTotalTimeNull(){
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession(mehdi);
        fitnessTrackerSystem.addTrainingsSession(trainingsSession);

        assertEquals(null, fitnessTrackerSystem.findMostActiveUserByTotalTime(null, null));
    }

    /**
     * verifies that the most active user by total distance is null
     * if null parameters are provided
     */
    @Test
    @DisplayName("Most active User by Distance: null")
    void testFindMostActiveUserByTotalDistanceNull(){
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession(mehdi);
        fitnessTrackerSystem.addTrainingsSession(trainingsSession);

        assertEquals(null, fitnessTrackerSystem.findMostActiveUserByTotalDistance(null, null));
    }

    /**
     * verifies that the most active user by total time was found successfully
     */
    @Test
    @DisplayName("Most active user by Time: successfully")
    void testFindMostActiveUserByTotalTimeSuccess(){
        // Arrange
        TrainingsSession trainingsSession1 = Constants.getFirstTrainingsSession(mehdi);
        TrainingsSession trainingsSession2 = Constants.getSecondTrainingsSession(ammar);

        fitnessTrackerSystem.addTrainingsSession(trainingsSession1);
        fitnessTrackerSystem.addTrainingsSession(trainingsSession2);

        // Act
        UserDetails findWinner = fitnessTrackerSystem.findMostActiveUserByTotalTime(LocalDate.of(2026, 3, 20), LocalDate.of(2026, 3, 31));

        // Assert
        assertEquals(mehdi, findWinner);

    }

    /**
     * verifies that the most active user by total distance was found successfully
     */
    @Test
    @DisplayName("Most active user by Distance: successfully")
    void testFindMostActiveUserByTotalDistanceSuccess(){
        // Arrange
        TrainingsSession trainingsSession1 = Constants.getFirstTrainingsSession(mehdi);
        TrainingsSession trainingsSession2 = Constants.getSecondTrainingsSession(ammar);

        fitnessTrackerSystem.addTrainingsSession(trainingsSession1);
        fitnessTrackerSystem.addTrainingsSession(trainingsSession2);

        // Act
        UserDetails findWinner = fitnessTrackerSystem.findMostActiveUserByTotalDistance(LocalDate.of(2026, 3, 20), LocalDate.of(2026, 3, 31));

        // Assert
        assertEquals(ammar, findWinner);
    }

    /**
     * verifies that the list filter is null if null parameters are provides
     */
    @Test
    @DisplayName("Filter List: null")
    void testFilterTrainingsSessionNUll(){
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();
        fitnessTrackerSystem.addTrainingsSession(trainingsSession);

        // Act
        List<TrainingsSession> trainingsSessionFilter1 =  fitnessTrackerSystem.filterTrainingsSession(TrainingsSessionFilter.builder().withStartDate(null).build());
        List<TrainingsSession> trainingsSessionFilter2 =  fitnessTrackerSystem.filterTrainingsSession(TrainingsSessionFilter.builder().withEndDate(null).build());
        List<TrainingsSession> trainingsSessionFilter3 =  fitnessTrackerSystem.filterTrainingsSession(TrainingsSessionFilter.builder().withActivityType(null).build());
        List<TrainingsSession> trainingsSessionFilter4 =  fitnessTrackerSystem.filterTrainingsSession(TrainingsSessionFilter.builder().withUser(null).build());
        List<TrainingsSession> trainingsSessionFilter5 =  fitnessTrackerSystem.filterTrainingsSession(TrainingsSessionFilter.builder().withMinBurnedCalories(0).build());

        // Assert
        assertEquals(1, trainingsSessionFilter1.size());
        assertEquals(1, trainingsSessionFilter2.size());
        assertEquals(1, trainingsSessionFilter3.size());
        assertEquals(1, trainingsSessionFilter4.size());
        assertEquals(1, trainingsSessionFilter5.size());

    }

    /**
     * verifies that the list ist filtert successfully
     */
    @Test
    @DisplayName("Filter List: Success")
    void testFilterTrainingsSessionSuccess(){
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession(Constants.getFirstUser());
        fitnessTrackerSystem.addTrainingsSession(trainingsSession);

        // Act
        List<TrainingsSession> trainingsSessionFilter1 =  fitnessTrackerSystem.filterTrainingsSession(TrainingsSessionFilter.builder().withStartDate(Constants.FIRST_DATE).build());
        List<TrainingsSession> trainingsSessionFilter2 =  fitnessTrackerSystem.filterTrainingsSession(TrainingsSessionFilter.builder().withEndDate(LocalDate.of(2026, 3, 31)).build());
        List<TrainingsSession> trainingsSessionFilter3 =  fitnessTrackerSystem.filterTrainingsSession(TrainingsSessionFilter.builder().withActivityType(Constants.FIRST_ACTIVITY_TYPE).build());
        List<TrainingsSession> trainingsSessionFilter4 =  fitnessTrackerSystem.filterTrainingsSession(TrainingsSessionFilter.builder().withUser(Constants.getFirstUser()).build());
        List<TrainingsSession> trainingsSessionFilter5 =  fitnessTrackerSystem.filterTrainingsSession(TrainingsSessionFilter.builder().withMinBurnedCalories(Constants.FIRST_BURNED_CALORIES).build());
        List<TrainingsSession> trainingsSessionFilter6 =  fitnessTrackerSystem.filterTrainingsSession(TrainingsSessionFilter.builder().withMaxBurnedCalories(1200).build());

        // Assert
        assertEquals(1, trainingsSessionFilter1.size());
        assertEquals(1, trainingsSessionFilter2.size());
        assertEquals(1, trainingsSessionFilter3.size());
        assertEquals(1, trainingsSessionFilter4.size());
        assertEquals(1, trainingsSessionFilter5.size());
        assertEquals(1, trainingsSessionFilter6.size());
    }

}

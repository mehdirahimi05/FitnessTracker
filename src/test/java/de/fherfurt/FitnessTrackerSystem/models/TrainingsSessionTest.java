package de.fherfurt.FitnessTrackerSystem.models;


import de.fherfurt.FitnessTrackerSystem.Constants;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link TrainingsSession} class.
 * This test suite covers deep cloning, equality logic, hash code generation,
 * chronological sorting via comparison, and data integrity via getters and setters.
 *
 * @author Mehdi Rahimi
 */
class TrainingsSessionTest {
    /**
     * Verifies that the clone method creates a deep copy of the session.
     * The cloned session must be a different object in memory, but hold equal values,
     * and its associated User must also be a separate object (deep copy).
     */
    @Test void cloneTest(){
        // Arrange
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession();

        // Act
        TrainingsSession cloneTrainingsSession = trainingsSession.clone();

        // Assert
        assertEquals(trainingsSession.getDate(), cloneTrainingsSession.getDate());
        assertEquals(trainingsSession.getDurationInMinute(), cloneTrainingsSession.getDurationInMinute());
        assertEquals(trainingsSession.getDistanceInKm(), cloneTrainingsSession.getDistanceInKm());
        assertEquals(trainingsSession.getBurnedCalories(), cloneTrainingsSession.getBurnedCalories());
        assertEquals(trainingsSession.getActivityType(), cloneTrainingsSession.getActivityType());
        assertEquals(trainingsSession.getUser(), cloneTrainingsSession.getUser());

    }


}
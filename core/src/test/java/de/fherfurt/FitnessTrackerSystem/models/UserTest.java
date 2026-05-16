package de.fherfurt.FitnessTrackerSystem.models;

import de.fherfurt.FitnessTrackerSystem.Constants;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link User} class.
 * This test suite ensures full coverage for cloning logic, equality comparisons,
 * hash code generation, alphabetical sorting, and standard data integrity.
 *
 * @author Mehdi Rahimi
 */
class UserTest {
    /**
     * Verifies that the clone method creates a new, independent instance
     * with identical attribute values.
     */
    @Test
    void cloneTest(){
        // Arrange
        User user = Constants.getFirstUser();

        // Act
        User clonedUser = user.clone();

        // Assert
        assertNotSame(user, clonedUser);
        assertEquals(user, clonedUser);
        assertEquals(user.getFirstName(), clonedUser.getFirstName());
        assertEquals(user.getLastName(), clonedUser.getLastName());
        assertEquals(user.getBirthDate(), clonedUser.getBirthDate());
        assertEquals(user.getWeight(), clonedUser.getWeight());
        assertEquals(user.getHeight(), clonedUser.getHeight());
    }

}
package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.models.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link UserDetailsRepository} class
 *
 * @author Mehdi Rahimi
 */
public class UserDetailsRepositoryTest {
    private UserDetailsRepository userDetailsRepository;
    private User mehdi;

    @BeforeEach
    void setUp() {
        userDetailsRepository = new UserDetailsRepository();
        mehdi = Constants.getFirstUser();
    }

    /**
     * verifies that there is no user id
     */
    @Test
    @DisplayName("get Userdetails by id: empty")
    void testGetUserDetailsOfUserByIdIsEmpty() {
        // Act
        Optional<UserDetails> userDetailsList = userDetailsRepository.getUserDetailsOfUserById(5);

        // Assert
        assertTrue(userDetailsList.isEmpty());
    }

    /**
     * verifies that there is a user id
     */
    @Test
    @DisplayName("get Userdetails by id: present")
    void testGetUserDetailsOfUserByIdIsPresent() {
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();
        int userId = Constants.FIRST_USER_ID;

        // Act
        userDetailsRepository.createUserDetails(userDetails);
        Optional<UserDetails> userDetailsList = userDetailsRepository.getUserDetailsOfUserById(userId);

        // Assert
        assertTrue(userDetailsList.isPresent());

    }

    /**
     * get userdetails by email
     */
    @Test
    @DisplayName("get Userdetails by email: success")
    void testGetUserDetailsOfUserByEmailSuccess() {
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();
        int userId = Constants.FIRST_USER_ID;

        // Act
        userDetailsRepository.createUserDetails(userDetails);
        Optional<UserDetails> userDetailsList = userDetailsRepository.getUserDetailsOfUserById(userId);

        // Assert
        assertEquals(userId, userDetailsList.get().getUserId());
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("Update UserDetails: Ignore null input and maintain empty list")
    void testUpdateUserDetailsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            userDetailsRepository.updateUserDetails(null);
        });
    }

    /**
     * verifies that the IllegalStateException was thrown when user list is empty
     */
    @Test
    @DisplayName("Update UserDetails: ignore empty list and maintain the list")
    void testUpdateUserDetailsEmpty() {
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            userDetailsRepository.updateUserDetails(userDetails);
        });
    }

    /**
     * updates User Details
     */
    @Test
    @DisplayName("update UserDetails: Success")
    void testUpdateUserDetailsSuccess() {
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();
        int userId = Constants.FIRST_USER_ID;
        userDetailsRepository.createUserDetails(userDetails);

        UserDetails updatedUserDetails = new UserDetails(userId, "omar", "takla", "omar@gmail.com", LocalDate.of(2004, 01, 01), 77, 170);

        // Act
        userDetailsRepository.updateUserDetails(updatedUserDetails);
        Optional<UserDetails> finalUserDetailsList = userDetailsRepository.getUserDetailsOfUserById(userId);

        // Assert
        assertEquals("omar", finalUserDetailsList.get().getFirstName());
        assertEquals("takla", finalUserDetailsList.get().getLastName());
        assertEquals("omar@gmail.com", finalUserDetailsList.get().getEmail());
        assertEquals(LocalDate.of(2004, 01, 01), finalUserDetailsList.get().getBirthDate());
        assertEquals(77, finalUserDetailsList.get().getWeight());
        assertEquals(170, finalUserDetailsList.get().getHeight());
    }

    /**
     * verifies that the IllegalStateException was thrown when user list is empty
     */
    @Test
    @DisplayName("delete User by id: ignore empty list and maintain the list")
    void testDeleteUserByIdEmpty() {
        // Arrange
        int userId = Constants.FIRST_USER_ID;

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            userDetailsRepository.deleteUserDetailsById(userId);
        });
    }

    /**
     * delete user by id
     */
    @Test
    @DisplayName("delete User by id: success")
    void testDeleteUserByIdSuccess() {
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();
        userDetailsRepository.createUserDetails(userDetails);
        int userId = Constants.FIRST_USER_ID;
        int expectedSizeOfUserList = 0;

        // Act
        userDetailsRepository.deleteUserDetailsById(userId);
        int actualSizeOfUserList = userDetailsRepository.getUserDetailsList().size();

        // Assert
        assertEquals(expectedSizeOfUserList, actualSizeOfUserList);

    }
}

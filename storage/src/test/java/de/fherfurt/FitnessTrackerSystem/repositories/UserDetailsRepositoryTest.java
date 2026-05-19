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
 * @author Mehdi Rahimi
 */
public class UserDetailsRepositoryTest {
    private UserDetailsRepository userDetailsRepository ;
    private User mehdi;

    @BeforeEach
    void setUp(){
        userDetailsRepository = new UserDetailsRepository();
        mehdi = Constants.getFirstUser();
    }

    /**
     * verifies that there is no user email
     */
    @Test
    @DisplayName("get Userdetails by email: empty")
    void testGetUserDetailsOfUserByEmailIsEmpty(){
        // Act
        Optional<UserDetails> userDetailsList = userDetailsRepository.getUserDetailsOfUserByEmail("Saphia@gmail.com");

        // Assert
        assertTrue(userDetailsList.isEmpty());
    }

    /**
     * verifies that there is a user email
     */
    @Test
    @DisplayName("get Userdetails by email: present")
    void testGetUserDetailsOfUserByEmailIsPresent(){
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();
        String email = Constants.FIRST_USER_EMAIL;

        // Act
        userDetailsRepository.createUserDetails(userDetails);
        Optional<UserDetails> userDetailsList = userDetailsRepository.getUserDetailsOfUserByEmail(email);

        // Assert
        assertTrue(userDetailsList.isPresent());

    }

    /**
     * get userdetails by email
     */
    @Test
    @DisplayName("get Userdetails by email: success")
    void testGetUserDetailsOfUserByEmailSuccess(){
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();
        String email = Constants.FIRST_USER_EMAIL;

        // Act
        userDetailsRepository.createUserDetails(userDetails);
        Optional<UserDetails> userDetailsList = userDetailsRepository.getUserDetailsOfUserByEmail(email);

        // Assert
        assertEquals(email, userDetailsList.get().getEmail());
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("Update UserDetails: Ignore null input and maintain empty list")
    void testUpdateUserDetailsNull(){
        assertThrows(IllegalArgumentException.class, () ->{
            userDetailsRepository.updateUserDetails(null);
        });
    }

    /**
     * verifies that the IllegalStateException was thrown when user list is empty
     */
    @Test
    @DisplayName("Update UserDetails: ignore empty list and maintain the list")
    void testUpdateUserDetailsEmpty(){
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();

        // Assert
        assertThrows(IllegalStateException.class, () ->{
            userDetailsRepository.updateUserDetails(userDetails);
        });
    }

    /**
     * updates User Details
     */
    @Test
    @DisplayName("update UserDetails: Success")
    void testUpdateUserDetailsSuccess(){
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();
        String email = Constants.FIRST_USER_EMAIL;
        userDetailsRepository.createUserDetails(userDetails);

        UserDetails updatedUserDetails = new UserDetails("omar", "takla", "mehdi@icloud.com", LocalDate.of(2004, 01, 01), 77, 170);

        // Act
        userDetailsRepository.updateUserDetails(updatedUserDetails);
        Optional<UserDetails> finalUserList = userDetailsRepository.getUserDetailsOfUserByEmail(email);

        // Assert
        assertEquals("omar", finalUserList.get().getFirstName());
        assertEquals("takla", finalUserList.get().getLastName());
        assertEquals(LocalDate.of(2004, 01, 01), finalUserList.get().getBirthDate());
        assertEquals(77, finalUserList.get().getWeight());
        assertEquals(170, finalUserList.get().getHeight());
    }

    /**
     * verifies that the IllegalStateException was thrown when user list is empty
     */
    @Test
    @DisplayName("delete User by email: ignore empty list and maintain the list")
    void testDeleteUserByEmailEmpty(){
        // Arrange
        String email = Constants.FIRST_USER_EMAIL;

        // Assert
        assertThrows(IllegalStateException.class, () ->{
            userDetailsRepository.deleteUserDetailsByEmail(email);
        });
    }

    /**
     * delete user by email
     */
    @Test
    @DisplayName("delete User by email: success")
    void testDeleteUserByNameSuccess(){
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();
        userDetailsRepository.createUserDetails(userDetails);
        String email = Constants.FIRST_USER_EMAIL;
        int expectedSizeOfUserList = 0;

        // Act
        userDetailsRepository.deleteUserDetailsByEmail(email);
        int actualSizeOfUserList = userDetailsRepository.getUserDetailsList().size();

        // Assert
        assertEquals(expectedSizeOfUserList, actualSizeOfUserList);

    }
}

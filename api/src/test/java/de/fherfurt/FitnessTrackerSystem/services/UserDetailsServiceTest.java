package de.fherfurt.FitnessTrackerSystem.services;


import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.UserDetails;
import de.fherfurt.FitnessTrackerSystem.repositories.UserDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link UserDetailsService} class
 * @author Mehdi Rahimi
 */
public class UserDetailsServiceTest {
    private UserDetailsRepository userDetailsRepository;
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp(){
        userDetailsRepository = new UserDetailsRepository();
        userDetailsService = new UserDetailsService(userDetailsRepository);
    }

    /**
     * get User details by id
     */
    @Test
    @DisplayName("get UserDetails by Id: success")
    void testGetUserDetailsOfUserByIdSuccess(){
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();
        int userId = Constants.FIRST_USER_ID;

        // Act
        userDetailsRepository.createUserDetails(userDetails);
        Optional<UserDetails> userDetailsList = userDetailsService.getUserDetailsOfUserById(userId);

        // Assert
        assertTrue(userDetailsList.isPresent());
        assertEquals(userDetails, userDetailsList.get());
    }

    /**
     * verifies that false is returned when the userid does not exists
     */
    @Test
    @DisplayName("checkIsOwnUserDetails: userId does not exists")
    void testCheckIsOwnUserDetailsNotFound(){
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();
        userDetailsRepository.createUserDetails(userDetails);

        // Act
        boolean userDetailsList = userDetailsService.checkIsOwnUserDetails(5);

        // Assert
        assertFalse(userDetailsList);
    }

    /**
     * verifies that the userDetails exists
     */
    @Test
    @DisplayName("testCheckIsOwnUserDetails : userDetails does  exists")
    void testCheckIsOwnUserDetailsSuccess(){
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();

        // Act
        userDetailsRepository.createUserDetails(userDetails);
        boolean userDetailsList = userDetailsService.checkIsOwnUserDetails(userDetails.getUserId());

        // Assert
        assertTrue(userDetailsList);
    }

    /**
     * added userDetails
     */
    @Test
    @DisplayName("addUserDetails: success")
    void testAddUserDetailsSuccess(){
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();
        int expectedSizeOfUserDetails = 1;

        // Act
        userDetailsService.addUserDetails(userDetails);
        int actualSizeOfUserDetails = userDetailsRepository.getUserDetailsList().size();

        // Assert
        assertEquals(expectedSizeOfUserDetails, actualSizeOfUserDetails);
    }

    /**
     * updates userDetails
     */
    @Test
    @DisplayName("updateUserDetails: Success")
    void testUpdateUserDetailsSuccess(){
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();
        userDetailsRepository.createUserDetails(userDetails);
        int userId = Constants.FIRST_USER_ID;

        UserDetails updatedUserDetails = new UserDetails(userId,"omar", "takla", "omar@gmail.com", LocalDate.of(2004, 01, 01), 77, 170);

        // Act
        userDetailsService.updateUserDetails(updatedUserDetails);
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
     * delete UserDetails by Id
     */
    @Test
    @DisplayName("delete UserDetails By Id: Success")
    void testDeleteUserByUserDetailsIdSuccess(){
        // Arrange
        UserDetails userDetails = Constants.getFirstUserDetails();
        userDetailsRepository.createUserDetails(userDetails);
        int userId = 1;
        int expectedSizeOfUsers = 0;

        // Act
        userDetailsService.deleteUserDetailsById(userId);
        int actualSizeOfUsers = userDetailsRepository.getUserDetailsList().size();

        // Assert
        assertEquals(expectedSizeOfUsers, actualSizeOfUsers);
    }

}

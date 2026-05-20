package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.core.UserRole;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link UserService} class
 * @author Mehdi Rahimi
 */

public class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp(){
        userRepository = new UserRepository();
        userService = new UserService(userRepository);
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("SignUp User null: Ignore null input and maintain")
    void testSignUpUserNull(){
        assertThrows(IllegalArgumentException.class, () ->{
                userService.signUpUser(null);
        });
    }

    /**
     * verifies that the user already exists
     */
    @Test
    @DisplayName("SignUp User exists: return empty optional")
    void testSignUpUserExists(){
        // Arrange
        User existingUser = Constants.getFirstUser();
        userRepository.createUser(existingUser);

        // Act
        Optional<User> userSignUpList = userService.signUpUser(existingUser);

        // Assert
        assertTrue(userSignUpList.isEmpty());
    }

    /**
     * verifies that the user signed up successfully
     */
    @Test
    @DisplayName("SignUp User: success")
    void testSignUpUserSuccess(){
        User user = Constants.getSecondUser();

        // Act
        Optional<User> userSignUpList = userService.signUpUser(user);

        // Assert that the list is not empty
        assertTrue(userSignUpList.isPresent());
        assertEquals(user, userSignUpList.get());

        // Assert that user is in the list
        Optional<User> savedUser = userRepository.getUserByUserName(user.getUserName());
        assertTrue(savedUser.isPresent());
        assertEquals(user, savedUser.get());
    }

    /**
     * gets User by Id
     */
    @Test
    @DisplayName("getUserById: success")
    void testGetUserByIdSuccess(){
        // Arrange
        User user = Constants.getFirstUser();
        int userId = Constants.FIRST_USER_ID;

        // Act
        userRepository.createUser(user);
        Optional<User> userSignUpList = userService.getUserById(userId);

        // Assert
        assertTrue(userSignUpList.isPresent());
        assertEquals(user, userSignUpList.get());

    }

    /**
     * gets User by UserName
     */
    @Test
    @DisplayName("getUserByUserName: success")
    void testGetUserByUserNameSuccess(){
        // Arrange
        User user = Constants.getFirstUser();
        String userName = Constants.FIRST_USER_NAME;

        // Act
        userRepository.createUser(user);
        Optional<User> userSignUpList = userService.getUserByUserName(userName);

        // Assert that the userSignUpList is not empty
        assertTrue(userSignUpList.isPresent());
        assertEquals(user, userSignUpList.get());
    }

    /**
     * Verifies that false is returned when the username does not exist
     */
    @Test
    @DisplayName("Authenticate User: Fail when username does not exist")
    void testAuthenticateUserNotFound() {
        // Arrange
        User user = Constants.getFirstUser();
        userRepository.createUser(user);

        // Act
        boolean userList = userService.authenticateUser("Saphia", "something");

        // Assert
        assertFalse(userList);
    }

    /**
     * Verifies that false is returned when the user exists but the password is wrong
     */
    @Test
    @DisplayName("Authenticate User: Fail with wrong password")
    void testAuthenticateUserWrongPassword() {
        // Arrange
        User user = Constants.getFirstUser();
        userRepository.createUser(user);

        // Act
        boolean userList = userService.authenticateUser(user.getUserName(), "falsches_passwort_123");

        // Assert
        assertFalse(userList);
    }

    /**
     * verifies that the user exists
     */
    @Test
    @DisplayName("authenticateUser : user does exists")
    void testAuthenticateUserSuccess(){
        // Arrange
        User user = Constants.getFirstUser();

        // Act
        userRepository.createUser(user);
        boolean userList = userService.authenticateUser(user.getUserName(), user.getPassWord());

        // Assert that the userSignUpList is not empty
        assertTrue(userList);
    }

    /**
     * update user
     */
    @Test
    @DisplayName("UpdateUser: Success")
    void testUpdateUseSuccess(){
        // Arrange
        User user = Constants.getFirstUser();
        userRepository.createUser(user);
        User updatedUser = new User(1, "mehdi", "newPassword", UserRole.USER, null);

        // Act
        userService.updateUser(updatedUser);

        // Assert
        Optional<User> finalUserList = userRepository.getUserByUserName("mehdi");
        assertEquals("newPassword", finalUserList.get().getPassWord());

    }

    /**
     * delete User by UserName
     */
    @Test
    @DisplayName("delete User By UserName: Success")
    void testDeleteUserByUserNameSuccess(){
        // Arrange
        User user = Constants.getFirstUser();
        userRepository.createUser(user);
        String userName = "mehdi";
        int expectedSizeOfUsers = 0;

        // Act
        userService.deleteUserByUserName(userName);
        int actualSizeOfUsers = userRepository.getUsersList().size();

        // Assert
        assertEquals(expectedSizeOfUsers, actualSizeOfUsers);
    }

    /**
     * delete User by Id
     */
    @Test
    @DisplayName("delete User By Id: Success")
    void testDeleteUserByUserIdSuccess(){
        // Arrange
        User user = Constants.getFirstUser();
        userRepository.createUser(user);
        int userId = 1;
        int expectedSizeOfUsers = 0;

        // Act
        userService.deleteUserByUserId(userId);
        int actualSizeOfUsers = userRepository.getUsersList().size();

        // Assert
        assertEquals(expectedSizeOfUsers, actualSizeOfUsers);
    }

}

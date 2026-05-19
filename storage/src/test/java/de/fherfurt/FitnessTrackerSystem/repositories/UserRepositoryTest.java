package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.core.UserRole;
import de.fherfurt.FitnessTrackerSystem.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link UserRepository} class
 * @author Mehdi Rahimi
 */
public class UserRepositoryTest {
    private UserRepository userRepository;
    private User mehdi;
    private User ammar;

    @BeforeEach
    void setUp(){
        userRepository = new UserRepository();
        mehdi = Constants.getFirstUser();
        ammar = Constants.getSecondUser();
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("Crate User : Ignore null input and maintain empty list")
    void testCreateUserNull(){
        assertThrows(IllegalArgumentException.class, () ->{
            userRepository.createUser(null);
        });
    }

    /**
     * verifies that a user was added to the list
     */
    @Test
    @DisplayName("Crate User : success")
    void testCreateUserSuccess(){
        // Arrange
        User user = Constants.getFirstUser();
        int expectedSizeOfUserList = 1;

        // Act
        userRepository.createUser(user);
        int actualSizeOfUserList = userRepository.getUsersList().size();

        // Assert
        assertEquals(expectedSizeOfUserList, actualSizeOfUserList);
    }

    /**
     * verifies that there is no user in the list
     */
    @Test
    @DisplayName("Get null Users")
    void testGetNullUser(){
        // Act
        List<User> userList = userRepository.getAllUsers();

        // Assert
        assertEquals(0, userList.size());
    }

    /**
     * verifies that there is 1 User in the list
     */
    @Test
    @DisplayName("Get one User")
    void testGetOneUser(){
        // Arrange
        User user1 = mehdi;

        // Act
        userRepository.createUser(user1);

        List<User> userList = userRepository.getAllUsers();

        // Assert
        assertEquals(1, userList.size());
        assertTrue(userList.contains(user1));

    }

    /**
     * verifies that there is multiple User
     */
    @Test
    @DisplayName("Get all Users")
    void testGetAllUsers(){
        // Arrange
        User user1 = mehdi;
        User user2 = ammar;

        // Act
        userRepository.createUser(user1);
        userRepository.createUser(user2);

        List<User> userList = userRepository.getAllUsers();

        // Assert
        assertEquals(2, userList.size());
        assertTrue(userList.contains(user1));
        assertTrue(userList.contains(user2));
    }

    /**
     * verifies that there is no User id
     */
    @Test
    @DisplayName("Get User by Id: empty")
    void testGetUserByIdIsEmpty(){
        // Act
        Optional<User> userList = userRepository.getUserById(3);

        // Assert
        assertTrue(userList.isEmpty());
    }

    /**
     * verifies that there is a User id
     */
    @Test
    @DisplayName("Get User by Id : present")
    void testGetUserByIdIsPresent(){
        // Arrange
        User user1 = mehdi;

        // Act
        userRepository.createUser(user1);
        Optional<User> userList = userRepository.getUserById(1);

        // Assert
        assertTrue(userList.isPresent());
    }

    /**
     * gets the user id
     */
    @Test
    @DisplayName("Get User by Id")
    void testGetUserById(){
        // Arrange
        User user1 = mehdi;

        // Act
        userRepository.createUser(user1);
        Optional<User> userList = userRepository.getUserById(1);

        // Assert
        assertEquals(user1, userList.get());
    }

    /**
     * verifies that there is no User name
     */
    @Test
    @DisplayName("Get User by name: empty")
    void testGetUserByNameIsEmpty(){
        // Act
        Optional<User> userList = userRepository.getUserByUserName("Saphia");

        // Assert
        assertTrue(userList.isEmpty());
    }

    /**
     * verifies that there is a User name
     */
    @Test
    @DisplayName("Get User by name : present")
    void testGetUserByNameIsPresent(){
        // Arrange
        User user1 = mehdi;

        // Act
        userRepository.createUser(user1);
        Optional<User> userList = userRepository.getUserByUserName("mehdi");

        // Assert
        assertTrue(userList.isPresent());
    }

    /**
     * gets the user name
     */
    @Test
    @DisplayName("Get User by name")
    void testGetUserByName(){
        // Arrange
        User user = mehdi;

        // Act
        userRepository.createUser(user);
        Optional<User> userList = userRepository.getUserByUserName("mehdi");

        // Assert
        assertEquals(user, userList.get());
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("Update User: Ignore null input and maintain empty list")
    void testUpdateUserNull(){
        assertThrows(IllegalArgumentException.class, () ->{
            userRepository.updateUser(null);
        });
    }

    /**
     * verifies that the IllegalStateException was thrown when user list is empty
     */
    @Test
    @DisplayName("Update User: ignore empty list and maintain the list")
    void testUpdateUserEmpty(){
        // Arrange
        User user = mehdi;

        // Assert
        assertThrows(IllegalStateException.class, () ->{
            userRepository.updateUser(user);
        });
    }

    /**
     * updates User
     */
    @Test
    @DisplayName("update User: Success")
    void testUpdateUserSuccess(){
        // Arrange
        User user = mehdi;                    // userName: "mehdi", password: "password123"
        userRepository.createUser(user);

        User updatedUser = new User(1, "mehdi", "newPassword", UserRole.USER, null);  // gleiche ID, neues Passwort

        // Act
        userRepository.updateUser(updatedUser);

        // Assert
        Optional<User> finalUserList = userRepository.getUserByUserName("mehdi");
        assertEquals("newPassword", finalUserList.get().getPassWord());
    }

    /**
     * verifies that the IllegalStateException was thrown when user list is empty
     */
    @Test
    @DisplayName("delete User by id: ignore empty list and maintain the list")
    void testDeleteUserByIdEmpty(){
        // Arrange
        int userId = 1;

        // Assert
        assertThrows(IllegalStateException.class, () ->{
            userRepository.deleteUserByUserId(userId);
        });
    }

    /**
     * delete user by id
     */
    @Test
    @DisplayName("delete User by id: success")
    void testDeleteUserByIdSuccess(){
        // Arrange
        User user = mehdi;
        userRepository.createUser(user);
        int userId = 1;
        int expectedSizeOfUsers = 0;

        // Act
        userRepository.deleteUserByUserId(userId);
        int actualSizeOfUsers = userRepository.getUsersList().size();

        // Assert
        assertEquals(expectedSizeOfUsers, actualSizeOfUsers);

    }

    /**
     * verifies that the IllegalStateException was thrown when user list is empty
     */
    @Test
    @DisplayName("delete User by naem: ignore empty list and maintain the list")
    void testDeleteUserByNameEmpty(){
        // Arrange
        String userName = "mehdi";

        // Assert
        assertThrows(IllegalStateException.class, () ->{
            userRepository.deleteUserByUserName(userName);
        });
    }

    /**
     * delete user by name
     */
    @Test
    @DisplayName("delete User by name: success")
    void testDeleteUserByNameSuccess(){
        // Arrange
        User user = mehdi;
        userRepository.createUser(user);
        String userName = "mehdi";
        int expectedSizeOfUsers = 0;

        // Act
        userRepository.deleteUserByUserName(userName);
        int actualSizeOfUsers = userRepository.getUsersList().size();

        // Assert
        assertEquals(expectedSizeOfUsers, actualSizeOfUsers);

    }
}


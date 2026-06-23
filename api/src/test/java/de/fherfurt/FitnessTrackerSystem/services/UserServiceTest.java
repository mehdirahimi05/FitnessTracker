package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.models.UserRole;
import de.fherfurt.FitnessTrackerSystem.repositories.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    IUserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    private final User mehdi = new User(1, "mehdi", "pass123", UserRole.USER, null);
    private final User ammar = new User(2, "ammar", "pass456", UserRole.USER, null);

    /**
     * verifies that no User was found
     */
    @Test
    void testGetAllUserNull() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of());

        // Act
        List<User> result = userService.getAllUser();

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * verifies that one user was found
     */
    @Test
    void testGetAllUsersOne() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of(mehdi));

        // Act
        List<User> result = userService.getAllUser();

        // Assert
        assertEquals(1, result.size());
    }

    /**
     * verifies that more than one user was found
     */
    @Test
    void testGetAllUser() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of(mehdi, ammar));

        // Act
        List<User> result = userService.getAllUser();

        // Assert
        assertEquals(2, result.size());
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    void testSignUpUserNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.signUpUser(null);
        });
    }

    /**
     * verifies that the user already exists
     */
    @Test
    void testSignUpUserExists() {
        // Arrange
        when(userRepository.findByUserName("mehdi")).thenReturn(Optional.of(mehdi));

        // Act
        Optional<User> result = userService.signUpUser(mehdi);

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * verifies that the user was singed up successfully
     */
    @Test
    void testSignUpUserSuccess() {
        // Arrange
        when(userRepository.findByUserName("mehdi")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(mehdi);

        // Act
        Optional<User> result = userService.signUpUser(mehdi);

        // Assert that the list is not empty anymore
        assertTrue(result.isPresent());
        assertEquals(mehdi, result.get());

    }

    @Test
    void testGetUserByIdSuccess() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(mehdi));

        // Act
        Optional<User> result = userService.getUserById(mehdi.getUserId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mehdi, result.get());
    }

    @Test
    void testGetUserByUserNameSuccess() {
        // Arrange
        when(userRepository.findByUserName("mehdi")).thenReturn(Optional.of(mehdi));

        // Act
        Optional<User> result = userService.getUserByUserName(mehdi.getUserName());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mehdi, result.get());
    }

    /**
     * verifies that update user was successful
     */
    @Test
    void testUpdateUserSuccess() {
        // Arrange
        userService.updateUser(mehdi);

        // Assert - verify if save() was called
        verify(userRepository).save(mehdi);
    }

    /**
     * verifies that the user was deleted by UserName successfully
     */
    @Test
    void testDeleteUserByUserNameSuccess() {
        // Act
        userService.deleteUserByUserName(mehdi.getUserName());

        // Assert - verify if deleteUserByUserName() was called
        verify(userRepository).deleteByUserName(mehdi.getUserName());
    }

    /**
     * verifies that the user was deleted by UserId successfully
     */
    @Test
    void testDeleteUserByUserIdSuccess() {
        // Act
        userService.deleteUserByUserId(mehdi.getUserId());

        // Assert - verify if deleteById() was called
        verify(userRepository).deleteById(mehdi.getUserId());
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    void testLogInNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.logIn(null, null);
        });
    }

    /**
     * Verifies that false is returned when the username does not exist
     */
    @Test
    void testLogInUserNotFound() {
        // Arrange
        when(userRepository.findByUserName("Ali")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.logIn("Ali", "password2376");

        // Assert
        assertFalse(result.isPresent());
    }

    /**
     * Verifies that false is returned when the user exists but the password is wrong
     */
    @Test
    void testLogInWrongPassword() {
        // Arrange
        when(userRepository.findByUserName("mehdi")).thenReturn(Optional.of(mehdi));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Act
        Optional<User> result = userService.logIn("mehdi", "pass234");

        // Assert
        assertFalse(result.isPresent());
    }

    /**
     * verifies that the user exists
     */
    @Test
    void testLogInSuccess() {
        // Arrange
        when(userRepository.findByUserName("mehdi")).thenReturn(Optional.of(mehdi));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // Act
        Optional<User> result = userService.logIn(mehdi.getUserName(), mehdi.getPassWord());

        // Assert
        assertTrue(result.isPresent());
    }

    /**
     * verifies that the authentification was successful
     */
    @Test
    void tesAuthenticateUserSuccess() {
        // Arrange
        when(userRepository.findByUserName("mehdi")).thenReturn(Optional.of(mehdi));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // Act
        boolean result = userService.authenticateUser(mehdi.getUserName(), mehdi.getPassWord());

        // Assert
        assertTrue(result);

    }
}

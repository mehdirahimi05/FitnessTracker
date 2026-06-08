package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> signUpUser(User newUser);

    List<User> getAllUser();

    Optional<User> getUserById(int userId);

    Optional<User> getUserByUserName(String userName);

    void updateUser(User updatedUser);

    void deleteUserByUserName(String username);

    void deleteUserByUserId(int userId);

    Optional<User> logIn(String userName, String password);

    boolean authenticateUser(String userName, String passWord);

    void logOut(String userName);
}


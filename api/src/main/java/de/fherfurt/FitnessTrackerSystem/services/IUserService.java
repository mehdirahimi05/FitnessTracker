package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> signUpUser(User newUser);
    Optional<User> getUserById(int userId);
    Optional<User> getUserByUserName(String userName);
    boolean authenticateUser(String userName, String passWord);
    void updateUser(User updatedUser);
    void deleteUserByUserName(String username);
    void deleteUserByUserId(int userId);
}


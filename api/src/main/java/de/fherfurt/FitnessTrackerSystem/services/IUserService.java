package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> getUserById(int userId);
    Optional<User> getUserByUserName(String userName);
    boolean checkUserExists(String userName, String passWord);
    void addUser(User newUser);
    void updateUser(User updatedUser);
    void deleteUserByUserName(String username);
    void deleteUserByUserId(int userid);
}


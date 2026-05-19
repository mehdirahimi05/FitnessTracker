package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    void createUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(int userId);
    Optional<User> getUserByUserName(String  userName);
    void updateUser(User user);
    void deleteUserByUserId(int userId);
    void deleteUserByUserName(String userName);

}

package de.fherfurt.FitnessTrackerSystem.repositorys;

import de.fherfurt.FitnessTrackerSystem.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    List<User> getAllUser();
    Optional<User> getUserById(int userId);
    Optional<User> getUserByUserName(String  userName);
    boolean checkUserExists(String userName, String passWord);
    void createUser(User user);
    void updateUser(User user);
    void deleteUserByUserId(int userId);
    void deleteUserByUserName(String user);

}

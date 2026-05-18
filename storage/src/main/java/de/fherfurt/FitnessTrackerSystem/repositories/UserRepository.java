package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the User Repository. This class provides the methods
 * for managing Users
 *
 * @author Mehdi Rahimi
 */
public class UserRepository implements IUserRepository{
    @Getter
    private final List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
    }

    /**
     * create and add a new User to the users list
      * @param user
     */
    @Override
    public void createUser(User user) {
        if (user == null){
            throw new IllegalArgumentException("users can not be null");
        }
        users.add(user);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    /**
     * @param userId
     */
    @Override
    public Optional<User> getUserById(int userId) {
        return users.stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst();
    }

    /**
     * @param userName
     */
    @Override
    public Optional<User> getUserByUserName(String userName) {
        return users.stream()
                .filter(user -> user.getUserName().equalsIgnoreCase(userName))
                .findFirst();
    }

    /**
     * @param user
     */
    @Override
    public void updateUser(User user) {
        if (user == null){
            throw new IllegalArgumentException("users can not be null");
        }
        var existingUser = getUserByUserName(user.getUserName());
        if (existingUser.isEmpty()) {
            throw new IllegalStateException("User not found");
        }
        users.set(users.indexOf(existingUser.get()), user);
    }

    /**
     * @param userId
     */
    @Override
    public void deleteUserByUserId(int userId) {
        var foundUserId = getUserById(userId);
        if (foundUserId.isEmpty()){
            throw new IllegalStateException("foundUserId does not exists in users");
        }
        users.remove(foundUserId.get());
    }

    /**
     * @param userName
     */
    @Override
    public void deleteUserByUserName(String userName) {
        var foundUserName = getUserByUserName(userName);
        if (foundUserName.isEmpty()){
            throw new IllegalStateException("foundUserName does not exists in users");
        }
            users.remove(foundUserName.get());
    }
}

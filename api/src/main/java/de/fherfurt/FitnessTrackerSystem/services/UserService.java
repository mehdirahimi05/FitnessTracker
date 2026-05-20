package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.repositories.IUserRepository;

import java.util.Optional;

public class UserService implements IUserService{
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> signUpUser(User newUser) {
        if (newUser == null){
            throw new IllegalArgumentException("Can not be null");
        }
        var existingUser = userRepository.getUserByUserName(newUser.getUserName());
        if (existingUser.isPresent()) {
            return Optional.empty();
        }
        userRepository.createUser(newUser);
        return Optional.of(newUser);
    }

    @Override
    public Optional<User> getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName);
    }


    @Override
    public boolean authenticateUser(String userName, String passWord) {
        var foundUser = getUserByUserName(userName);
        if (foundUser.isEmpty()){
            return false;
        }
        return foundUser.get().getPassWord().equals(passWord);
    }


    @Override
    public void updateUser(User updatedUser) {
        userRepository.updateUser(updatedUser);
    }

    @Override
    public void deleteUserByUserName(String username) {
        userRepository.deleteUserByUserName(username);
    }

    @Override
    public void deleteUserByUserId(int userId) {
        userRepository.deleteUserByUserId(userId);
    }
}

package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> signUpUser(User newUser) {
        if (newUser == null) {
            throw new IllegalArgumentException("User can not be null");
        }
        var existingUser = userRepository.findByUserName(newUser.getUserName());
        if (existingUser.isPresent()) {
            return Optional.empty();
        }
        userRepository.save(newUser);
        return Optional.of(newUser);
    }

    @Override
    public Optional<User> getUserById(int userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }


    @Override
    public boolean authenticateUser(String userName, String passWord) {
        var foundUser = getUserByUserName(userName);
        if (foundUser.isEmpty()) {
            return false;
        }
        return foundUser.get().getPassWord().equals(passWord);
    }


    @Override
    public void updateUser(User updatedUser) {
        userRepository.save(updatedUser);
    }

    @Override
    public void deleteUserByUserName(String username) {
        userRepository.deleteByUserName(username);
    }

    @Override
    public void deleteUserByUserId(int userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<User> logIn(String userName, String password) {
        //TODO: check if the user exists
        if (userName == null || password == null) {
            throw new IllegalArgumentException("can not be null");
        }
        var existingUser = userRepository.findByUserName(userName);
        if (existingUser.isEmpty()) {
            return Optional.empty();
        }
        if (existingUser.get().getPassWord().equals(password)) {
            return Optional.of(existingUser.get());
        }
        return Optional.empty();
        //TODO: check if the user is already logt in->spring
        //TODO: create a session
    }

    @Override
    public void logOut(String userName) {
    }
}

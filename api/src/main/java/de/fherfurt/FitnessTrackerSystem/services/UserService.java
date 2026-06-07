package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.repositories.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> signUpUser(User newUser) {
        if (newUser == null) {
            throw new IllegalArgumentException("User can not be null");
        }
        var existingUser = userRepository.findByUserName(newUser.getUserName());
        if (existingUser.isPresent()) {
            return Optional.empty();
        }
        // Passwort hashen bevor es gespeichert wird
        newUser.setPassWord(passwordEncoder.encode(newUser.getPassWord()));
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
        return passwordEncoder.matches(passWord, foundUser.get().getPassWord());
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
        if (userName == null || password == null) {
            throw new IllegalArgumentException("can not be null");
        }
        var existingUser = userRepository.findByUserName(userName);
        if (existingUser.isEmpty()) {
            return Optional.empty();
        }
        // BCrypt password compare
        if (passwordEncoder.matches(password, existingUser.get().getPassWord())) {
            return Optional.of(existingUser.get());
        }
        return Optional.empty();
    }

    @Override
    public void logOut(String userName) {
        // Spring Security handled logout
    }
}
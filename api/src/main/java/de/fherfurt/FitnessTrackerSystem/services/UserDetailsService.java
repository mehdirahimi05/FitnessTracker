package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.UserDetails;
import de.fherfurt.FitnessTrackerSystem.repositories.IUserDetailsRepository;

import java.util.Optional;

public class UserDetailsService implements IUserDetailsService {
    private final IUserDetailsRepository userDetailsRepository;

    public UserDetailsService(IUserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public Optional<UserDetails> getUserDetailsOfUserById(int userId) {
        return userDetailsRepository.getUserDetailsOfUserByUserId(userId);
    }


    @Override
    public boolean checkIsOwnUserDetails(int userId) {
        var userDetailsToCheck = userDetailsRepository.getUserDetailsOfUserByUserId(userId);
        if (userDetailsToCheck.isEmpty()) {
            return false;
        }
        return userDetailsToCheck.get().getUserId() == userId;
    }

    @Override
    public void addUserDetails(UserDetails newDetails) {
        userDetailsRepository.createUserDetails(newDetails);
    }

    @Override
    public void updateUserDetails(UserDetails updatedDetails) {
        userDetailsRepository.updateUserDetails(updatedDetails);
    }

    @Override
    public void deleteUserDetailsById(int userId) {
        userDetailsRepository.deleteUserDetailsById(userId);
    }
}

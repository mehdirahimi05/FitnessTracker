package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.UserDetails;

import java.util.Optional;

public interface IUserDetailsService {
    Optional<UserDetails> getUserDetailsOfUserById(int userId);
    boolean checkIsOwnUserDetails(int userId);
    void addUserDetails(UserDetails newDetails);
    void updateUserDetails(UserDetails updatedDetails);
    void deleteUserDetailsById(int userId);
}

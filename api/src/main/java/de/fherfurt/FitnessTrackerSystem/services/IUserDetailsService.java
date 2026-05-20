package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.UserDetails;

import java.util.Optional;

public interface IUserDetailsService {
    Optional<UserDetails> getUserDetailsOfUserById(int userId);
    Optional<UserDetails> getUserDetailsOfUserByEmail(String email);
    boolean checkIsOwnUserDetails(String email, String userName);
    void addUserDetails(UserDetails newDetails);
    void updateUserDetails(UserDetails updatedDetails);
    void deleteUserDetails(String email);
}

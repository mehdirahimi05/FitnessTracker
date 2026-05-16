package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.models.UserDetails;

import java.util.List;
import java.util.Optional;

public interface IUserDetailsService {
    Optional<UserDetails> getUserDetailsOfUserById(int userId);
    Optional<UserDetails> getUserDetailsOfUserByUserName(String userName);
    boolean checkIsOwnUserDetails(UserDetails userDetails, String userName);
    void addUserDetails(UserDetails newDetails);
    void updateUserDetails(UserDetails updatedDetails);
    void deleteUserDetails(UserDetails userDetails);
}

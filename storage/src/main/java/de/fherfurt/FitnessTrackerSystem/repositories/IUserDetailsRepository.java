package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.UserDetails;

import java.util.Optional;

public interface IUserDetailsRepository {
    void createUserDetails(UserDetails userDetails);
    Optional<UserDetails> getUserDetailsOfUserByEmail(String email);
    void updateUserDetails(UserDetails userDetails);
    void deleteUserDetailsByEmail(String  email);
}

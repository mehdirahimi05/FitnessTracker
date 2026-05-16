package de.fherfurt.FitnessTrackerSystem.repositorys;

import de.fherfurt.FitnessTrackerSystem.models.UserDetails;

import java.util.Optional;

public interface IUserDetailsRepository {
    Optional<UserDetails> getUserDetailsOfUserById(int userId);
    Optional<UserDetails> getUserDetailsOfUserByUserName(String userName);
    void createUserDetails(UserDetails userDetails);
    void updateUserDetails(UserDetails userDetails);
    void deleteUserDetails(UserDetails userDetails);
}

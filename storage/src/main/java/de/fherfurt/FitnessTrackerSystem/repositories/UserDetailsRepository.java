package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.UserDetails;

import java.util.Optional;

public class UserDetailsRepository implements IUserDetailsRepository{
    @Override
    public Optional<UserDetails> getUserDetailsOfUserById(int userId) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDetails> getUserDetailsOfUserByUserName(String userName) {
        return Optional.empty();
    }

    @Override
    public void createUserDetails(UserDetails userDetails) {

    }

    @Override
    public void updateUserDetails(UserDetails userDetails) {

    }

    @Override
    public void deleteUserDetails(UserDetails userDetails) {

    }
}

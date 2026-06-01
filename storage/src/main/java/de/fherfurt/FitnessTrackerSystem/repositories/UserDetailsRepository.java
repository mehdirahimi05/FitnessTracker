package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.UserDetails;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDetailsRepository implements IUserDetailsRepository {
    @Getter
    private final List<UserDetails> userDetailsList;

    public UserDetailsRepository() {
        userDetailsList = new ArrayList<>();
    }

    @Override
    public void createUserDetails(UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("can not be null");
        }
        userDetailsList.add(userDetails);

    }

    public Optional<UserDetails> getUserDetailsOfUserByUserId(int userId) {
        return userDetailsList.stream()
                .filter(userDetails -> userDetails.getUserId() == userId)
                .findFirst();
    }


    @Override
    public void updateUserDetails(UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("can not be null");
        }
        var existingUserDetails = getUserDetailsOfUserByUserId(userDetails.getUserId());
        if (existingUserDetails.isEmpty()) {
            throw new IllegalStateException("user details not found");
        }
        userDetailsList.set(userDetailsList.indexOf(existingUserDetails.get()), userDetails);

    }

    @Override
    public void deleteUserDetailsById(int userId) {
        var foundUserEmail = getUserDetailsOfUserByUserId(userId);
        if (foundUserEmail.isEmpty()) {
            throw new IllegalStateException("foundUserEmail does not exist");
        }
        userDetailsList.remove(foundUserEmail.get());
    }
}

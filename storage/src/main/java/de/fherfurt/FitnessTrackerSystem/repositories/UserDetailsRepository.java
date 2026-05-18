package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.UserDetails;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDetailsRepository implements IUserDetailsRepository{
    @Getter
    private final List<UserDetails> userDetailsList;

    public UserDetailsRepository() {
        userDetailsList = new ArrayList<>();
    }

    @Override
    public void createUserDetails(UserDetails userDetails) {
        if (userDetails == null){
            throw new IllegalArgumentException("can not be null");
        }
        userDetailsList.add(userDetails);

    }

    public Optional<UserDetails> getUserDetailsOfUserByEmail(String email) {
        return userDetailsList.stream()
                .filter(userDetails -> userDetails.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }


    @Override
    public void updateUserDetails(UserDetails userDetails) {
        if (userDetails == null){
            throw new IllegalArgumentException("can not be null");
        }
        var existingUserDetails = getUserDetailsOfUserByEmail(userDetails.getEmail());
        if (existingUserDetails.isEmpty()){
            throw new IllegalStateException("user details not found");
        }
        userDetailsList.set(userDetailsList.indexOf(existingUserDetails.get()), userDetails);

    }

    @Override
    public void deleteUserDetailsByEmail(String email) {
        var foundUserEmail = getUserDetailsOfUserByEmail(email);
        if (foundUserEmail.isEmpty()){
            throw new IllegalStateException("foundUserEmail does not exist");
        }
        userDetailsList.remove(foundUserEmail.get());
    }
}

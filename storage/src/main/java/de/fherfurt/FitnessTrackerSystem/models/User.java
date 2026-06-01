package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * Model class representing a user
 *
 * @author Mehdi Rahimi
 */
@AllArgsConstructor
@Setter
@Getter
public class User {
    private int userId;
    private String userName;
    private String passWord;
    private UserRole role;
    private UserDetails userDetails;

}

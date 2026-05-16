package de.fherfurt.FitnessTrackerSystem.models;

import de.fherfurt.FitnessTrackerSystem.core.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Model class representing a user
 * @see Cloneable
 * @see Comparable
 * @author Mehdi Rahimi
 */
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class User{
    private int userId;
    private String userName;
    private String passWord;
    private UserRole role;
    private UserDetails userDetails;

}

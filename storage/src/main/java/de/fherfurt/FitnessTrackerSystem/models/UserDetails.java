package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data model representing UserDetails within the Fitness Tracker System.
 * logic for alphabetical sorting, and provides cloning capabilities.
 *
 * @author Mehdi Rahimi
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails{
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private float weight;
    private float height;

}
package de.fherfurt.FitnessTrackerSystem.models;

import jakarta.persistence.*;
import lombok.*;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    private LocalDate birthDate;
}
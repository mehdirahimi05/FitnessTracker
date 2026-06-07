package de.fherfurt.FitnessTrackerSystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * JPA entity representing extended profile information for a {@link User}.
 *
 * <p>Mapped to the {@code user_details} table. Holds personal data that is
 * kept separate from the core {@link User} account (credentials, role) to
 * maintain a clean separation between authentication data and profile data.</p>
 *
 * <p>Owned by {@link User} via a {@code @OneToOne} relationship — this entity
 * is created, updated, and deleted together with its parent {@link User}
 * through cascading.</p>
 *
 * <p>The protected no-args constructor exists solely for JPA — use the
 * all-args constructor for programmatic instantiation.</p>
 *
 * @author Mehdi Rahimi
 * @see User
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_details")
public class UserProfile {

    /**
     * Auto-generated primary key, assigned by the database on first persist.
     * Do not set manually.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    /**
     * First name of the user.
     */
    private String firstName;

    /**
     * Last name of the user.
     */
    private String lastName;

    /**
     * Email address of the user.
     * Cannot be {@code null} and must be unique across the {@code user_details} table.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Date of birth of the user.
     * Can be used to derive the user's current age where needed.
     */
    private LocalDate birthDate;
}
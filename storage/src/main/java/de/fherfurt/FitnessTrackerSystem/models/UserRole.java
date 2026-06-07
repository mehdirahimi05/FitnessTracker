package de.fherfurt.FitnessTrackerSystem.models;

/**
 * Represents the access role assigned to a {@link User} within the FitnessTrackerSystem.
 *
 * <p>Used by {@link User#getRole()} to control permissions and access levels
 * throughout the system. Persisted as a string via {@code @Enumerated(EnumType.STRING)}.</p>
 *
 * @author Mehdi Rahimi
 * @see User
 */
public enum UserRole {

    /**
     * Limited access — can view public data but cannot create or modify records.
     */
    GUEST,

    /**
     * Standard access — can manage their own training sessions, nutrition, and measurements.
     */
    USER,

    /**
     * Full access — can manage all users and system-wide data.
     */
    ADMIN
}
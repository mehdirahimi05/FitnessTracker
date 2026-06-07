package de.fherfurt.FitnessTrackerSystem.models;

/**
 * Represents the difficulty level of a {@link TrainingsSession}.
 *
 * <p>Used by {@link TrainingsSession#getDifficulty()} and persisted as a
 * string via {@code @Enumerated(EnumType.STRING)}.</p>
 *
 * @author Mehdi Rahimi
 * @see TrainingsSession
 */
public enum Difficulty {

    /**
     * Low intensity — suitable for beginners or recovery sessions.
     */
    EASY,

    /**
     * Moderate intensity — suitable for regular training sessions.
     */
    MEDIUM,

    /**
     * High intensity — suitable for advanced or peak performance sessions.
     */
    HARD
}
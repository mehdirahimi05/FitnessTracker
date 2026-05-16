package de.fherfurt.FitnessTrackerSystem.models;

/**
 * Enumeration representing the different types of physical activities
 * supported by the Fitness Tracker System. Each type includes a
 * display name for better readability in the user interface.
 *
 * @author Mehdi Rahimi
 */
public enum ActivityType {
    /**
     * Represents a running session.
     */
    RUNNING("Running"),

    /**
     * Represents a cycling session.
     */
    CYCLING("Cycling"),

    /**
     * Represents a swimming session.
     */
    SWIMMING("Swimming");

    private final String displayName;

    /**
     * Constructor for the ActivityType enum.
     *
     * @param displayName The human-readable name of the activity.
     */
    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns a string representation of the activity type.
     *
     * @return A string containing the display name of the activity.
     */
    @Override
    public String toString() {
        return "ActivityType{" +
                "displayName='" + displayName + '\'' +
                '}';
    }
}
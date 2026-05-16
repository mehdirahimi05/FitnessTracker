package de.fherfurt.FitnessTrackerSystem;

import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;
import java.time.LocalDate;

/**
 * Zentralisierte Test-Konstanten und Factory-Methoden für das FitnessTrackerSystem.
 * Beinhaltet die Teammitglieder: Mehdi Rahimi, Ammar Alassi und Rawan Asani.
 */
public class Constants {

    // -- USER CONSTANTS --

    /**
     * Factory method to create the third test user (Mehdi).
     * @return A new User instance with predefined constants.
     */
    public static final String FIRST_USER_FIRST_NAME = "Mehdi";
    public static final String FIRST_USER_LAST_NAME = "Rahimi";
    public static final LocalDate FIRST_USER_BIRTH_DATE = LocalDate.of(2005, 10, 14);
    public static final float FIRST_USER_WEIGHT = 80;
    public static final float FIRST_USER_HIGHT = 180;

    public static User getFirstUser(){
        return new User(
                FIRST_USER_FIRST_NAME,
                FIRST_USER_LAST_NAME,
                FIRST_USER_BIRTH_DATE,
                FIRST_USER_WEIGHT,
                FIRST_USER_HIGHT
        );
    }

    /**
     * Factory method to create the third test user (Ammar).
     * @return A new User instance with predefined constants.
     */
    public static final String SECOND_USER_FIRST_NAME = "Ammar";
    public static final String SECOND_USER_LAST_NAME = "Alassi";
    public static final LocalDate SECOND_USER_BIRTH_DATE = LocalDate.of(2004, 1, 13);
    public static final float SECOND_USER_WEIGHT = 75;
    public static final float SECOND_USER_HIGHT = 179;

    public static User getSecondUser(){
        return new User(
                SECOND_USER_FIRST_NAME,
                SECOND_USER_LAST_NAME,
                SECOND_USER_BIRTH_DATE,
                SECOND_USER_WEIGHT,
                SECOND_USER_HIGHT
        );
    }

    // --- TRAINING SESSION CONSTANTS ---
    public static final LocalDate FIRST_DATE = LocalDate.of(2026, 3, 20);
    public static final int FIRST_DURATION_IN_MINUTE = 60;
    public static final float FIRST_DISTANCE_IN_KM = 10.0f;
    public static final int FIRST_BURNED_CALORIES = 600;
    public static final ActivityType FIRST_ACTIVITY_TYPE = ActivityType.RUNNING;

    /**
     * Factory method to create a training session without a specific user.
     * @return A new TrainingsSession instance.
     */
    public static TrainingsSession getFirstTrainingsSession() {
        return getFirstTrainingsSession(null);
    }

    /**
     * Factory method to create a training session for a specific user.
     * @param user The user associated with the session.
     * @return A new TrainingsSession instance.
     */
    public static TrainingsSession getFirstTrainingsSession(User user) {
        return new TrainingsSession(
                FIRST_DATE,
                FIRST_DURATION_IN_MINUTE,
                FIRST_DISTANCE_IN_KM,
                FIRST_BURNED_CALORIES,
                FIRST_ACTIVITY_TYPE,
                user
        );
    }

    public static final LocalDate SECOND_DATE = LocalDate.of(2026, 3, 21);
    public static final int SECOND_DURATION_IN_MINUTE = 45;
    public static final float SECOND_DISTANCE_IN_KM = 25.0f;
    public static final int SECOND_BURNED_CALORIES = 800;
    public static final ActivityType SECOND_ACTIVITY_TYPE = ActivityType.CYCLING;

    /**
     * Factory method to create a second training session without a specific user.
     * @return A new TrainingsSession instance.
     */
    public static TrainingsSession getSecondTrainingsSession() {
        return getSecondTrainingsSession(null);
    }

    /**
     * Factory method to create a second training session for a specific user.
     * @param user The user associated with the session.
     * @return A new TrainingsSession instance.
     */
    public static TrainingsSession getSecondTrainingsSession(User user) {
        return new TrainingsSession(
                SECOND_DATE,
                SECOND_DURATION_IN_MINUTE,
                SECOND_DISTANCE_IN_KM,
                SECOND_BURNED_CALORIES,
                SECOND_ACTIVITY_TYPE,
                user
        );
    }

}
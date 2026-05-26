package de.fherfurt.FitnessTrackerSystem;

import de.fherfurt.FitnessTrackerSystem.core.UserRole;
import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.models.UserDetails;
import de.fherfurt.FitnessTrackerSystem.models.Nutrition;
import de.fherfurt.FitnessTrackerSystem.models.MealTyp;

import java.time.LocalDate;

public class Constants {

    // First User
    public static final int FIRST_USER_ID = 1;
    public static final String FIRST_USER_NAME = "mehdi";
    public static final String FIRST_USER_PASSWORD = "password123";
    public static final UserRole FIRST_USER_ROLE = UserRole.USER;
    public static final String FIRST_USER_FIRST_NAME = "Mehdi";
    public static final String FIRST_USER_LAST_NAME = "Rahimi";
    public static final String FIRST_USER_EMAIL = "mehdi@icloud.com";
    public static final LocalDate FIRST_USER_BIRTH_DATE = LocalDate.of(2005, 10, 14);
    public static final float FIRST_USER_WEIGHT = 80;
    public static final float FIRST_USER_HEIGHT = 180;

    public static UserDetails getFirstUserDetails() {
        return new UserDetails(FIRST_USER_ID, FIRST_USER_FIRST_NAME, FIRST_USER_LAST_NAME, FIRST_USER_EMAIL, FIRST_USER_BIRTH_DATE, FIRST_USER_WEIGHT, FIRST_USER_HEIGHT);
    }

    public static User getFirstUser() {
        return new User(FIRST_USER_ID, FIRST_USER_NAME, FIRST_USER_PASSWORD, FIRST_USER_ROLE, getFirstUserDetails());
    }

    // Second User
    public static final int SECOND_USER_ID = 2;
    public static final String SECOND_USER_NAME = "ammar";
    public static final String SECOND_USER_PASSWORD = "password456";
    public static final UserRole SECOND_USER_ROLE = UserRole.USER;
    public static final String SECOND_USER_FIRST_NAME = "Ammar";
    public static final String SECOND_USER_LAST_NAME = "Alassi";
    public static final String SECOND_USER_EMAIL = "ammar@icloud.com";
    public static final LocalDate SECOND_USER_BIRTH_DATE = LocalDate.of(2004, 1, 13);
    public static final float SECOND_USER_WEIGHT = 75;
    public static final float SECOND_USER_HEIGHT = 179;

    public static UserDetails getSecondUserDetails() {
        return new UserDetails(SECOND_USER_ID, SECOND_USER_FIRST_NAME, SECOND_USER_LAST_NAME, SECOND_USER_EMAIL, SECOND_USER_BIRTH_DATE, SECOND_USER_WEIGHT, SECOND_USER_HEIGHT);
    }

    public static User getSecondUser() {
        return new User(SECOND_USER_ID, SECOND_USER_NAME, SECOND_USER_PASSWORD, SECOND_USER_ROLE, getSecondUserDetails());
    }

    // Training Sessions
    public static final int FIRST_TRAININGS_SESSION_ID = 1;
    public static final LocalDate FIRST_DATE = LocalDate.of(2026, 3, 20);
    public static final int FIRST_DURATION_IN_MINUTE = 60;
    public static final float FIRST_DISTANCE_IN_KM = 10.0f;
    public static final int FIRST_BURNED_CALORIES = 600;
    public static final ActivityType FIRST_ACTIVITY_TYPE = ActivityType.RUNNING;

    public static TrainingsSession getFirstTrainingsSession() {
        return getFirstTrainingsSession(null);
    }

    public static TrainingsSession getFirstTrainingsSession(User user) {
        return new TrainingsSession(FIRST_TRAININGS_SESSION_ID, user, FIRST_DATE, FIRST_DURATION_IN_MINUTE, FIRST_DISTANCE_IN_KM, FIRST_BURNED_CALORIES, FIRST_ACTIVITY_TYPE);
    }

    public static final int SECOND_TRAININGS_SESSION_ID = 2;
    public static final LocalDate SECOND_DATE = LocalDate.of(2026, 3, 21);
    public static final int SECOND_DURATION_IN_MINUTE = 45;
    public static final float SECOND_DISTANCE_IN_KM = 25.0f;
    public static final int SECOND_BURNED_CALORIES = 800;
    public static final ActivityType SECOND_ACTIVITY_TYPE = ActivityType.CYCLING;

    public static TrainingsSession getSecondTrainingsSession() {
        return getSecondTrainingsSession(null);
    }

    public static TrainingsSession getSecondTrainingsSession(User user) {
        return new TrainingsSession(SECOND_TRAININGS_SESSION_ID, user, SECOND_DATE, SECOND_DURATION_IN_MINUTE, SECOND_DISTANCE_IN_KM, SECOND_BURNED_CALORIES, SECOND_ACTIVITY_TYPE);
    }

    // Nutrition Entry
    public static final int FIRST_NUTRITION_ID = 1;
    public static final int FIRST_NUTRITION_USER_ID = FIRST_USER_ID;
    public static final int FIRST_NUTRITION_CALORIES = 500;
    public static final int FIRST_NUTRITION_PROTEIN = 30;
    public static final int FIRST_NUTRITION_CARBOHYDRATES = 60;
    public static final int FIRST_NUTRITION_FAT = 15;
    public static final MealTyp FIRST_MEAL_TYP = MealTyp.BREAKFAST;

    public static Nutrition getFirstNutrition() {
        return new Nutrition(FIRST_NUTRITION_ID, FIRST_NUTRITION_USER_ID, FIRST_NUTRITION_CALORIES, FIRST_NUTRITION_PROTEIN, FIRST_NUTRITION_CARBOHYDRATES, FIRST_NUTRITION_FAT, FIRST_MEAL_TYP);
    }

    public static final int SECOND_NUTRITION_ID = 2;
    public static final int SECOND_NUTRITION_USER_ID = FIRST_USER_ID;
    public static final int SECOND_NUTRITION_CALORIES = 700;
    public static final int SECOND_NUTRITION_PROTEIN = 45;
    public static final int SECOND_NUTRITION_CARBOHYDRATES = 80;
    public static final int SECOND_NUTRITION_FAT = 20;
    public static final MealTyp SECOND_MEAL_TYP = MealTyp.LUNCH;

    public static Nutrition getSecondNutrition() {
        return new Nutrition(SECOND_NUTRITION_ID, SECOND_NUTRITION_USER_ID, SECOND_NUTRITION_CALORIES, SECOND_NUTRITION_PROTEIN, SECOND_NUTRITION_CARBOHYDRATES, SECOND_NUTRITION_FAT, SECOND_MEAL_TYP);
    }
}
package de.fherfurt.FitnessTrackerSystem;

import de.fherfurt.FitnessTrackerSystem.models.*;

import java.time.LocalDate;
import java.util.List;

public class Constants {

    // ─── ActivityType ────────────────────────────────────────
    public static final int FIRST_ACTIVITY_TYPE_ID = 1;
    public static final String FIRST_ACTIVITY_TYPE_NAME = "Krafttraining";

    public static final int SECOND_ACTIVITY_TYPE_ID = 2;
    public static final String SECOND_ACTIVITY_TYPE_NAME = "Cardio";

    public static ActivityType getFirstActivityType() {
        return new ActivityType(FIRST_ACTIVITY_TYPE_ID, FIRST_ACTIVITY_TYPE_NAME);
    }

    public static ActivityType getSecondActivityType() {
        return new ActivityType(SECOND_ACTIVITY_TYPE_ID, SECOND_ACTIVITY_TYPE_NAME);
    }

    // ─── Exercise ────────────────────────────────────────────
    public static final int FIRST_EXERCISE_ID = 1;
    public static final String FIRST_EXERCISE_NAME = "Bankdrücken";

    public static final int SECOND_EXERCISE_ID = 2;
    public static final String SECOND_EXERCISE_NAME = "Kniebeugen";

    public static Exercise getFirstExercise() {
        return new Exercise(FIRST_EXERCISE_ID, FIRST_EXERCISE_NAME, List.of(getFirstActivityType()));
    }

    public static Exercise getSecondExercise() {
        return new Exercise(SECOND_EXERCISE_ID, SECOND_EXERCISE_NAME, List.of(getFirstActivityType()));
    }

    // ─── WorkoutPlanExercise ─────────────────────────────────
    public static final int FIRST_WORKOUT_PLAN_EXERCISE_ID = 1;
    public static final int FIRST_SETS = 4;
    public static final int FIRST_REPETITIONS = 10;

    public static final int SECOND_WORKOUT_PLAN_EXERCISE_ID = 2;
    public static final int SECOND_SETS = 3;
    public static final int SECOND_REPETITIONS = 12;

    public static WorkoutPlanExercise getFirstWorkoutPlanExercise() {
        return new WorkoutPlanExercise(FIRST_WORKOUT_PLAN_EXERCISE_ID, getFirstExercise(), FIRST_SETS, FIRST_REPETITIONS);
    }

    public static WorkoutPlanExercise getSecondWorkoutPlanExercise() {
        return new WorkoutPlanExercise(SECOND_WORKOUT_PLAN_EXERCISE_ID, getSecondExercise(), SECOND_SETS, SECOND_REPETITIONS);
    }

    // ─── WorkoutPlan ─────────────────────────────────────────
    public static final int FIRST_WORKOUT_PLAN_ID = 1;
    public static final String FIRST_WORKOUT_PLAN_NAME = "Brust Training";

    public static final int SECOND_WORKOUT_PLAN_ID = 2;
    public static final String SECOND_WORKOUT_PLAN_NAME = "Bein Training";

    public static WorkoutPlan getFirstWorkoutPlan() {
        WorkoutPlan plan = new WorkoutPlan(FIRST_WORKOUT_PLAN_ID, FIRST_WORKOUT_PLAN_NAME, new java.util.ArrayList<>());
        plan.getExercises().add(getFirstWorkoutPlanExercise());
        return plan;
    }

    public static WorkoutPlan getSecondWorkoutPlan() {
        WorkoutPlan plan = new WorkoutPlan(SECOND_WORKOUT_PLAN_ID, SECOND_WORKOUT_PLAN_NAME, new java.util.ArrayList<>());
        plan.getExercises().add(getSecondWorkoutPlanExercise());
        return plan;
    }

    // ─── UserDetails ─────────────────────────────────────────
    public static final int FIRST_USER_ID = 1;
    public static final String FIRST_USER_FIRST_NAME = "Mehdi";
    public static final String FIRST_USER_LAST_NAME = "Rahimi";
    public static final String FIRST_USER_EMAIL = "mehdi@icloud.com";
    public static final LocalDate FIRST_USER_BIRTH_DATE = LocalDate.of(2005, 10, 14);

    public static final int SECOND_USER_ID = 2;
    public static final String SECOND_USER_FIRST_NAME = "Ammar";
    public static final String SECOND_USER_LAST_NAME = "Alassi";
    public static final String SECOND_USER_EMAIL = "ammar@icloud.com";
    public static final LocalDate SECOND_USER_BIRTH_DATE = LocalDate.of(2004, 1, 13);

    public static UserDetails getFirstUserDetails() {
        return new UserDetails(FIRST_USER_ID, FIRST_USER_FIRST_NAME, FIRST_USER_LAST_NAME, FIRST_USER_EMAIL, FIRST_USER_BIRTH_DATE);
    }

    public static UserDetails getSecondUserDetails() {
        return new UserDetails(SECOND_USER_ID, SECOND_USER_FIRST_NAME, SECOND_USER_LAST_NAME, SECOND_USER_EMAIL, SECOND_USER_BIRTH_DATE);
    }

    // ─── User ────────────────────────────────────────────────
    public static final String FIRST_USER_NAME = "mehdi";
    public static final String FIRST_USER_PASSWORD = "password123";
    public static final UserRole FIRST_USER_ROLE = UserRole.USER;

    public static final String SECOND_USER_NAME = "ammar";
    public static final String SECOND_USER_PASSWORD = "password456";
    public static final UserRole SECOND_USER_ROLE = UserRole.USER;

    public static User getFirstUser() {
        return new User(FIRST_USER_ID, FIRST_USER_NAME, FIRST_USER_PASSWORD, FIRST_USER_ROLE, getFirstUserDetails());
    }

    public static User getSecondUser() {
        return new User(SECOND_USER_ID, SECOND_USER_NAME, SECOND_USER_PASSWORD, SECOND_USER_ROLE, getSecondUserDetails());
    }

    // ─── TrainingsSession ────────────────────────────────────
    public static final int FIRST_TRAININGS_SESSION_ID = 1;
    public static final LocalDate FIRST_DATE = LocalDate.of(2026, 3, 20);
    public static final int FIRST_DURATION_IN_MINUTE = 60;
    public static final int FIRST_BURNED_CALORIES = 500;
    public static final Difficulty FIRST_DIFFICULTY = Difficulty.MEDIUM;

    public static final int SECOND_TRAININGS_SESSION_ID = 2;
    public static final LocalDate SECOND_DATE = LocalDate.of(2026, 3, 21);
    public static final int SECOND_DURATION_IN_MINUTE = 45;
    public static final int SECOND_BURNED_CALORIES = 400;
    public static final Difficulty SECOND_DIFFICULTY = Difficulty.EASY;

    public static TrainingsSession getFirstTrainingsSession() {
        return getFirstTrainingsSession(null);
    }

    public static TrainingsSession getFirstTrainingsSession(User user) {
        return new TrainingsSession(FIRST_TRAININGS_SESSION_ID, user, FIRST_DATE, FIRST_DURATION_IN_MINUTE, FIRST_BURNED_CALORIES, getFirstActivityType(), FIRST_DIFFICULTY, getFirstWorkoutPlan());
    }

    public static TrainingsSession getSecondTrainingsSession() {
        return getSecondTrainingsSession(null);
    }

    public static TrainingsSession getSecondTrainingsSession(User user) {
        return new TrainingsSession(SECOND_TRAININGS_SESSION_ID, user, SECOND_DATE, SECOND_DURATION_IN_MINUTE, SECOND_BURNED_CALORIES, getSecondActivityType(), SECOND_DIFFICULTY, getSecondWorkoutPlan());
    }

    // ─── Nutrition ───────────────────────────────────────────
    public static final int FIRST_NUTRITION_ID = 1;
    public static final int FIRST_NUTRITION_CALORIES = 500;
    public static final int FIRST_NUTRITION_PROTEIN = 30;
    public static final int FIRST_NUTRITION_CARBOHYDRATES = 60;
    public static final int FIRST_NUTRITION_FAT = 15;
    public static final MealTyp FIRST_MEAL_TYP = MealTyp.BREAKFAST;
    public static final LocalDate FIRST_NUTRITION_DATE = LocalDate.of(2026, 3, 20);

    public static final int SECOND_NUTRITION_ID = 2;
    public static final int SECOND_NUTRITION_CALORIES = 700;
    public static final int SECOND_NUTRITION_PROTEIN = 45;
    public static final int SECOND_NUTRITION_CARBOHYDRATES = 80;
    public static final int SECOND_NUTRITION_FAT = 20;
    public static final MealTyp SECOND_MEAL_TYP = MealTyp.LUNCH;
    public static final LocalDate SECOND_NUTRITION_DATE = LocalDate.of(2026, 3, 20);

    public static Nutrition getFirstNutrition(User user) {
        return new Nutrition(FIRST_NUTRITION_ID, user, FIRST_NUTRITION_CALORIES, FIRST_NUTRITION_PROTEIN, FIRST_NUTRITION_CARBOHYDRATES, FIRST_NUTRITION_FAT, FIRST_MEAL_TYP, FIRST_NUTRITION_DATE);
    }

    public static Nutrition getSecondNutrition(User user) {
        return new Nutrition(SECOND_NUTRITION_ID, user, SECOND_NUTRITION_CALORIES, SECOND_NUTRITION_PROTEIN, SECOND_NUTRITION_CARBOHYDRATES, SECOND_NUTRITION_FAT, SECOND_MEAL_TYP, SECOND_NUTRITION_DATE);
    }

    // ─── BodyMeasurement ─────────────────────────────────────
    public static final int FIRST_BODY_MEASUREMENT_ID = 1;
    public static final float FIRST_WEIGHT = 80.0f;
    public static final float FIRST_HEIGHT = 1.80f;
    public static final int FIRST_BODY_FAT_PERCENTAGE = 15;
    public static final LocalDate FIRST_MEASURED_AT = LocalDate.of(2026, 3, 20);

    public static final int SECOND_BODY_MEASUREMENT_ID = 2;
    public static final float SECOND_WEIGHT = 79.0f;
    public static final float SECOND_HEIGHT = 1.80f;
    public static final int SECOND_BODY_FAT_PERCENTAGE = 14;
    public static final LocalDate SECOND_MEASURED_AT = LocalDate.of(2026, 4, 20);

    public static BodyMeasurement getFirstBodyMeasurement(User user) {
        return new BodyMeasurement(FIRST_BODY_MEASUREMENT_ID, user, FIRST_WEIGHT, FIRST_HEIGHT, FIRST_BODY_FAT_PERCENTAGE, FIRST_MEASURED_AT);
    }

    public static BodyMeasurement getSecondBodyMeasurement(User user) {
        return new BodyMeasurement(SECOND_BODY_MEASUREMENT_ID, user, SECOND_WEIGHT, SECOND_HEIGHT, SECOND_BODY_FAT_PERCENTAGE, SECOND_MEASURED_AT);
    }
}
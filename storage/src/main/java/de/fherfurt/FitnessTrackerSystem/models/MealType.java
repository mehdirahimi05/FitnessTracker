package de.fherfurt.FitnessTrackerSystem.models;

/**
 * Represents the category of a meal within a {@link Nutrition} entry.
 *
 * <p>Used by {@link Nutrition#getMealTyp()} and persisted as a string
 * via {@code @Enumerated(EnumType.STRING)}.</p>
 *
 * @author Mehdi Rahimi
 * @see Nutrition
 */
public enum MealType {

    /**
     * First meal of the day, typically consumed in the morning.
     */
    BREAKFAST,

    /**
     * Midday meal.
     */
    LUNCH,

    /**
     * Evening meal, typically the last main meal of the day.
     */
    DINNER,

    /**
     * A small meal or snack consumed between main meals.
     */
    SNACK
}
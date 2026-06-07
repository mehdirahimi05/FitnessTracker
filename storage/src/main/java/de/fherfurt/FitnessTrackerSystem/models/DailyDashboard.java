package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Immutable daily overview combining a user's training and nutrition data for a single day.
 *
 * <p>This is a read-only data transfer object (DTO) — it has no JPA mapping
 * and is not persisted. Aggregates a {@link TrainingsSessionSummary} and a
 * {@link NutritionSummary} into a single object for a specific date, intended
 * as a top-level response model for dashboard views.</p>
 *
 * @author Mehdi Rahimi
 * @see TrainingsSessionSummary
 * @see NutritionSummary
 */
@Getter
@AllArgsConstructor
public class DailyDashboard {

    /**
     * The date this dashboard represents.
     * Both {@link #trainingsSessionSummary} and {@link #nutritionSummary}
     * must refer to data from this date.
     */
    private LocalDate date;

    /**
     * Summary of all training sessions performed on {@link #date},
     * including the session list and aggregated totals for duration and calories burned.
     *
     * @see TrainingsSessionSummary
     */
    private TrainingsSessionSummary trainingsSessionSummary;

    /**
     * Summary of all nutrition entries logged on {@link #date},
     * including aggregated totals for calories and macronutrients.
     *
     * @see NutritionSummary
     */
    private NutritionSummary nutritionSummary;
}
package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Immutable summary of aggregated nutritional values across multiple {@link Nutrition} entries.
 *
 * <p>This is a read-only data transfer object (DTO) — it has no JPA mapping
 * and is not persisted. Typically constructed by aggregating a user's
 * {@link Nutrition} records over a given time period (e.g. a single day).</p>
 *
 * <p>All values represent totals across the aggregated entries, not per-meal values.</p>
 *
 * @author Mehdi Rahimi
 * @see Nutrition
 */
@Getter
@AllArgsConstructor
public class NutritionSummary {

    /**
     * Total caloric intake across all aggregated meals, in kilocalories (kcal).
     */
    private int totalCalories;

    /**
     * Total protein intake across all aggregated meals, in grams.
     */
    private int totalProteinInGram;

    /**
     * Total carbohydrate intake across all aggregated meals, in grams.
     */
    private int totalCarbohydratesInGram;

    /**
     * Total fat intake across all aggregated meals, in grams.
     */
    private int totalFatInGram;
}
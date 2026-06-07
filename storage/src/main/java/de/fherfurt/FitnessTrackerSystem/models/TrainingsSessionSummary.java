package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Immutable summary of aggregated training data across multiple {@link TrainingsSession} entries.
 *
 * <p>This is a read-only data transfer object (DTO) — it has no JPA mapping
 * and is not persisted. Typically constructed by aggregating a user's
 * {@link TrainingsSession} records over a given time period (e.g. a week or month).</p>
 *
 * <p>The session list provides the raw data, while the totals provide
 * pre-aggregated values to avoid repeated iteration on the caller side.</p>
 *
 * @author Mehdi Rahimi
 * @see TrainingsSession
 */
@Getter
@AllArgsConstructor
public class TrainingsSessionSummary {

    /**
     * The individual training sessions included in this summary.
     * Should not be {@code null} — use an empty list if no sessions exist.
     */
    List<TrainingsSession> trainingsSessions;

    /**
     * Total duration across all sessions in this summary, in minutes.
     */
    int totalDurationInMinutes;

    /**
     * Total calories burned across all sessions in this summary, in kilocalories (kcal).
     */
    int totalCaloriesBurned;
}
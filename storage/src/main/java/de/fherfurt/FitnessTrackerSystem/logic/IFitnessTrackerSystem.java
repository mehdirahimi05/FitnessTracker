package de.fherfurt.FitnessTrackerSystem.logic;

import de.fherfurt.FitnessTrackerSystem.logic.filter.TrainingsSessionFilter;
import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.UserDetails;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface defining the core functionalities of the Fitness Tracker System.
 * It provides methods for managing training sessions, calculating performance metrics,
 * and identifying active users based on various criteria.
 *
 * @author Mehdi Rahimi
 */
public interface IFitnessTrackerSystem {

    /**
     * Adds a new training session to the system.
     *
     * @param trainingsSession The session to be added.
     */
    void addTrainingsSession(TrainingsSession trainingsSession);

    /**
     * Updates the metrics of an existing training session.
     *
     * @param trainingsSession  The session to modify.
     * @param durationInMinutes The new duration in minutes.
     * @param distanceInKm      The new distance in kilometers.
     */
    void setTrainingsSession(TrainingsSession trainingsSession, int durationInMinutes, float distanceInKm);

    /**
     * Removes a training session from the system.
     *
     * @param trainingsSession The session to be removed.
     */
    void removeTrainingsSession(TrainingsSession trainingsSession);

    /**
     * Calculates the total training duration for a user within a specified timeframe.
     *
     * @param user      The user whose time is being calculated.
     * @param startDate The start of the date range.
     * @param endDate   The end of the date range.
     * @return Total duration in minutes.
     */
    int calculateTotalTrainingTimeInMinutes(UserDetails user, LocalDate startDate, LocalDate endDate);

    /**
     * Calculates the total distance covered by a user within a specified timeframe.
     *
     * @param user      The user whose distance is being calculated.
     * @param startDate The start of the date range.
     * @param endDate   The end of the date range.
     * @return Total distance in kilometers.
     */
    float calculateTotalDistanceInKm(UserDetails user, LocalDate startDate, LocalDate endDate);

    /**
     * Calculates the average speed of a user during a specific period.
     *
     * @param user      The user whose speed is being calculated.
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return Average speed in km/h.
     */
    float calculateAverageSpeedInKmH(UserDetails user, LocalDate startDate, LocalDate endDate);

    /**
     * Finds the user with the highest total training time in a given timeframe.
     *
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return The most active user by time.
     */
    UserDetails findMostActiveUserByTotalTime(LocalDate startDate, LocalDate endDate);

    /**
     * Finds the user with the highest total distance covered in a given timeframe.
     *
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return The most active user by distance.
     */
    UserDetails findMostActiveUserByTotalDistance(LocalDate startDate, LocalDate endDate);

    /**
     * Filters training sessions based on various optional criteria.

     */
    public List<TrainingsSession> filterTrainingsSession(TrainingsSessionFilter trainingsSessionFilter);
}
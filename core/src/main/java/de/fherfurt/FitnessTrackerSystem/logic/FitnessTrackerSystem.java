package de.fherfurt.FitnessTrackerSystem.logic;


import de.fherfurt.FitnessTrackerSystem.logic.filter.TrainingsSessionFilter;
import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the Fitness Tracker System. This class provides the core logic
 * for managing training sessions, calculating user statistics, and determining
 * the most active users within specific timeframes.
 *
 * @author Mehdi Rahimi
 */
public class FitnessTrackerSystem implements IFitnessTrackerSystem, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<TrainingsSession> trainingsSessionList = new ArrayList<>();

    public List <TrainingsSession> getTrainingsSessionList(){
        return new ArrayList<>(trainingsSessionList);
    }

    /**
     * Adds a new training session to the system.
     *
     * @param trainingsSession The session to be added.
     */
    @Override
    public void addTrainingsSession(TrainingsSession trainingsSession) {
        if (trainingsSession == null) {
            throw new IllegalArgumentException("TrainingsSession can not be null");
        }
        trainingsSessionList.add(trainingsSession);
    }

    /**
     * Updates the duration and distance of an existing training session.
     *
     * @param trainingsSession  The session to update.
     * @param durationInMinutes New duration in minutes.
     * @param distanceInKm      New distance in kilometers.
     */
    @Override
    public void setTrainingsSession(TrainingsSession trainingsSession, int durationInMinutes, float distanceInKm) {
        if (trainingsSession == null) {
            throw new IllegalArgumentException("TrainingsSession can not be null");
        }
        if (!trainingsSessionList.contains(trainingsSession)) {

            throw new IllegalStateException("TrainingsSession does not exist in the list");
        }
        trainingsSession.setDurationInMinute(durationInMinutes);
        trainingsSession.setDistanceInKm(distanceInKm);
    }

    /**
     * Removes a specific training session from the system.
     *
     * @param trainingsSession The session to remove.
     */
    @Override
    public void removeTrainingsSession(TrainingsSession trainingsSession) {
        if (trainingsSession == null) {
            throw new IllegalArgumentException("TrainingsSession can not be null");
        }
        if (!trainingsSessionList.contains(trainingsSession)) {

            throw new IllegalStateException("TrainingsSession does not exist in the list");
        }
        trainingsSessionList.remove(trainingsSession);
    }

    /**
     * Internal helper to filter sessions by user and date range.
     *
     * @param user      The user to filter for.
     * @param startDate The start of the date range.
     * @param endDate   The end of the date range.
     * @return A list of matching training sessions.
     */
    private List<TrainingsSession> getFilteredSessions(User user, LocalDate startDate, LocalDate endDate) {
        List<TrainingsSession> filteredTrainingsSession = new ArrayList<>();
        if (user == null || startDate == null || endDate == null || trainingsSessionList == null) {
            return filteredTrainingsSession;
        }
        for (TrainingsSession trainingsSession : trainingsSessionList) {
            if (trainingsSession.getUser().equals(user)
                    && !trainingsSession.getDate().isBefore(startDate)
                    && !trainingsSession.getDate().isAfter(endDate)) {
                filteredTrainingsSession.add(trainingsSession);
            }
        }
        return filteredTrainingsSession;
    }

    /**
     * Calculates the total training time for a specific user within a date range.
     *
     * @param user      The target user.
     * @param startDate Start date of evaluation.
     * @param endDate   End date of evaluation.
     * @return Total time in minutes.
     */
    @Override
    public int calculateTotalTrainingTimeInMinutes(User user, LocalDate startDate, LocalDate endDate) {
        List<TrainingsSession> filteredTrainingsSession = getFilteredSessions(user, startDate, endDate);
        int totalTimeInMinutes = 0;
        for (TrainingsSession trainingsSession : filteredTrainingsSession) {
            totalTimeInMinutes += trainingsSession.getDurationInMinute();
        }
        return totalTimeInMinutes;
    }

    /**
     * Calculates the total distance covered by a specific user within a date range.
     *
     * @param user      The target user.
     * @param startDate Start date of evaluation.
     * @param endDate   End date of evaluation.
     * @return Total distance in kilometers.
     */
    @Override
    public float calculateTotalDistanceInKm(User user, LocalDate startDate, LocalDate endDate) {
        List<TrainingsSession> filteredTrainingsSession = getFilteredSessions(user, startDate, endDate);
        float totalDistanceInKm = 0;
        for (TrainingsSession trainingsSession : filteredTrainingsSession) {
            totalDistanceInKm += trainingsSession.getDistanceInKm();
        }
        return totalDistanceInKm;
    }

    /**
     * Calculates the average speed of a user within a specific timeframe.
     *
     * @param user      The target user.
     * @param startDate Start date of evaluation.
     * @param endDate   End date of evaluation.
     * @return Average speed in km/h. Returns 0 if total time is 0.
     */
    @Override
    public float calculateAverageSpeedInKmH(User user, LocalDate startDate, LocalDate endDate) {
        int totalTimeInMinutes = calculateTotalTrainingTimeInMinutes(user, startDate, endDate);
        float totalDistanceInKm = calculateTotalDistanceInKm(user, startDate, endDate);

        if (totalTimeInMinutes == 0) {
            return 0;
        }

        float totalTimeInHours = totalTimeInMinutes / 60.0f;
        return totalDistanceInKm / totalTimeInHours;
    }

    /**
     * Aggregates activity data across all users based on a given criterion.
     *
     * @param startDate        Start date of aggregation.
     * @param endDate          End date of aggregation.
     * @param activityCriteria The criterion (Time or Distance).
     * @return A map associating users with their accumulated activity values.
     */
    private Map<User, Float> aggregatUserActivity(LocalDate startDate, LocalDate endDate, ActivityCriteriesEnum activityCriteria) {
        Map<User, Float> activityMap = new HashMap<>();

        if (trainingsSessionList == null || startDate == null || endDate == null) {
            return new HashMap<>();
        }

        for (TrainingsSession trainingsSession : trainingsSessionList) {
            if (!trainingsSession.getDate().isBefore(startDate) && !trainingsSession.getDate().isAfter(endDate)) {
                User user = trainingsSession.getUser();
                float value = (activityCriteria == ActivityCriteriesEnum.TIME)
                        ? trainingsSession.getDurationInMinute()
                        : trainingsSession.getDistanceInKm();

                activityMap.put(user, activityMap.getOrDefault(user, 0.0f) + value);
            }
        }
        return activityMap;
    }

    /**
     * Determines the user with the highest activity value from an activity map.
     *
     * @param activityMap Map of users and their activity values.
     * @return The user with the highest value, or null if the map is empty.
     */
    private User findWinner(Map<User, Float> activityMap) {
        if (activityMap == null || activityMap.isEmpty()) {
            return null;
        }

        User winnerUser = null;
        float maxValues = -1.0f;

        for (Map.Entry<User, Float> entry : activityMap.entrySet()) {
            if (entry.getValue() > maxValues) {
                maxValues = entry.getValue();
                winnerUser = entry.getKey();
            }
        }
        return winnerUser;
    }

    /**
     * Identifies the user who spent the most total time training in a date range.
     *
     * @param startDate Start date.
     * @param endDate   End date.
     * @return The most active user by time.
     */
    @Override
    public User findMostActiveUserByTotalTime(LocalDate startDate, LocalDate endDate) {
        Map<User, Float> activityMap = aggregatUserActivity(startDate, endDate, ActivityCriteriesEnum.TIME);

        return findWinner(activityMap);
    }


    /**
     * Identifies the user who covered the most total distance in a date range.
     *
     * @param startDate Start date.
     * @param endDate   End date.
     * @return The most active user by distance
     */
    @Override
    public User findMostActiveUserByTotalDistance(LocalDate startDate, LocalDate endDate) {
        Map<User, Float> activityMap = aggregatUserActivity(startDate, endDate, ActivityCriteriesEnum.DISTANCE);

        return findWinner(activityMap);
    }


    /**
     * Filters all sessions in the system based on multiple optional parameters.
     */
    @Override
    public List<TrainingsSession> filterTrainingsSession(TrainingsSessionFilter trainingsSessionFilter) {
        return trainingsSessionList.stream()
                .filter(trainingsSessionFilter.buildPredicate())
                .toList();
    }
}
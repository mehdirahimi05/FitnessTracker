package de.fherfurt.FitnessTrackerSystem.services.utils;

import de.fherfurt.FitnessTrackerSystem.models.*;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Author Filter Object
 *
 * @author Mehdi Rahimi
 * @see IFilterObject
 */
public class TrainingsSessionFilter implements IFilterObject<TrainingsSession> {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final User user;
    private final int minBurnedCalories;
    private final int maxBurnedCalories;
    private final ActivityType activityType;
    private final Difficulty difficulty;

    public TrainingsSessionFilter(LocalDate startDate,
                                  LocalDate endDate,
                                  User user,
                                  int minBurnedCalories,
                                  int maxBurnedCalories,
                                  ActivityType activityType,
                                  Difficulty difficulty) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.minBurnedCalories = minBurnedCalories;
        this.maxBurnedCalories = maxBurnedCalories;
        this.activityType = activityType;
        this.difficulty = difficulty;
    }

    /**
     * Creates new Builder Instance
     *
     * @return TrainingsSessionFilterBuilder
     */
    public static TrainingsSessionFilterBuilder builder() {
        return new TrainingsSessionFilterBuilder();
    }


    @Override
    public Predicate<TrainingsSession> buildPredicate() {
        return (trainingsSession -> {
            boolean matchesStartDate = (startDate == null
                    || !trainingsSession.getDate().isBefore(startDate));
            boolean matchesEndDate = (endDate == null
                    || !trainingsSession.getDate().isAfter(endDate));
            boolean matchesUser = (user == null
                    || trainingsSession.getUser().equals(user));
            boolean matchesMinBurnedCalories = (trainingsSession.getBurnedCalories() >= minBurnedCalories);
            boolean matchesMaxBurnedCalories = (trainingsSession.getBurnedCalories() <= maxBurnedCalories);
            boolean matchesActivityType = (activityType == null
                    || trainingsSession.getActivityType().getActivityTypeId() == activityType.getActivityTypeId());
            boolean matchesDifficulty = (difficulty == null
                    ||trainingsSession.getDifficulty().equals(difficulty));

            return matchesStartDate
                    && matchesEndDate
                    && matchesUser
                    && matchesMinBurnedCalories
                    && matchesMaxBurnedCalories
                    && matchesActivityType
                    && matchesDifficulty;

        });
    }

}


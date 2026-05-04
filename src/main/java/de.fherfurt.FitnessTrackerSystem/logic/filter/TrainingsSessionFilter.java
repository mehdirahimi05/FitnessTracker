package de.fherfurt.FitnessTrackerSystem.logic.filter;

import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;

import java.time.LocalDate;
import java.util.function.Predicate;


/**
 * Author Filter Object
 *
 * @see IFilterObject
 * @author Mehdi Rahimi
 */
public class TrainingsSessionFilter implements IFilterObject<TrainingsSession> {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final ActivityType activityType;
    private final User user;
    private final int minBurnedCalories;
    private final int maxBurnedCalories;

    public TrainingsSessionFilter(LocalDate startDate, LocalDate endDate, ActivityType activityType, User user, int minBurnedCalories, int maxBurnedCalories) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.activityType = activityType;
        this.user = user;
        this.minBurnedCalories = minBurnedCalories;
        this.maxBurnedCalories = maxBurnedCalories;
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
    public Predicate<TrainingsSession> buildPredicate(){
        return (trainingsSession -> {
            boolean matchesStartDate = (startDate == null
                    || !trainingsSession.getDate().isBefore(startDate));
            boolean matchesEndDate = (endDate == null
                    || !trainingsSession.getDate().isAfter(endDate));
            boolean matchesActivityType = (activityType == null
                    || trainingsSession.getActivityType().equals(activityType));
            boolean matchesUser = (user == null
                    || trainingsSession.getUser().equals(user));
            boolean matchesMinBurnedCalories = (trainingsSession.getBurnedCalories() >= minBurnedCalories);
            boolean matchesMaxBurnedCalories = (trainingsSession.getBurnedCalories() <= maxBurnedCalories);

            return matchesStartDate
                && matchesEndDate
                && matchesActivityType
                && matchesUser
                && matchesMinBurnedCalories
                && matchesMaxBurnedCalories;

        });
    }

}

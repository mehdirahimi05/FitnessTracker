package de.fherfurt.FitnessTrackerSystem.logic.filter;

import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import de.fherfurt.FitnessTrackerSystem.models.User;

import java.time.LocalDate;

/**
 * TrainingsSession Filter Builder
 *
 * @author Mehdi Rahimi
 */
public class TrainingsSessionFilterBuilder {
    private LocalDate startDate;
    private LocalDate endDate;
    private ActivityType activityType;
    private User user;
    private int minBurnedCalories;
    private int maxBurnedCalories = Integer.MAX_VALUE;


    public TrainingsSessionFilterBuilder withStartDate (LocalDate startDate){
        this.startDate = startDate;
         return this;
    }

    public TrainingsSessionFilterBuilder withEndDate (LocalDate endDate){
        this.endDate = endDate;
        return this;
    }

    public TrainingsSessionFilterBuilder withActivityType (ActivityType activityType){
        this.activityType = activityType;
        return this;
    }

    public TrainingsSessionFilterBuilder withUser (User user){
        this.user = user;
        return this;
    }

    public TrainingsSessionFilterBuilder withMinBurnedCalories (int minBurnedCalories){
        this.minBurnedCalories = minBurnedCalories;
        return this;
    }

    public TrainingsSessionFilterBuilder withMaxBurnedCalories (int maxBurnedCalories){
        this.maxBurnedCalories = maxBurnedCalories;
        return this;
    }

    public TrainingsSessionFilter build(){
        return new TrainingsSessionFilter(
                startDate,
                endDate,
                activityType,
                user,
                minBurnedCalories,
                maxBurnedCalories
        );
    }

}

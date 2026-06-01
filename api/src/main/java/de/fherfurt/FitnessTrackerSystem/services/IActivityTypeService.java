package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.ActivityType;

import java.util.List;
import java.util.Optional;

public interface IActivityTypeService {
    List<ActivityType> getAllActivityType();

    Optional<ActivityType> getActivityTypeById(int activityTypeId);

    boolean checkIsOwnActivityType(int activityTypeId);

    void addActivityType(ActivityType activityType);

    void updateActivityType(ActivityType activityType);

    void deleteActivityTypeById(int activityTypeId);
}

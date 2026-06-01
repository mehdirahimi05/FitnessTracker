package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.ActivityType;

import java.util.List;
import java.util.Optional;

public interface IActivityTypeRepository {
    void createActivityType(ActivityType activityType);

    List<ActivityType> getAllActivityType();

    Optional<ActivityType> getActivityTypeById(int activityTypeId);

    void updateActivityType(ActivityType activityType);

    void deleteActivityTypeById(int activityTypeId);
}

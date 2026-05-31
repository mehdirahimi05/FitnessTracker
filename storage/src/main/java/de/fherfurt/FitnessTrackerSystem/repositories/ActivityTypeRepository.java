package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.ActivityType;

import java.util.List;
import java.util.Optional;

public class ActivityTypeRepository implements IActivityTypeRepository {
    @Override
    public void createActivityType(ActivityType activityType) {

    }

    @Override
    public List<ActivityType> getAllActivityType() {
        return List.of();
    }

    @Override
    public Optional<ActivityType> getActivityTypeById(int activityTypeId) {
        return Optional.empty();
    }

    @Override
    public void updateActivityType(ActivityType activityType) {

    }

    @Override
    public void deleteActivityTypeById(int activityTypeId) {

    }
}

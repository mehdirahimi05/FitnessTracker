package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActivityTypeRepository implements IActivityTypeRepository {
    @Getter
    private final List<ActivityType> activityTypeList;

    public ActivityTypeRepository() {
        activityTypeList = new ArrayList<>();
    }

    @Override
    public void createActivityType(ActivityType activityType) {
        if (activityType == null) {
            throw new IllegalArgumentException("can not be null");
        }
        activityTypeList.add(activityType);
    }

    @Override
    public List<ActivityType> getAllActivityType() {
        return new ArrayList<>(activityTypeList);
    }

    @Override
    public Optional<ActivityType> getActivityTypeById(int activityTypeId) {
        return activityTypeList.stream()
                .filter(activityType -> activityType.getActivityTypeId() == activityTypeId)
                .findFirst();
    }

    @Override
    public void updateActivityType(ActivityType activityType) {
        if (activityType == null) {
            throw new IllegalArgumentException("can not be null");
        }
        var existingActivityType = getActivityTypeById(activityType.getActivityTypeId());
        if (existingActivityType.isEmpty()) {
            throw new IllegalStateException("does not exist");
        }
        activityTypeList.set(activityTypeList.indexOf(existingActivityType.get()), activityType);
    }

    @Override
    public void deleteActivityTypeById(int activityTypeId) {
        var foundActivityTypeId = getActivityTypeById(activityTypeId);
        if (foundActivityTypeId.isEmpty()) {
            throw new IllegalStateException("does not exist");
        }
        activityTypeList.remove(foundActivityTypeId.get());
    }
}

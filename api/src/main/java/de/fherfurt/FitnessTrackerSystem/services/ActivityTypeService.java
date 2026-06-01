package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import de.fherfurt.FitnessTrackerSystem.repositories.IActivityTypeRepository;

import java.util.List;
import java.util.Optional;

public class ActivityTypeService implements IActivityTypeService {
    private final IActivityTypeRepository activityTypeRepository;

    public ActivityTypeService(IActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    @Override
    public List<ActivityType> getAllActivityType() {
        return activityTypeRepository.getAllActivityType();
    }

    @Override
    public Optional<ActivityType> getActivityTypeById(int activityTypeId) {
        return activityTypeRepository.getActivityTypeById(activityTypeId);
    }

    @Override
    public boolean checkIsOwnActivityType(int activityTypeId) {
        var activityTypeToCheck = activityTypeRepository.getActivityTypeById(activityTypeId);
        if (activityTypeToCheck.isEmpty()) {
            return false;
        }
        return activityTypeToCheck.get().getActivityTypeId() == activityTypeId;
    }

    @Override
    public void addActivityType(ActivityType activityType) {
        activityTypeRepository.createActivityType(activityType);
    }

    @Override
    public void updateActivityType(ActivityType activityType) {
        activityTypeRepository.updateActivityType(activityType);
    }

    @Override
    public void deleteActivityTypeById(int activityTypeId) {
        activityTypeRepository.deleteActivityTypeById(activityTypeId);
    }
}

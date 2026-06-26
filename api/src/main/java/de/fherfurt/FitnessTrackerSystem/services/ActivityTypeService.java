package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import de.fherfurt.FitnessTrackerSystem.repositories.IActivityTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityTypeService implements IActivityTypeService {
    private final IActivityTypeRepository activityTypeRepository;

    public ActivityTypeService(IActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    @Override
    public List<ActivityType> getAllActivityType() {
        return activityTypeRepository.findAll();
    }

    @Override
    public Optional<ActivityType> getActivityTypeById(int activityTypeId) {
        return activityTypeRepository.findById(activityTypeId);
    }

    @Override
    public boolean checkIsOwnActivityType(int activityTypeId) {
        var activityTypeToCheck = activityTypeRepository.findById(activityTypeId);
        if (activityTypeToCheck.isEmpty()) {
            return false;
        }
        return activityTypeToCheck.get().getActivityTypeId() == activityTypeId;
    }

    @Override
    public ActivityType addActivityType(ActivityType activityType) {
        return activityTypeRepository.save(activityType);
    }

    @Override
    public void updateActivityType(ActivityType activityType) {
        activityTypeRepository.save(activityType);
    }

    @Override
    public void deleteActivityTypeById(int activityTypeId) {
        activityTypeRepository.deleteById(activityTypeId);
    }
}

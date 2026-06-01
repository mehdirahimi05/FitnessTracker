package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;
import de.fherfurt.FitnessTrackerSystem.repositories.IBodyMeasurementRepository;

import java.util.List;
import java.util.Optional;

public class BodyMeasurementService implements IBodyMeasurementService {
    private final IBodyMeasurementRepository bodyMeasurementRepository;

    public BodyMeasurementService(IBodyMeasurementRepository bodyMeasurementRepository) {
        this.bodyMeasurementRepository = bodyMeasurementRepository;
    }

    @Override
    public List<BodyMeasurement> getAllBodyMeasurement() {
        return bodyMeasurementRepository.getAllBodyMeasurement();
    }

    @Override
    public Optional<BodyMeasurement> getBodyMeasurementById(int bodyMeasurementId) {
        return bodyMeasurementRepository.getBodyMeasurementById(bodyMeasurementId);
    }

    @Override
    public boolean checkIsOwnBodyMeasurement(int bodyMeasurementId) {
        var bodyMeasurementToCheck = bodyMeasurementRepository.getBodyMeasurementById(bodyMeasurementId);
        if (bodyMeasurementToCheck.isEmpty()) {
            return false;
        }
        return bodyMeasurementToCheck.get().getBodyMeasurementId() == bodyMeasurementId;
    }

    @Override
    public void addBodyMeasurement(BodyMeasurement bodyMeasurement) {
        bodyMeasurementRepository.createBodyMeasurement(bodyMeasurement);
    }

    @Override
    public void updateBodyMeasurement(BodyMeasurement bodyMeasurement) {
        bodyMeasurementRepository.updateBodyMeasurement(bodyMeasurement);
    }

    @Override
    public void deleteBodyMeasurement(int bodyMeasurementId) {
        bodyMeasurementRepository.deleteBodyMeasurementById(bodyMeasurementId);
    }
}

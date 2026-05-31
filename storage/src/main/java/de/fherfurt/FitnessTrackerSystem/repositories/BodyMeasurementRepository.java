package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;

import java.util.List;
import java.util.Optional;

public class BodyMeasurementRepository implements IBodyMeasurementRepository {
    @Override
    public void createBodyMeasurement(BodyMeasurement bodyMeasurement) {

    }

    @Override
    public List<BodyMeasurement> getAllBodyMeasurement() {
        return List.of();
    }

    @Override
    public Optional<BodyMeasurement> getBodyMeasurementById(int bodyMeasurementId) {
        return Optional.empty();
    }

    @Override
    public void updateBodyMeasurement(BodyMeasurement bodyMeasurement) {

    }

    @Override
    public void deleteBodyMeasurementById(int bodyMeasurementId) {

    }
}

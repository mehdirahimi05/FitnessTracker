package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BodyMeasurementRepository implements IBodyMeasurementRepository {
    @Getter
    private final List<BodyMeasurement> bodyMeasurementList;

    public BodyMeasurementRepository() {
        bodyMeasurementList = new ArrayList<>();
    }

    @Override
    public void createBodyMeasurement(BodyMeasurement bodyMeasurement) {
        if (bodyMeasurement == null) {
            throw new IllegalArgumentException("can not be null");
        }
        bodyMeasurementList.add(bodyMeasurement);
    }

    @Override
    public List<BodyMeasurement> getAllBodyMeasurement() {
        return new ArrayList<>(bodyMeasurementList);
    }

    @Override
    public Optional<BodyMeasurement> getBodyMeasurementById(int bodyMeasurementId) {
        return bodyMeasurementList.stream()
                .filter(bodyMeasurement -> bodyMeasurement.getBodyMeasurementId() == bodyMeasurementId)
                .findFirst();
    }

    @Override
    public void updateBodyMeasurement(BodyMeasurement bodyMeasurement) {
        if (bodyMeasurement == null) {
            throw new IllegalArgumentException("can not be null");
        }
        var existingBodyMeasurement = getBodyMeasurementById(bodyMeasurement.getBodyMeasurementId());
        if (existingBodyMeasurement.isEmpty()) {
            throw new IllegalStateException("does not exist");
        }
        bodyMeasurementList.set(bodyMeasurementList.indexOf(existingBodyMeasurement.get()), bodyMeasurement);
    }

    @Override
    public void deleteBodyMeasurementById(int bodyMeasurementId) {
        var foundBodyMeasurementId = getBodyMeasurementById(bodyMeasurementId);
        if (foundBodyMeasurementId.isEmpty()) {
            throw new IllegalStateException("does not exist");
        }
        bodyMeasurementList.remove(foundBodyMeasurementId.get());
    }
}

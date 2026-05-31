package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;

import java.util.List;
import java.util.Optional;

public interface IBodyMeasurementRepository {
    void createBodyMeasurement(BodyMeasurement bodyMeasurement);

    List<BodyMeasurement> getAllBodyMeasurement();

    Optional<BodyMeasurement> getBodyMeasurementById(int bodyMeasurementId);

    void updateBodyMeasurement(BodyMeasurement bodyMeasurement);

    void deleteBodyMeasurementById(int bodyMeasurementId);
}

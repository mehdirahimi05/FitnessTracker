package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IBodyMeasurementService {
    List<BodyMeasurement> getAllBodyMeasurement();

    Optional<BodyMeasurement> getBodyMeasurementById(int bodyMeasurementId);

    boolean checkIsOwnBodyMeasurement(int bodyMeasurementId);

    void addBodyMeasurement(BodyMeasurement bodyMeasurement);

    void updateBodyMeasurement(BodyMeasurement bodyMeasurement);

    void deleteBodyMeasurement(int bodyMeasurementId);

    float calculateBmi(float weightInKg, float heightInMeter);

    Optional<BodyMeasurement> getLatestBodyMeasurement(int userId);

    List<BodyMeasurement> getBodyMeasurementHistory(int userId, LocalDate startDate, LocalDate endDate);

    List<Float> getWeightProgress(int userId, LocalDate startDate, LocalDate endDate);

    List<Integer> getBodyFatPercentageProgress(int userId, LocalDate startDate, LocalDate endDate);
}

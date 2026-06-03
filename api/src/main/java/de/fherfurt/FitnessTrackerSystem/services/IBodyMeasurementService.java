package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;
import de.fherfurt.FitnessTrackerSystem.models.User;

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

    Optional<BodyMeasurement> getLatestBodyMeasurement(User user);

    List<BodyMeasurement> getBodyMeasurementHistory(User user, LocalDate startDate, LocalDate endDate);

    List<Float> getWeightProgress(User user, LocalDate startDate, LocalDate endDate);

    List<Integer> getBodyFatPercentageProgress(User user, LocalDate startDate, LocalDate endDate);
}

package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;
import de.fherfurt.FitnessTrackerSystem.repositories.IBodyMeasurementRepository;

import java.time.LocalDate;
import java.util.Comparator;
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

    @Override
    public float calculateBmi(float weightInKg, float heightInMeter) {
        if (heightInMeter <= 0 || weightInKg <= 0) {
            throw new IllegalArgumentException("Height and weight must be greater than 0");
        }
        return weightInKg / (heightInMeter * heightInMeter);
    }

    @Override
    public Optional<BodyMeasurement> getLatestBodyMeasurement(int userId) {
        if (userId == 0) {
            throw new IllegalArgumentException("can not be null");
        }
        List<BodyMeasurement> filterBodyMeasurement = bodyMeasurementRepository.getAllBodyMeasurement().stream()
                .filter(bodyMeasurement -> bodyMeasurement.getUserId() == userId)
                .sorted(Comparator.comparing(BodyMeasurement::getMeasuredAt).reversed())
                .toList();
        if (filterBodyMeasurement.isEmpty()) {
            throw new IllegalStateException("does not exist");
        }
        return Optional.of(filterBodyMeasurement.get(0));
    }

    @Override
    public List<BodyMeasurement> getBodyMeasurementHistory(int userId, LocalDate startDate, LocalDate endDate) {
        if (userId == 0 || startDate == null || endDate == null) {
            throw new IllegalArgumentException("can not be null");
        }
        List<BodyMeasurement> filterBodyMeasurement = bodyMeasurementRepository.getAllBodyMeasurement().stream()
                .filter(bodyMeasurement -> bodyMeasurement.getUserId() == userId)
                .filter(bodyMeasurement -> !bodyMeasurement.getMeasuredAt().isBefore(startDate))
                .filter(bodyMeasurement -> !bodyMeasurement.getMeasuredAt().isAfter(endDate))
                .toList();
        if (filterBodyMeasurement.isEmpty()) {
            throw new IllegalStateException("does not exits");
        }
        return filterBodyMeasurement;
    }

    @Override
    public List<Float> getWeightProgress(int userId, LocalDate startDate, LocalDate endDate) {
        if (userId == 0 || startDate == null || endDate == null) {
            throw new IllegalArgumentException("can not be null");
        }
        List<Float> filterBodyMeasurement = bodyMeasurementRepository.getAllBodyMeasurement().stream()
                .filter(bodyMeasurement -> bodyMeasurement.getUserId() == userId)
                .filter(bodyMeasurement -> !bodyMeasurement.getMeasuredAt().isBefore(startDate))
                .filter(bodyMeasurement -> !bodyMeasurement.getMeasuredAt().isAfter(endDate))
                .map(BodyMeasurement::getWeightInKg)
                .toList();
        if (filterBodyMeasurement.isEmpty()) {
            throw new IllegalStateException("does not exits");
        }
        return filterBodyMeasurement;
    }

    @Override
    public List<Integer> getBodyFatPercentageProgress(int userId, LocalDate startDate, LocalDate endDate) {
        if (userId == 0 || startDate == null || endDate == null) {
            throw new IllegalArgumentException("can not be null");
        }
        List<Integer> filterBodyMeasurement = bodyMeasurementRepository.getAllBodyMeasurement().stream()
                .filter(bodyMeasurement -> bodyMeasurement.getUserId() == userId)
                .filter(bodyMeasurement -> !bodyMeasurement.getMeasuredAt().isBefore(startDate))
                .filter(bodyMeasurement -> !bodyMeasurement.getMeasuredAt().isAfter(endDate))
                .map(BodyMeasurement::getBodyFatPercentage)
                .toList();
        if (filterBodyMeasurement.isEmpty()) {
            throw new IllegalStateException("does not exits");
        }
        return filterBodyMeasurement;
    }
}

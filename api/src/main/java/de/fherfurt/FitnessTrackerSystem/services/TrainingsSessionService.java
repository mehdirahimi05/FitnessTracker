package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.repositories.ITrainingsSessionRepository;
import de.fherfurt.FitnessTrackerSystem.services.utils.TrainingsSessionFilter;
import de.fherfurt.FitnessTrackerSystem.services.utils.ActivityCriteriesEnum;

import java.time.LocalDate;
import java.util.*;

public class TrainingsSessionService implements ITrainingsSessionService {
    private final ITrainingsSessionRepository trainingsSessionRepository;

    public TrainingsSessionService(ITrainingsSessionRepository trainingsSessionRepository) {
        this.trainingsSessionRepository = trainingsSessionRepository;
    }

    @Override
    public List<TrainingsSession> getAllTrainingsSessions() {
        return trainingsSessionRepository.getAllTrainingsSessions();
    }

    @Override
    public Optional<TrainingsSession> getTrainingsSessionById(int trainingsSessionId) {
        return trainingsSessionRepository.getTrainingsSessionById(trainingsSessionId);
    }

    @Override
    public boolean checkIsOwnTrainingsSession(int trainingsSessionId) {
        var trainingsSessionToCheck = trainingsSessionRepository.getTrainingsSessionById(trainingsSessionId);
        if (trainingsSessionToCheck.isEmpty()) {
            return false;
        }
        return trainingsSessionToCheck.get().getTrainingsSessionId() == trainingsSessionId;
    }

    @Override
    public void addTrainingsSession(TrainingsSession newTrainingsSession) {
        trainingsSessionRepository.createTrainingsSession(newTrainingsSession);
    }

    @Override
    public void updateTrainingsSession(TrainingsSession updatedTrainingsSession) {
        trainingsSessionRepository.updateTrainingsSession(updatedTrainingsSession);
    }

    @Override
    public void deleteTrainingsSessionById(int trainingsSessionId) {
        trainingsSessionRepository.deleteTrainingsSessionById(trainingsSessionId);
    }

    @Override
    public int getTotalTrainingTimeByUser(User user, LocalDate startDate, LocalDate endDate) {
        if (user == null || startDate == null || endDate == null){
            throw new IllegalArgumentException("can not be calculated");
        }
        return trainingsSessionRepository.getAllTrainingsSessions().stream()
                .filter(trainingsSession -> trainingsSession.getUser().equals(user))
                .filter(trainingsSession -> !trainingsSession.getDate().isBefore(startDate))
                .filter(trainingsSession -> !trainingsSession.getDate().isAfter(endDate))
                .mapToInt(TrainingsSession :: getDurationInMinute)
                .sum();
    }

    @Override
    public float getTotalDistanceByUser(User user, LocalDate startDate, LocalDate endDate) {
        if (user == null || startDate == null || endDate == null){
            throw new IllegalArgumentException("can not be calculated");
        }

        return(float) trainingsSessionRepository.getAllTrainingsSessions().stream()
                .filter(trainingsSession -> trainingsSession.getUser().equals(user))
                .filter(trainingsSession -> !trainingsSession.getDate().isBefore(startDate))
                .filter(trainingsSession -> !trainingsSession.getDate().isAfter(endDate))
                .mapToDouble(TrainingsSession :: getDistanceInKm)
                .sum();
    }

    @Override
    public float getAverageSpeed(TrainingsSession trainingsSession) {
        int totalTimeInMinutes = trainingsSession.getDurationInMinute();
        float totalDistanceInKm = trainingsSession.getDistanceInKm();

        if (totalTimeInMinutes == 0) {
            return 0;
        }

        float totalTimeInHours = totalTimeInMinutes / 60.0f;
        return totalDistanceInKm / totalTimeInHours;
    }

    /**
     * Aggregates activity data across all users based on a given criterion.
     *
     * @param startDate        Start date of aggregation.
     * @param endDate          End date of aggregation.
     * @param activityCriteria The criterion (Time or Distance).
     * @return A map associating users with their accumulated activity values.
     */
    private Map<User, Float> aggregatUserActivity(LocalDate startDate, LocalDate endDate, ActivityCriteriesEnum activityCriteria) {
        List<TrainingsSession> trainingsSessionList = trainingsSessionRepository.getAllTrainingsSessions();
        Map<User, Float> activityMap = new HashMap<>();

        for (TrainingsSession trainingsSession : trainingsSessionList) {
            if (!trainingsSession.getDate().isBefore(startDate) && !trainingsSession.getDate().isAfter(endDate)) {
                User user = trainingsSession.getUser();
                float value = (activityCriteria == ActivityCriteriesEnum.TIME)
                        ? trainingsSession.getDurationInMinute()
                        : trainingsSession.getDistanceInKm();

                activityMap.put(user, activityMap.getOrDefault(user, 0.0f) + value);
            }
        }
        return activityMap;
    }

    /**
     * Determines the user with the highest activity value from an activity map.
     *
     * @param activityMap Map of users and their activity values.
     * @return The user with the highest value, or null if the map is empty.
     */
    private User findWinner(Map<User, Float> activityMap) {
        return activityMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    @Override
    public User getMostActiveUserByTime(LocalDate startDate, LocalDate endDate) {
        Map<User, Float> activityMap = aggregatUserActivity(startDate, endDate, ActivityCriteriesEnum.TIME);

        return findWinner(activityMap);
    }

    @Override
    public User getMostActiveUserByDistance(LocalDate startDate, LocalDate endDate) {
        Map<User, Float> activityMap = aggregatUserActivity(startDate, endDate, ActivityCriteriesEnum.DISTANCE);

        return findWinner(activityMap);
    }

    @Override
    public List<TrainingsSession> filterTrainingsSession(TrainingsSessionFilter trainingsSessionFilter) {
        List<TrainingsSession> trainingsSessionList = trainingsSessionRepository.getAllTrainingsSessions();
        return trainingsSessionList.stream()
                .filter(trainingsSessionFilter.buildPredicate())
                .toList();
    }
}

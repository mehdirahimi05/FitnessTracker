package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.repositories.ITrainingsSessionRepository;
import de.fherfurt.FitnessTrackerSystem.services.utils.TrainingsSessionFilter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    private Map<User, Long> aggregatUserTrainingsSessions(LocalDate startDate, LocalDate endDate) {
        Map<User, Long> trainingsSessionMap = new HashMap<>();
        List<TrainingsSession> trainingsSessionList = trainingsSessionRepository.getAllTrainingsSessions();

        if (trainingsSessionList == null || startDate == null || endDate == null) {
            return new HashMap<>();
        }

        for (TrainingsSession trainingsSession : trainingsSessionList) {
            if (!trainingsSession.getDate().isBefore(startDate) && !trainingsSession.getDate().isAfter(endDate)) {
                User user = trainingsSession.getUser();
                trainingsSessionMap.put(user, trainingsSessionMap.getOrDefault(user, 0L) + 1L);
            }
        }
        return trainingsSessionMap;
    }

    private User findWinner(Map<User, Long> trainingsSessionMap) {
        if (trainingsSessionMap == null || trainingsSessionMap.isEmpty()) {
            throw new IllegalStateException("map is empty");
        }
        User winnerUser = null;
        long maxValues = -1L;

        for (Map.Entry<User, Long> entry : trainingsSessionMap.entrySet()) {
            if (entry.getValue() > maxValues) {
                maxValues = entry.getValue();
                winnerUser = entry.getKey();
            }
        }
        return winnerUser;
    }

    @Override
    public User getMostActiveUserByAmountOfTrainingsSessions(LocalDate startDate, LocalDate endDate) {
        Map<User, Long> trainingsSessionMap = aggregatUserTrainingsSessions(startDate, endDate);

        return findWinner(trainingsSessionMap);
    }


    @Override
    public List<TrainingsSession> filterTrainingsSession(TrainingsSessionFilter trainingsSessionFilter) {
        List<TrainingsSession> trainingsSessionList = trainingsSessionRepository.getAllTrainingsSessions();
        return trainingsSessionList.stream()
                .filter(trainingsSessionFilter.buildPredicate())
                .toList();
    }
}

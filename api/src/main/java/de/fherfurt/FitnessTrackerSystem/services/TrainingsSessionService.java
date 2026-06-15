package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.TrainingsSessionSummary;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.repositories.ITrainingsSessionRepository;
import de.fherfurt.FitnessTrackerSystem.services.utils.TrainingsSessionFilter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TrainingsSessionService implements ITrainingsSessionService {
    private final ITrainingsSessionRepository trainingsSessionRepository;

    public TrainingsSessionService(ITrainingsSessionRepository trainingsSessionRepository) {
        this.trainingsSessionRepository = trainingsSessionRepository;
    }

    @Override
    public List<TrainingsSession> getAllTrainingsSessions() {
        return trainingsSessionRepository.findAll();
    }

    @Override
    public Optional<TrainingsSession> getTrainingsSessionById(int trainingsSessionId) {
        return trainingsSessionRepository.findById(trainingsSessionId);
    }

    @Override
    public boolean checkIsOwnTrainingsSession(int trainingsSessionId) {
        var trainingsSessionToCheck = trainingsSessionRepository.findById(trainingsSessionId);
        if (trainingsSessionToCheck.isEmpty()) {
            return false;
        }
        return trainingsSessionToCheck.get().getTrainingsSessionId() == trainingsSessionId;
    }

    @Override
    public void addTrainingsSession(TrainingsSession newTrainingsSession) {
        trainingsSessionRepository.save(newTrainingsSession);
    }

    @Override
    public void updateTrainingsSession(TrainingsSession updatedTrainingsSession) {
        trainingsSessionRepository.save(updatedTrainingsSession);
    }

    @Override
    public void deleteTrainingsSessionById(int trainingsSessionId) {
        trainingsSessionRepository.deleteById(trainingsSessionId);
    }

    private Map<User, Long> aggregatUserTrainingsSessions(LocalDate startDate, LocalDate endDate) {
        Map<User, Long> trainingsSessionMap = new HashMap<>();
        List<TrainingsSession> trainingsSessionList = trainingsSessionRepository.findAll();

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
        List<TrainingsSession> trainingsSessionList = trainingsSessionRepository.findAll();
        return trainingsSessionList.stream()
                .filter(trainingsSessionFilter.buildPredicate())
                .toList();
    }

    @Override
    public int getTrainingSessionStreak(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        List<TrainingsSession> filteredTrainingsSession = trainingsSessionRepository.findAll().stream()
                .filter(trainingsSession -> trainingsSession.getUser().equals(user))
                .sorted(Comparator.comparing(TrainingsSession::getDate))
                .toList();
        if (filteredTrainingsSession.isEmpty()) {
            throw new IllegalStateException("does not exist");
        }
        int currentStreak = 1;
        int longestStreak = 1;
        for (int i = 1; i < filteredTrainingsSession.size(); i++) {
            LocalDate currentDate = filteredTrainingsSession.get(i).getDate();
            LocalDate previousDate = filteredTrainingsSession.get(i - 1).getDate();

            if (currentDate.equals(previousDate.plusDays(1))) {
                currentStreak++;
            } else {
                currentStreak = 1;
            }
            longestStreak = Math.max(longestStreak, currentStreak);
        }

        return longestStreak;
    }

    @Override
    public TrainingsSessionSummary getDailyTrainingsSessionSummary(User user, LocalDate date) {
        if (user == null || date == null) {
            throw new IllegalArgumentException("cannot be null");
        }
        List<TrainingsSession> filteredTrainingsSessionSummary = trainingsSessionRepository.findAll()
                .stream()
                .filter(trainingsSession -> trainingsSession.getUser().equals(user))
                .filter(trainingsSession -> trainingsSession.getDate().equals(date))
                .toList();
        if (filteredTrainingsSessionSummary.isEmpty()) {
            throw new IllegalStateException("does not exist");
        }
        List<TrainingsSession> trainingsSessions = filteredTrainingsSessionSummary;
        int totalDurationInMinutes = filteredTrainingsSessionSummary.stream()
                .mapToInt(TrainingsSession::getDurationInMinute)
                .sum();
        int totalCaloriesBurned = filteredTrainingsSessionSummary.stream()
                .mapToInt(TrainingsSession::getBurnedCalories)
                .sum();
        return new TrainingsSessionSummary(
                trainingsSessions,
                totalDurationInMinutes,
                totalCaloriesBurned
        );
    }
}

package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.TrainingsSessionSummary;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.services.utils.TrainingsSessionFilter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITrainingsSessionService {
    List<TrainingsSession> getAllTrainingsSessions();

    Optional<TrainingsSession> getTrainingsSessionById(int trainingsSessionId);

    boolean checkIsOwnTrainingsSession(int trainingsSessionId);

    void addTrainingsSession(TrainingsSession newTrainingsSession);

    void updateTrainingsSession(TrainingsSession updatedTrainingsSession);

    void deleteTrainingsSessionById(int trainingsSessionId);

    User getMostActiveUserByAmountOfTrainingsSessions(LocalDate startDate, LocalDate endDate);

    List<TrainingsSession> filterTrainingsSession(TrainingsSessionFilter trainingsSessionFilter);

    int getTrainingSessionStreak(User user);

    TrainingsSessionSummary getDailyTrainingsSessionSummary(User user, LocalDate date);
}

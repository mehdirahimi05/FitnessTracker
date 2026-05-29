package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
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

    int getTotalTrainingTimeByUser(User user, LocalDate startDate, LocalDate endDate);

    float getTotalDistanceByUser(User user, LocalDate startDate, LocalDate endDate);

    float getAverageSpeed(TrainingsSession trainingsSession);

    User getMostActiveUserByTime(LocalDate startDate, LocalDate endDate);

    User getMostActiveUserByDistance(LocalDate startDate, LocalDate endDate);

    List<TrainingsSession> filterTrainingsSession(TrainingsSessionFilter trainingsSessionFilter);
}

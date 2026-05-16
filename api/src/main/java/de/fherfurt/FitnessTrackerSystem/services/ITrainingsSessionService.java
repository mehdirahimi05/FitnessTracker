package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;

import java.time.LocalDate;
import java.util.List;

public interface ITrainingsSessionService {
    List<TrainingsSession> getAllTrainingsSessions();
    List<TrainingsSession> getTrainingsSessionByDate(LocalDate date);
    List<TrainingsSession> getTrainingsSessionByUser(User user);
    boolean checkIsOwnTrainingsSession(TrainingsSession trainingsSession, User userName);
    void addTrainingsSession(TrainingsSession newTrainingsSession);
    void updateTrainingsSession(TrainingsSession updatedTrainingsSession);
    void deleteTrainingsSession(TrainingsSession TrainingsSession);
}

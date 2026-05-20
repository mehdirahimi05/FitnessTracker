package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;

import java.util.List;
import java.util.Optional;

public interface ITrainingsSessionService {
    List<TrainingsSession> getAllTrainingsSessions();
    Optional<TrainingsSession> getTrainingsSessionByTrainingsSessionID(int trainingsSessionId);
    boolean checkIsOwnTrainingsSession(TrainingsSession trainingsSession, User userName);
    void addTrainingsSession(TrainingsSession newTrainingsSession);
    void updateTrainingsSession(TrainingsSession updatedTrainingsSession);
    void deleteTrainingsSessionByTrainingsSessionId(int trainingsSessionId);
}

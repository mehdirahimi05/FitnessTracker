package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;

import java.util.List;
import java.util.Optional;

public interface ITrainingsSessionRepository {
    void createTrainingsSession(TrainingsSession trainingsSession);
    List<TrainingsSession> getAllTrainingsSessions();
    Optional<TrainingsSession> getTrainingsSessionByTrainingsSessionId(int trainingsSessionId);
    void updateTrainingsSession(TrainingsSession trainingsSession);
    void deleteTrainingsSessionByTrainingsSessionId(int trainingsSessionId);
}

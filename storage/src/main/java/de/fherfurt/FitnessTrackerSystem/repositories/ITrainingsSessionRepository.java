package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;

import java.util.List;
import java.util.Optional;

public interface ITrainingsSessionRepository {
    void createTrainingsSession(TrainingsSession trainingsSession);
    List<TrainingsSession> getAllTrainingsSessions();
    Optional<TrainingsSession> getTrainingsSessionById(int trainingsSessionId);
    void updateTrainingsSession(TrainingsSession trainingsSession);
    void deleteTrainingsSessionById(int trainingsSessionId);
}

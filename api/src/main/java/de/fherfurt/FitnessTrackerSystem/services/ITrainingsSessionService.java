package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;

import java.util.List;
import java.util.Optional;

public interface ITrainingsSessionService {
    List<TrainingsSession> getAllTrainingsSessions();

    Optional<TrainingsSession> getTrainingsSessionById(int trainingsSessionId);

    boolean checkIsOwnTrainingsSession(int trainingsSessionId);

    void addTrainingsSession(TrainingsSession newTrainingsSession);

    void updateTrainingsSession(TrainingsSession updatedTrainingsSession);

    void deleteTrainingsSessionById(int trainingsSessionId);
}

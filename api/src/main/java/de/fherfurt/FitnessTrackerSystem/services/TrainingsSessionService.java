package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.repositories.ITrainingsSessionRepository;

import java.util.List;
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
}

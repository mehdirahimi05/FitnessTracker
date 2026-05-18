package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;

import java.time.LocalDate;
import java.util.List;

public class TrainingsSessionRepository implements ITrainingsSessionRepository{
    @Override
    public List<TrainingsSession> getAllTrainingsSessions() {
        return List.of();
    }

    @Override
    public List<TrainingsSession> getTrainingsSessionByDate(LocalDate date) {
        return List.of();
    }

    @Override
    public List<TrainingsSession> getTrainingsSessionByUser(User user) {
        return List.of();
    }

    @Override
    public void createTrainingsSession(TrainingsSession trainingsSession) {

    }

    @Override
    public void updateTrainingsSession(TrainingsSession trainingsSession) {

    }

    @Override
    public void deleteTrainingsSession(TrainingsSession trainingsSession) {

    }
}

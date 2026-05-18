package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;

import java.time.LocalDate;
import java.util.List;

public interface ITrainingsSessionRepository {
    List<TrainingsSession> getAllTrainingsSessions();
    List<TrainingsSession> getTrainingsSessionByDate(LocalDate date);
    List<TrainingsSession> getTrainingsSessionByUser(User user);
    void createTrainingsSession(TrainingsSession trainingsSession);
    void updateTrainingsSession(TrainingsSession trainingsSession);
    void deleteTrainingsSession(TrainingsSession trainingsSession);

}

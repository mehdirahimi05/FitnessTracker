package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainingsSessionRepository implements ITrainingsSessionRepository{
    @Getter
    private final List<TrainingsSession> trainingsSessionList;

    public TrainingsSessionRepository(){
        trainingsSessionList = new ArrayList<>();
    }

    @Override
    public void createTrainingsSession(TrainingsSession trainingsSession) {
        if (trainingsSession == null){
            throw new IllegalArgumentException("trainingsSessionList can not be null");
        }
        trainingsSessionList.add(trainingsSession);
    }

    @Override
    public List<TrainingsSession> getAllTrainingsSessions() {
        return new ArrayList<>(trainingsSessionList);
    }

    @Override
    public Optional<TrainingsSession> getTrainingsSessionByTrainingsSessionId(int trainingsSessionId) {
        return trainingsSessionList.stream()
                .filter(trainingsSession -> trainingsSession.getTrainingsSessionId() == trainingsSessionId)
                .findFirst();
    }


    @Override
    public void updateTrainingsSession(TrainingsSession trainingsSession) {
        if (trainingsSession == null){
            throw new IllegalArgumentException("trainingsSessionList can not be null");
        }
        var existingTrainingsSession = getTrainingsSessionByTrainingsSessionId(trainingsSession.getTrainingsSessionId());
        if (existingTrainingsSession.isEmpty()){
            throw new IllegalStateException("trainingsSession does not exist");
        }
        trainingsSessionList.set(trainingsSessionList.indexOf(existingTrainingsSession.get()),trainingsSession);
    }

    public void deleteTrainingsSessionByTrainingsSessionId(int trainingsSessionId) {
        var foundTrainingsSessionId = getTrainingsSessionByTrainingsSessionId((trainingsSessionId));
        if (foundTrainingsSessionId.isEmpty()){
            throw new IllegalStateException("foundTrainingsSessionId does not exist");
        }
        trainingsSessionList.remove(foundTrainingsSessionId.get());
    }
}

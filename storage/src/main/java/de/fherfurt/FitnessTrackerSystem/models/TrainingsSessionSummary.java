package de.fherfurt.FitnessTrackerSystem.models;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TrainingsSessionSummary {
    List<TrainingsSession> trainingsSessions;
    int totalDurationInMinutes;
    int totalCaloriesBurned;
}

package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Exercise {
    private int exerciseId;
    private String exerciseName;
    private List<ActivityType> activityTypes;
}

package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ActivityType {
    private int activityTypeId;
    private String name;           // "Running", "Fitness", "Yoga"
}

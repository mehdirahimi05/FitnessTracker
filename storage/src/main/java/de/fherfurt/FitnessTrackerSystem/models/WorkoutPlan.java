package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WorkoutPlan {
    private int workoutPlanId;
    private String name;
    private List<WorkoutPlanExercise> exercises;
}

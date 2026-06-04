package de.fherfurt.FitnessTrackerSystem.services.contoller.request;


import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddExerciseRequest {
    private WorkoutPlan workoutPlan;
    private Exercise exercise;
    private int sets;
    private int repetitions;
}

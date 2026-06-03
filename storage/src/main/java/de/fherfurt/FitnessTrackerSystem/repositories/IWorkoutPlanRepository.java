package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWorkoutPlanRepository extends JpaRepository<WorkoutPlan, Integer> {
}

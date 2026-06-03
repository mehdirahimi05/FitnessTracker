package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IExerciseRepository extends JpaRepository<Exercise, Integer> {
}

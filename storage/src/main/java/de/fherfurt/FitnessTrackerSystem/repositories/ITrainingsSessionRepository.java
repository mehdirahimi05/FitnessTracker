package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrainingsSessionRepository extends JpaRepository<TrainingsSession, Integer> {
}

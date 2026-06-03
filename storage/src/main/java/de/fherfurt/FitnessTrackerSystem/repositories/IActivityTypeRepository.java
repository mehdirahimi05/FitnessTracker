package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IActivityTypeRepository extends JpaRepository<ActivityType, Integer> {
}

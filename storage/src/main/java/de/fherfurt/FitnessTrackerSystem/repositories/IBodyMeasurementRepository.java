package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IBodyMeasurementRepository extends JpaRepository<BodyMeasurement, Integer> {
}

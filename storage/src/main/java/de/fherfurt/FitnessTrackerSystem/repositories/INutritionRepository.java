package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INutritionRepository extends JpaRepository<Nutrition, Integer> {
}

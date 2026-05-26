package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.Nutrition;

import java.util.List;
import java.util.Optional;

public interface INutritionRepository {
    void createNutrition(Nutrition nutrition);
    List<Nutrition> getAllNutrition();
    Optional<Nutrition> getNutritionById(int nutritionId);
    void updateNutrition(Nutrition nutrition);
    void deleteNutritionById(int nutritionId);
}

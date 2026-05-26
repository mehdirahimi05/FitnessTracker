package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.Nutrition;

import java.util.List;
import java.util.Optional;

public interface INutritionService {
    List<Nutrition> getAllNutrition();
    Optional<Nutrition> getNutritionById(int nutritionId);
    boolean checkIsOwnNutrition(int nutritionId);
    void addNutrition(Nutrition newNutrition);
    void updateNutrition(Nutrition updatedNutrition);
    void deleteNutritionById(int nutritionId);
}

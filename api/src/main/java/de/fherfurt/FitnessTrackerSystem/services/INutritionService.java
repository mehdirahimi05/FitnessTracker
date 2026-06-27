package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.Nutrition;
import de.fherfurt.FitnessTrackerSystem.models.NutritionSummary;
import de.fherfurt.FitnessTrackerSystem.models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface INutritionService {
    List<Nutrition> getAllNutrition();

    Optional<Nutrition> getNutritionById(int nutritionId);

    boolean checkIsOwnNutrition(int nutritionId);

    Nutrition addNutrition(Nutrition newNutrition);

    void updateNutrition(Nutrition updatedNutrition);

    void deleteNutritionById(int nutritionId);

    NutritionSummary getDailyNutritionSummary(User user, LocalDate date);
}

package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.Nutrition;
import de.fherfurt.FitnessTrackerSystem.repositories.INutritionRepository;

import java.util.List;
import java.util.Optional;

public class NutritionService implements INutritionService {
    private final INutritionRepository nutritionRepository;

    public NutritionService(INutritionRepository nutritionRepository) {
        this.nutritionRepository = nutritionRepository;
    }

    @Override
    public List<Nutrition> getAllNutrition() {
        return nutritionRepository.getAllNutrition();
    }

    @Override
    public Optional<Nutrition> getNutritionById(int nutritionId) {
        return nutritionRepository.getNutritionById(nutritionId);
    }

    @Override
    public boolean checkIsOwnNutrition(int nutritionId) {
        var nutritionToCheck = nutritionRepository.getNutritionById(nutritionId);
        if (nutritionToCheck.isEmpty()) {
            return false;
        }
        return nutritionToCheck.get().getNutritionId() == nutritionId;
    }

    @Override
    public void addNutrition(Nutrition newNutrition) {
        nutritionRepository.createNutrition(newNutrition);
    }

    @Override
    public void updateNutrition(Nutrition updatedNutrition) {
        nutritionRepository.updateNutrition(updatedNutrition);
    }

    @Override
    public void deleteNutritionById(int nutritionId) {
        nutritionRepository.deleteNutritionById(nutritionId);
    }
}

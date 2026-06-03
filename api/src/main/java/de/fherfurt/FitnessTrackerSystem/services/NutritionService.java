package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.Nutrition;
import de.fherfurt.FitnessTrackerSystem.models.NutritionSummary;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.repositories.INutritionRepository;

import java.time.LocalDate;
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

    @Override
    public NutritionSummary getDailyNutritionSummary(User user, LocalDate date) {
        if (user == null || date == null) {
            throw new IllegalArgumentException("cannot be null");
        }
        List<Nutrition> filteredNutritionSummary = nutritionRepository.getAllNutrition().stream()
                .filter(nutrition -> nutrition.getUser().equals(user))
                .filter(nutrition -> nutrition.getDate().equals(date))
                .toList();
        if (filteredNutritionSummary.isEmpty()) {
            throw new IllegalStateException("does not exist");
        }

        int totalCalories = filteredNutritionSummary.stream()
                .mapToInt(Nutrition::getCalories)
                .sum();
        int totalProteinInGram = filteredNutritionSummary.stream()
                .mapToInt(Nutrition::getProteinInGram)
                .sum();
        int totalCarbohydratesInGram = filteredNutritionSummary.stream()
                .mapToInt(Nutrition::getCarbohydratesInGram)
                .sum();
        int totalFatInGram = filteredNutritionSummary.stream()
                .mapToInt(Nutrition::getFatInGram)
                .sum();

        return new NutritionSummary(
                totalCalories,
                totalProteinInGram,
                totalCarbohydratesInGram,
                totalFatInGram
        );
    }
}

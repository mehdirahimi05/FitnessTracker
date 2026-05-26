package de.fherfurt.FitnessTrackerSystem.repositories;

import de.fherfurt.FitnessTrackerSystem.models.Nutrition;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NutritionRepository implements INutritionRepository {
    @Getter
    private final List<Nutrition> nutritionList;

    public NutritionRepository(){
        nutritionList = new ArrayList<>();
    }

    @Override
    public void createNutrition(Nutrition nutrition) {
        if (nutrition == null){
            throw new IllegalArgumentException("can not be null");
        }
        nutritionList.add(nutrition);
    }

    @Override
    public List<Nutrition> getAllNutrition() {
        return new  ArrayList<>(nutritionList);
    }

    @Override
    public Optional<Nutrition> getNutritionById(int nutritionId) {
        return nutritionList.stream()
                .filter(nutritionEntry -> nutritionEntry.getNutritionId() == nutritionId)
                .findFirst();
    }

    @Override
    public void updateNutrition(Nutrition nutrition) {
        if (nutrition == null){
            throw new IllegalArgumentException("can not be null");
        }
        var existingNutrition = getNutritionById(nutrition.getNutritionId());
        if (existingNutrition.isEmpty()){
            throw new IllegalStateException("nutrition not found");
        }
        nutritionList.set(nutritionList.indexOf(existingNutrition.get()), nutrition);
    }

    @Override
    public void deleteNutritionById(int nutritionId) {
        var foundNutrition = getNutritionById(nutritionId);
        if (foundNutrition.isEmpty()){
            throw new IllegalStateException("foundNutrition does not exists");
        }
        nutritionList.remove(foundNutrition.get());
    }
}

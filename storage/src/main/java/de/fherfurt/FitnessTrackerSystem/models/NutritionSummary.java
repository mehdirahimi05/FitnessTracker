package de.fherfurt.FitnessTrackerSystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NutritionSummary {
    private int totalCalories;
    private int totalProteinInGram;
    private int totalCarbohydratesInGram;
    private int totalFatInGram;

}

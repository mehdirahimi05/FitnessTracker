package de.fherfurt.FitnessTrackerSystem.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Nutrition {
    private int nutritionId;
    private int userId;
    private int calories;
    private int proteinInGram;
    private int carbohydratesInGram;
    private int fatInGram;
    private MealTyp mealTyp;
}

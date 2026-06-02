package de.fherfurt.FitnessTrackerSystem.models;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DailyDashboard {
    private TrainingsSessionSummary trainingsSessionSummary;
    private NutritionSummary nutritionSummary;
}

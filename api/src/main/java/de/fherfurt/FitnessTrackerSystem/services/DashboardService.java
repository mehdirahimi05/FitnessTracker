package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.models.DailyDashboard;
import de.fherfurt.FitnessTrackerSystem.models.NutritionSummary;
import de.fherfurt.FitnessTrackerSystem.models.TrainingsSessionSummary;
import de.fherfurt.FitnessTrackerSystem.models.User;

import java.time.LocalDate;

public class DashboardService implements IDashboardService {

    private final TrainingsSessionService trainingsSessionService;
    private final NutritionService nutritionService;

    public DashboardService(TrainingsSessionService trainingsSessionService,
                            NutritionService nutritionService) {
        this.trainingsSessionService = trainingsSessionService;
        this.nutritionService = nutritionService;
    }

    @Override
    public DailyDashboard getDailyDashboard(User user, LocalDate date) {
        if (user == null || date == null) {
            throw new IllegalArgumentException("Can not be null");
        }
        TrainingsSessionSummary trainingsSessionSummary = trainingsSessionService.getDailyTrainingsSessionSummary(user, date);
        NutritionSummary nutritionSummary = nutritionService.getDailyNutritionSummary(user.getUserId(), date);

        return new DailyDashboard(trainingsSessionSummary, nutritionSummary);
    }
}

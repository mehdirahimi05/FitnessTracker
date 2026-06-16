package de.fherfurt.de.FitnessTracker.services;

import de.fherfurt.FitnessTrackerSystem.models.*;
import de.fherfurt.FitnessTrackerSystem.services.DashboardService;
import de.fherfurt.FitnessTrackerSystem.services.NutritionService;
import de.fherfurt.FitnessTrackerSystem.services.TrainingsSessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceTest {
    @InjectMocks
    DashboardService dashboardService;

    @Mock
    TrainingsSessionService trainingsSessionService;

    @Mock
    NutritionService nutritionService;

    private final LocalDate date = LocalDate.of(2026, 03, 20);

    private final User mehdi = new User(1, "mehdi", "pass", UserRole.USER, null);

    private final TrainingsSession trainingsSession1 = new TrainingsSession(
            1,
            mehdi,
            LocalDate.of(2026, 3, 20),
            60,
            500,
            new ActivityType(1, "Krafttraining"),
            Difficulty.MEDIUM,
            new WorkoutPlan(1, "Brust Training", new ArrayList<>())
    );

    private final Nutrition nutrition1 = new Nutrition(
            1,
            mehdi,
            600,
            40,
            80,
            20,
            MealType.BREAKFAST,
            LocalDate.of(2026, 3, 20)
    );

    private final TrainingsSessionSummary trainingsSessionSummary = new TrainingsSessionSummary(new ArrayList<>(), 90, 450);
    private final NutritionSummary nutritionSummary = new NutritionSummary(2500, 160, 600, 50);

    /**
     * verifies that a IllegalArgumentException was thrown when 0 parameters are provided
     */
    @Test
    void testGetDailyDashboardNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            dashboardService.getDailyDashboard(null, null);
        });
    }

    /**
     * verifies that the dashboard summary was successful
     */
    @Test
    void testGetDailyDashboardSuccess() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 03, 20);
        when(trainingsSessionService.getDailyTrainingsSessionSummary(mehdi, date)).thenReturn(trainingsSessionSummary);
        when(nutritionService.getDailyNutritionSummary(mehdi, date)).thenReturn(nutritionSummary);

        // Act
        DailyDashboard result = dashboardService.getDailyDashboard(mehdi, date);

        // Assert
        assertNotNull(result.getTrainingsSessionSummary());
        assertNotNull(result.getNutritionSummary());


    }
}

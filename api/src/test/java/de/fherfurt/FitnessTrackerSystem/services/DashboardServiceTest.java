package de.fherfurt.FitnessTrackerSystem.services;

import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.DailyDashboard;
import de.fherfurt.FitnessTrackerSystem.models.Nutrition;
import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.repositories.NutritionRepository;
import de.fherfurt.FitnessTrackerSystem.repositories.TrainingsSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unittest for {@link DashboardService} class
 *
 * @author Mehdi Rahimi
 */
public class DashboardServiceTest {
    private TrainingsSessionRepository trainingsSessionRepository;
    private NutritionRepository nutritionRepository;
    private TrainingsSessionService trainingsSessionService;
    private NutritionService nutritionService;
    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        trainingsSessionRepository = new TrainingsSessionRepository();
        nutritionRepository = new NutritionRepository();
        trainingsSessionService = new TrainingsSessionService(trainingsSessionRepository);
        nutritionService = new NutritionService(nutritionRepository);
        dashboardService = new DashboardService(trainingsSessionService, nutritionService);
    }

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
        User user = Constants.getFirstUser();
        LocalDate date = Constants.FIRST_DATE;
        TrainingsSession trainingsSession = Constants.getFirstTrainingsSession(user);
        Nutrition nutrition = Constants.getFirstNutrition();

        // Act
        trainingsSessionRepository.createTrainingsSession(trainingsSession);
        nutritionRepository.createNutrition(nutrition);

        DailyDashboard result = dashboardService.getDailyDashboard(user, date);

        // Assert
        assertNotNull(result.getTrainingsSessionSummary());
        assertNotNull(result.getNutritionSummary());


    }
}

package de.fherfurt.de.FitnessTracker.services;

import de.fherfurt.FitnessTrackerSystem.models.*;
import de.fherfurt.FitnessTrackerSystem.repositories.INutritionRepository;
import de.fherfurt.FitnessTrackerSystem.services.NutritionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NutritionServiceTest {
    @Mock
    INutritionRepository nutritionRepository;

    @InjectMocks
    NutritionService nutritionService;

    private User mehdi = new User(1, "mehdi", "pass", UserRole.USER, null);

    private Nutrition nutrition1 = new Nutrition(
            1,
            mehdi,
            600,
            40,
            80,
            20,
            MealType.BREAKFAST,
            LocalDate.of(2026, 3, 20)
    );

    private Nutrition nutrition2 = new Nutrition(
            2,
            mehdi,
            800,
            50,
            100,
            25,
            MealType.LUNCH,
            LocalDate.of(2026, 3, 20)
    );

    /**
     * verifies that no Nutrition was found
     */
    @Test
    void testGetAllNutritionNull() {
        // Arrange
        when(nutritionRepository.findAll()).thenReturn(List.of());

        // Act
        List<Nutrition> result = nutritionService.getAllNutrition();

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * verifies that one Nutrition was found
     */
    @Test
    void testGetAllNutritionOne() {
        // Arrange
        when(nutritionRepository.findAll()).thenReturn(List.of(nutrition1));

        // Act
        List<Nutrition> result = nutritionService.getAllNutrition();

        // Assert
        assertEquals(1, result.size());
    }

    /**
     * verifies that more than one Nutrition was found
     */
    @Test
    void testGetAllNutrition() {
        // Arrange
        when(nutritionRepository.findAll()).thenReturn(List.of(nutrition1, nutrition2));

        // Act
        List<Nutrition> result = nutritionService.getAllNutrition();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testGetNutritionByIdSuccess() {
        // Arrange
        when(nutritionRepository.findById(1)).thenReturn(Optional.of(nutrition1));

        // Act
        Optional<Nutrition> result = nutritionService.getNutritionById(nutrition1.getNutritionId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(nutrition1, result.get());
    }

    @Test
    void testCheckIsOwnNutritionNotFound() {
        // Arrange
        when(nutritionRepository.findById(5)).thenReturn(Optional.empty());

        // Act
        boolean result = nutritionService.checkIsOwnNutrition(5);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCheckIsOwnNutritionSuccess() {
        // Arrange
        when(nutritionRepository.findById(1)).thenReturn(Optional.of(nutrition1));

        // Act
        boolean result = nutritionService.checkIsOwnNutrition(nutrition1.getNutritionId());

        // Assert
        assertTrue(result);
    }

    @Test
    void testAddNutritionSuccess() {
        // Act
        nutritionService.addNutrition(nutrition1);

        // Assert -> verify if save() was called
        verify(nutritionRepository).save(nutrition1);
    }

    @Test
    void testUpdateNutritionSuccess() {
        // Act
        nutritionService.updateNutrition(nutrition1);

        // Assert -> verify if save() was called
        verify(nutritionRepository).save(nutrition1);
    }

    @Test
    void testDeleteNutritionById() {
        // Act
        nutritionService.deleteNutritionById(nutrition1.getNutritionId());

        // Assert -> verify if deleteById() was called
        verify(nutritionRepository).deleteById(nutrition1.getNutritionId());
    }

    /**
     * verifies that a IllegalArgumentException was thrown when 0 parameters are provided
     */
    @Test
    void testGetDailyNutritionSummaryNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            nutritionService.getDailyNutritionSummary(null, null);
        });
    }

    /**
     * verifies that a IllegalStateException was thrown when filterSession is Empty
     */
    @Test
    void testGetDailyNutritionSummaryIsEmpty() {
        // Act
        assertThrows(IllegalStateException.class, () -> {
            nutritionService.getDailyNutritionSummary(mehdi, nutrition1.getDate());
        });
    }

    /**
     * verifies that DailyNutritionSummary was summed successfully
     */
    @Test
    void testGetDailyNutritionSummarySuccess() {
        // Arrange
        when(nutritionRepository.findAll()).thenReturn(List.of(nutrition1, nutrition2));

        // Act
        NutritionSummary summaryList = nutritionService.getDailyNutritionSummary(mehdi, nutrition1.getDate());

        // Assert
        assertEquals(nutrition1.getCalories() + nutrition2.getCalories(), summaryList.getTotalCalories());
        assertEquals(nutrition1.getProteinInGram() + nutrition2.getProteinInGram(), summaryList.getTotalProteinInGram());
        assertEquals(nutrition1.getCarbohydratesInGram() + nutrition2.getCarbohydratesInGram(), summaryList.getTotalCarbohydratesInGram());
        assertEquals(nutrition1.getFatInGram() + nutrition2.getFatInGram(), summaryList.getTotalFatInGram());
    }
}
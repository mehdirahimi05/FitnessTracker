package de.fherfurt.FitnessTrackerSystem.services;


import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.Nutrition;
import de.fherfurt.FitnessTrackerSystem.repositories.NutritionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link NutritionService} class
 *
 * @author Mehdi Rahimi
 */
public class NutritionServiceTest {
    private NutritionRepository nutritionRepository;
    private NutritionService nutritionService;

    @BeforeEach
    void setUp() {
        nutritionRepository = new NutritionRepository();
        nutritionService = new NutritionService(nutritionRepository);
    }

    /**
     * gets all Nutrition
     */
    @Test
    @DisplayName("getAllNutrition: Success")
    void testGetAllNutritionSuccess() {
        // Arrange
        Nutrition nutrition1 = Constants.getFirstNutrition();
        Nutrition nutrition2 = Constants.getSecondNutrition();

        nutritionRepository.createNutrition(nutrition1);
        nutritionRepository.createNutrition(nutrition2);

        // Act
        List<Nutrition> nutritionList = nutritionService.getAllNutrition();

        // Assert
        assertEquals(2, nutritionList.size());
        assertTrue(nutritionList.contains(nutrition1));
        assertTrue(nutritionList.contains(nutrition2));
    }

    /**
     * get Nutrition  by id
     */
    @Test
    @DisplayName("get Nutrition by Id: success")
    void testGetNutritionByIdSuccess() {
        // Arrange
        Nutrition nutrition = Constants.getFirstNutrition();
        int nutritionId = Constants.FIRST_NUTRITION_ID;
        nutritionRepository.createNutrition(nutrition);

        // Act
        Optional<Nutrition> nutritionList = nutritionService.getNutritionById(nutritionId);

        // Assert
        assertTrue(nutritionList.isPresent());
        assertEquals(nutrition, nutritionList.get());
    }

    /**
     * verifies that false is returned when the NutritionId does not exist
     */
    @Test
    @DisplayName("checkIsOwnNutrition: NutritionId does not exists")
    void testCheckIsOwnNutritionNotFound() {
        // Arrange
        Nutrition nutrition = Constants.getFirstNutrition();
        nutritionRepository.createNutrition(nutrition);

        // Act
        boolean nutritionList = nutritionService.checkIsOwnNutrition(5);

        // Assert
        assertFalse(nutritionList);
    }

    /**
     * verifies that the NutritionId exists
     */
    @Test
    @DisplayName("checkIsOwnNutrition : NutritionId does  exists")
    void testCheckIsOwnNutritionSuccess() {
        // Arrange
        Nutrition nutrition = Constants.getFirstNutrition();
        nutritionRepository.createNutrition(nutrition);

        // Act
        boolean nutritionList = nutritionService.checkIsOwnNutrition(nutrition.getNutritionId());

        // Assert
        assertTrue(nutritionList);
    }

    /**
     * added Nutrition
     */
    @Test
    @DisplayName("Nutrition: success")
    void testAddNutritionSuccess() {
        // Arrange
        Nutrition nutrition = Constants.getFirstNutrition();
        int expectedSizeOfNutrition = 1;

        // Act
        nutritionService.addNutrition(nutrition);
        int actualSizeOfNutrition = nutritionRepository.getNutritionList().size();

        // Assert
        assertEquals(expectedSizeOfNutrition, actualSizeOfNutrition);
    }

    /**
     * updates Nutrition
     */
    @Test
    @DisplayName("update Nutrition : Success")
    void testUpdateNutritionSuccess() {
        // Arrange
        Nutrition nutrition = Constants.getFirstNutrition();
        int nutritionId = Constants.FIRST_NUTRITION_ID;

        nutritionRepository.createNutrition(nutrition);

        Nutrition updatedNutrition = new Nutrition(
                nutritionId,
                Constants.SECOND_USER_ID,
                Constants.SECOND_NUTRITION_CALORIES,
                Constants.SECOND_NUTRITION_PROTEIN,
                Constants.SECOND_NUTRITION_CARBOHYDRATES,
                Constants.SECOND_NUTRITION_FAT,
                Constants.SECOND_MEAL_TYP,
                Constants.SECOND_DATE
        );

        // Act
        nutritionService.updateNutrition(updatedNutrition);
        Optional<Nutrition> findNutritionList = nutritionRepository.getNutritionById(nutritionId);

        // Assert
        assertEquals(Constants.SECOND_USER_ID, findNutritionList.get().getUserId());
        assertEquals(Constants.SECOND_NUTRITION_CALORIES, findNutritionList.get().getCalories());
        assertEquals(Constants.SECOND_NUTRITION_PROTEIN, findNutritionList.get().getProteinInGram());
        assertEquals(Constants.SECOND_NUTRITION_CARBOHYDRATES, findNutritionList.get().getCarbohydratesInGram());
        assertEquals(Constants.SECOND_NUTRITION_FAT, findNutritionList.get().getFatInGram());
        assertEquals(Constants.SECOND_MEAL_TYP, findNutritionList.get().getMealTyp());
    }

    /**
     * delete Nutrition by id
     */
    @Test
    @DisplayName("delete Nutrition by id: success")
    void testDeleteNutritionByIdSuccess() {
        // Arrange
        Nutrition nutrition = Constants.getFirstNutrition();
        nutritionRepository.createNutrition(nutrition);
        int nutritionId = Constants.FIRST_NUTRITION_ID;
        int expectedSizeOfNutritionList = 0;

        // Act
        nutritionService.deleteNutritionById(nutritionId);
        int actualSizeOfNutritionList = nutritionRepository.getNutritionList().size();

        // Assert
        assertEquals(expectedSizeOfNutritionList, actualSizeOfNutritionList);

    }
}

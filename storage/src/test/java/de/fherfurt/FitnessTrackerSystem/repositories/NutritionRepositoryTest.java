package de.fherfurt.FitnessTrackerSystem.repositories;


import de.fherfurt.FitnessTrackerSystem.Constants;
import de.fherfurt.FitnessTrackerSystem.models.Nutrition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unittest for {@link NutritionRepository} class
 *
 * @author Mehdi Rahimi
 */
public class NutritionRepositoryTest {
    private NutritionRepository nutritionRepository;

    @BeforeEach
    void setUp() {
        nutritionRepository = new NutritionRepository();
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("createNutrition: Ignore null input and maintain empty list")
    void testCreateNutritionNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            nutritionRepository.createNutrition(null);
        });
    }

    /**
     * verifies that a nutrition was added to the list
     */
    @Test
    @DisplayName("createNutrition: success")
    void testCreateNutritionSuccess() {
        // Arrange
        Nutrition nutrition = Constants.getFirstNutrition();
        int expectedSizeOfNutritionList = 1;

        // Act
        nutritionRepository.createNutrition(nutrition);
        int actualSizeOfNutritionList = nutritionRepository.getNutritionList().size();

        // Assert
        assertEquals(expectedSizeOfNutritionList, actualSizeOfNutritionList);
    }

    /**
     * verifies that there is no Nutrition in the list
     */
    @Test
    @DisplayName("Get null Nutrition")
    void testGetNullNutrition() {
        // Act
        List<Nutrition> nutritionList = nutritionRepository.getAllNutrition();

        // Assert
        assertEquals(0, nutritionList.size());
    }

    /**
     * verifies that there is 1 Nutrition in the list
     */
    @Test
    @DisplayName("Get one Nutrition")
    void testGetOneNutrition() {
        // Arrange
        Nutrition nutrition = Constants.getFirstNutrition();

        // Act
        nutritionRepository.createNutrition(nutrition);

        List<Nutrition> nutritionList = nutritionRepository.getAllNutrition();

        // Assert
        assertEquals(1, nutritionList.size());
        assertTrue(nutritionList.contains(nutrition));

    }

    /**
     * verifies that there is multiple Nutrition
     */
    @Test
    @DisplayName("Get all Nutrition")
    void testGetAllNutrition() {
        // Arrange
        Nutrition nutrition1 = Constants.getFirstNutrition();
        Nutrition nutrition2 = Constants.getSecondNutrition();

        // Act
        nutritionRepository.createNutrition(nutrition1);
        nutritionRepository.createNutrition(nutrition2);

        List<Nutrition> nutritionList = nutritionRepository.getAllNutrition();

        // Assert
        assertEquals(2, nutritionList.size());
        assertTrue(nutritionList.contains(nutrition1));
        assertTrue(nutritionList.contains(nutrition2));
    }

    /**
     * verifies that there is no Nutrition id
     */
    @Test
    @DisplayName("Get Nutrition by Id: empty")
    void testGetNutritionByIdIsEmpty() {
        // Act
        Optional<Nutrition> nutritionList = nutritionRepository.getNutritionById(3);

        // Assert
        assertTrue(nutritionList.isEmpty());
    }

    /**
     * verifies that there is a Nutrition id
     */
    @Test
    @DisplayName("Get Nutrition by Id : present")
    void testGetNutritionByIdIsPresent() {
        // Arrange
        Nutrition nutrition = Constants.getFirstNutrition();

        // Act
        nutritionRepository.createNutrition(nutrition);
        Optional<Nutrition> nutritionList = nutritionRepository.getNutritionById(1);

        // Assert
        assertTrue(nutritionList.isPresent());
    }

    /**
     * gets the Nutrition id
     */
    @Test
    @DisplayName("Get Nutrition by Id")
    void testGetNutritionById() {
        // Arrange
        Nutrition nutrition = Constants.getFirstNutrition();

        // Act
        nutritionRepository.createNutrition(nutrition);
        Optional<Nutrition> nutritionList = nutritionRepository.getNutritionById(1);

        // Assert
        assertEquals(nutrition, nutritionList.get());
    }

    /**
     * Verifies that the IllegalArgumentException was thrown when null is provided
     */
    @Test
    @DisplayName("Update Nutrition: Ignore null input and maintain empty list")
    void testUpdateNutritionNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            nutritionRepository.updateNutrition(null);
        });
    }

    /**
     * verifies that the IllegalStateException was thrown when Nutrition list is empty
     */
    @Test
    @DisplayName("Update Nutrition: ignore empty list and maintain the list")
    void testUpdateNutritionEmpty() {
        // Arrange
        Nutrition nutrition = Constants.getFirstNutrition();

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            nutritionRepository.updateNutrition(nutrition);
        });
    }

    /**
     * update Nutrition
     */
    @Test
    @DisplayName("update Nutrition: success")
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
        nutritionRepository.updateNutrition(updatedNutrition);
        Optional<Nutrition> finalNutritionList = nutritionRepository.getNutritionById(nutritionId);

        // Assert
        assertEquals(Constants.SECOND_USER_ID, finalNutritionList.get().getUserId());
        assertEquals(Constants.SECOND_NUTRITION_CALORIES, finalNutritionList.get().getCalories());
        assertEquals(Constants.SECOND_NUTRITION_PROTEIN, finalNutritionList.get().getProteinInGram());
        assertEquals(Constants.SECOND_NUTRITION_CARBOHYDRATES, finalNutritionList.get().getCarbohydratesInGram());
        assertEquals(Constants.SECOND_NUTRITION_FAT, finalNutritionList.get().getFatInGram());
        assertEquals(Constants.SECOND_MEAL_TYP, finalNutritionList.get().getMealTyp());
    }

    /**
     * verifies that the IllegalStateException was thrown when nutrition list is empty
     */
    @Test
    @DisplayName("delete Nutrition by id: ignore empty list and maintain the list")
    void testDeleteNutritionByIdEmpty() {
        // Arrange
        int nutritionId = Constants.FIRST_NUTRITION_ID;

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            nutritionRepository.deleteNutritionById(nutritionId);
        });
    }

    /**
     * delete nutritionId by id
     */
    @Test
    @DisplayName("delete nutritionId by id: success")
    void testDeleteNutritionIdByIdSuccess() {
        // Arrange
        Nutrition nutrition = Constants.getFirstNutrition();
        nutritionRepository.createNutrition(nutrition);
        int nutritionId = Constants.FIRST_NUTRITION_ID;
        int expectedSizeOfNutritionList = 0;

        // Act
        nutritionRepository.deleteNutritionById(nutritionId);
        int actualSizeOfNutritionList = nutritionRepository.getNutritionList().size();

        // Assert
        assertEquals(expectedSizeOfNutritionList, actualSizeOfNutritionList);

    }
}



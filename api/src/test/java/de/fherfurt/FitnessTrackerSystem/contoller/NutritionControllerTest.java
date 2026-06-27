package de.fherfurt.FitnessTrackerSystem.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fherfurt.FitnessTrackerSystem.FitnessTrackerApplication;
import de.fherfurt.FitnessTrackerSystem.models.MealType;
import de.fherfurt.FitnessTrackerSystem.models.Nutrition;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.repositories.INutritionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FitnessTrackerApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class NutritionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private INutritionRepository nutritionRepository;

    private TestHelper testHelper;

    @BeforeEach
    void setUp() {
        testHelper = new TestHelper(mockMvc, objectMapper);
    }

    @Test
    void testAddNutritionSuccess() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        Nutrition nutrition = new Nutrition(
                0,
                user,
                500,
                30,
                60,
                15,
                MealType.BREAKFAST,
                LocalDate.of(2026, 1, 1)
        );
        String json = objectMapper.writeValueAsString(nutrition);

        // Add Nutrition
        mockMvc.perform(post("/api/nutrition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetNutritionWithoutToken() throws Exception {
        mockMvc.perform(get("/api/nutrition"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetNutritionWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        Nutrition nutrition = new Nutrition(
                0,
                user,
                500,
                30,
                60,
                15,
                MealType.BREAKFAST,
                LocalDate.of(2026, 1, 1)
        );
        String json = objectMapper.writeValueAsString(nutrition);

        // Add Nutrition
        mockMvc.perform(post("/api/nutrition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token
        mockMvc.perform(get("/api/nutrition")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllNutritionWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        Nutrition nutrition1 = new Nutrition(
                0,
                user,
                500,
                30,
                60,
                15,
                MealType.BREAKFAST,
                LocalDate.of(2026, 1, 1)
        );
        String json1 = objectMapper.writeValueAsString(nutrition1);

        Nutrition nutrition2 = new Nutrition(
                0,
                user,
                700,
                40,
                80,
                20,
                MealType.LUNCH,
                LocalDate.of(2026, 1, 1)
        );
        String json2 = objectMapper.writeValueAsString(nutrition2);

        // Add Nutrition 1
        mockMvc.perform(post("/api/nutrition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Add Nutrition 2
        mockMvc.perform(post("/api/nutrition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token
        mockMvc.perform(get("/api/nutrition")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetNutritionByIdWithoutToken() throws Exception {
        mockMvc.perform(get("/api/nutrition/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetNutritionByIdWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        Nutrition nutrition = new Nutrition(
                0,
                user,
                500,
                30,
                60,
                15,
                MealType.BREAKFAST,
                LocalDate.of(2026, 1, 1)
        );
        String json = objectMapper.writeValueAsString(nutrition);

        // Add Nutrition
        String addResponse = mockMvc.perform(post("/api/nutrition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        Nutrition addedNutrition = objectMapper.readValue(addResponse, Nutrition.class);

        // change it to an int
        int id = addedNutrition.getNutritionId();

        // Request with token
        mockMvc.perform(get("/api/nutrition/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateNutritionWithoutToken() throws Exception {
        mockMvc.perform(put("/api/nutrition"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdateNutritionWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        Nutrition nutrition = new Nutrition(
                0,
                user,
                500,
                30,
                60,
                15,
                MealType.BREAKFAST,
                LocalDate.of(2026, 1, 1)
        );
        String json = objectMapper.writeValueAsString(nutrition);

        // Add Nutrition
        String addResponse = mockMvc.perform(post("/api/nutrition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        Nutrition addedNutrition = objectMapper.readValue(addResponse, Nutrition.class);

        // update the calories
        addedNutrition.setCalories(600);

        // write the new addedNutrition
        String updateJson = objectMapper.writeValueAsString(addedNutrition);

        // Request with Token
        mockMvc.perform(put("/api/nutrition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteNutritionByIdWithoutToken() throws Exception {
        mockMvc.perform(delete("/api/nutrition"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteNutritionByIdWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        Nutrition nutrition = new Nutrition(
                0,
                user,
                500,
                30,
                60,
                15,
                MealType.BREAKFAST,
                LocalDate.of(2026, 1, 1)
        );
        String json = objectMapper.writeValueAsString(nutrition);

        // Add Nutrition
        String addResponse = mockMvc.perform(post("/api/nutrition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        Nutrition addedNutrition = objectMapper.readValue(addResponse, Nutrition.class);

        // change it to an int
        int id = addedNutrition.getNutritionId();

        // Request with Token
        mockMvc.perform(delete("/api/nutrition/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetDailyNutritionSummary() throws Exception{
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        Nutrition nutrition = new Nutrition(
                0,
                user,
                500,
                30,
                60,
                15,
                MealType.BREAKFAST,
                LocalDate.of(2026, 1, 1)
        );
        String json = objectMapper.writeValueAsString(nutrition);

        // Add Nutrition
        mockMvc.perform(post("/api/nutrition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with Token -> getDailyNutritionSummary
        mockMvc.perform(get("/api/nutrition/nutrition_summary")
                        .param("userId", String.valueOf(user.getUserId()))
                        .param("date", "2026-01-01")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
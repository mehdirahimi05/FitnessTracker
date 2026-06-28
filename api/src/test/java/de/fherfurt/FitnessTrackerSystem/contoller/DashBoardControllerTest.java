package de.fherfurt.FitnessTrackerSystem.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fherfurt.FitnessTrackerSystem.FitnessTrackerApplication;
import de.fherfurt.FitnessTrackerSystem.models.*;
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
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FitnessTrackerApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class DashBoardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private TestHelper testHelper;

    @BeforeEach
    void setUp() {
        testHelper = new TestHelper(mockMvc, objectMapper);
    }

    // for trainingsSession
    private ActivityType addActivityType(String token) throws Exception {
        // Arrange
        ActivityType activityType = new ActivityType(0, "Kraftsport");
        String json = objectMapper.writeValueAsString(activityType);


        // Add ActivityType and read the value
        String addResponse = mockMvc.perform(post("/api/activity_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(addResponse, ActivityType.class);
    }

    private WorkoutPlan addWorkoutPlan(String token) throws Exception{
        // Arrange
        WorkoutPlan workoutPlan = new WorkoutPlan(0, "Leg Day", new ArrayList<>());
        String json = objectMapper.writeValueAsString(workoutPlan);

        // Add WorkoutPlan and read the value
        String addResponse = mockMvc.perform(post("/api/workout_plan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(addResponse, WorkoutPlan.class);
    }

    @Test
    void testGetDailyDashboardSuccess() throws Exception{
        // Arrange
        String token = testHelper.getToken();

        User user = testHelper.getRegisteredUser();
        ActivityType activityType = addActivityType(token);
        WorkoutPlan workoutPlan = addWorkoutPlan(token);

        TrainingsSession trainingsSession = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 01, 01),
                60,
                400,
                activityType,
                Difficulty.MEDIUM,
                workoutPlan
        );
        String tsJson = objectMapper.writeValueAsString(trainingsSession);

        // Add TrainingsSession
        mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tsJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());


        Nutrition nutrition = new Nutrition(
                0,
                user,
                500,
                30,
                60,
                15,
                MealType.BREAKFAST,
                LocalDate.of(2026, 01, 01)
        );
        String nJson = objectMapper.writeValueAsString(nutrition);

    // Add Nutrition
        mockMvc.perform(post("/api/nutrition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nJson)
                        .header("Authorization", "Bearer " + token))
            .andExpect(status().isCreated());

        // Request with token -> getDailyDashboard
        mockMvc.perform(get("/api/dashboard/daily")
                .param("userId", String.valueOf(user.getUserId()))
                .param("date", "2026-01-01")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}

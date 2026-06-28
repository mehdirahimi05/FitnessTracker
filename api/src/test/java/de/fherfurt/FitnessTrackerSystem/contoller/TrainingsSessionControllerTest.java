package de.fherfurt.FitnessTrackerSystem.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fherfurt.FitnessTrackerSystem.FitnessTrackerApplication;
import de.fherfurt.FitnessTrackerSystem.models.*;
import de.fherfurt.FitnessTrackerSystem.repositories.ITrainingsSessionRepository;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FitnessTrackerApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class TrainingsSessionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ITrainingsSessionRepository trainingsSessionRepository;

    private TestHelper testHelper;

    @BeforeEach
    void setUp() {
        testHelper = new TestHelper(mockMvc, objectMapper);
    }


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
    void testAddTrainingsSessionSuccess() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();
        ActivityType activityType = addActivityType(token);
        WorkoutPlan workoutPlan = addWorkoutPlan(token);

        TrainingsSession trainingsSession = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 1),
                60,
                400,
                activityType,
                Difficulty.MEDIUM,
                workoutPlan
        );
        String json = objectMapper.writeValueAsString(trainingsSession);

        // Add TrainingsSession
        mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetTrainingsSessionWithoutToken() throws Exception {
        mockMvc.perform(get("/api/trainings_session"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetTrainingsSessionWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();
        ActivityType activityType = addActivityType(token);
        WorkoutPlan workoutPlan = addWorkoutPlan(token);

        TrainingsSession trainingsSession = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 1),
                60,
                400,
                activityType,
                Difficulty.MEDIUM,
                workoutPlan
        );
        String json = objectMapper.writeValueAsString(trainingsSession);

        // Add TrainingsSession
        mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token
        mockMvc.perform(get("/api/trainings_session")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllTrainingsSessionsWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();
        ActivityType activityType = addActivityType(token);
        WorkoutPlan workoutPlan = addWorkoutPlan(token);

        TrainingsSession trainingsSession1 = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 1),
                60,
                400,
                activityType,
                Difficulty.MEDIUM,
                workoutPlan
        );
        String json1 = objectMapper.writeValueAsString(trainingsSession1);

        TrainingsSession trainingsSession2 = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 2),
                45,
                300,
                activityType,
                Difficulty.EASY,
                workoutPlan
        );
        String json2 = objectMapper.writeValueAsString(trainingsSession2);

        // Add TrainingsSession 1
        mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Add TrainingsSession 2
        mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token
        mockMvc.perform(get("/api/trainings_session")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetTrainingsSessionByIdWithoutToken() throws Exception {
        mockMvc.perform(get("/api/trainings_session/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetTrainingsSessionByIdWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();
        ActivityType activityType = addActivityType(token);
        WorkoutPlan workoutPlan = addWorkoutPlan(token);

        TrainingsSession trainingsSession = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 1),
                60,
                400,
                activityType,
                Difficulty.MEDIUM,
                workoutPlan
        );
        String json = objectMapper.writeValueAsString(trainingsSession);

        // Add TrainingsSession
        String addResponse = mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        TrainingsSession addedTrainingsSession = objectMapper.readValue(addResponse, TrainingsSession.class);

        // change it to an int
        int id = addedTrainingsSession.getTrainingsSessionId();

        // Request with token
        mockMvc.perform(get("/api/trainings_session/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateTrainingsSessionWithoutToken() throws Exception {
        mockMvc.perform(put("/api/trainings_session"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdateTrainingsSessionWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();
        ActivityType activityType = addActivityType(token);
        WorkoutPlan workoutPlan = addWorkoutPlan(token);

        TrainingsSession trainingsSession = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 1),
                60,
                400,
                activityType,
                Difficulty.MEDIUM,
                workoutPlan
        );
        String json = objectMapper.writeValueAsString(trainingsSession);

        // Add TrainingsSession
        String addResponse = mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        TrainingsSession addedTrainingsSession = objectMapper.readValue(addResponse, TrainingsSession.class);

        // update the burned calories
        addedTrainingsSession.setBurnedCalories(500);

        // write the new addedTrainingsSession
        String updateJson = objectMapper.writeValueAsString(addedTrainingsSession);

        // Request with Token
        mockMvc.perform(put("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteTrainingsSessionByIdWithoutToken() throws Exception {
        mockMvc.perform(delete("/api/trainings_session"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteTrainingsSessionByIdWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();
        ActivityType activityType = addActivityType(token);
        WorkoutPlan workoutPlan = addWorkoutPlan(token);

        TrainingsSession trainingsSession = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 1),
                60,
                400,
                activityType,
                Difficulty.MEDIUM,
                workoutPlan
        );
        String json = objectMapper.writeValueAsString(trainingsSession);

        // Add TrainingsSession
        String addResponse = mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        TrainingsSession addedTrainingsSession = objectMapper.readValue(addResponse, TrainingsSession.class);

        // change it to an int
        int id = addedTrainingsSession.getTrainingsSessionId();

        // Request with Token
        mockMvc.perform(delete("/api/trainings_session/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetMostActiveUserByAmountOfTrainingsSessionsSuccess() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();
        ActivityType activityType = addActivityType(token);
        WorkoutPlan workoutPlan = addWorkoutPlan(token);

        TrainingsSession trainingsSession1 = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 1),
                60,
                400,
                activityType,
                Difficulty.MEDIUM,
                workoutPlan
        );
        String json1 = objectMapper.writeValueAsString(trainingsSession1);

        TrainingsSession trainingsSession2 = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 2),
                45,
                300,
                activityType,
                Difficulty.EASY,
                workoutPlan
        );
        String json2 = objectMapper.writeValueAsString(trainingsSession2);

        // Add TrainingsSession 1
        mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Add TrainingsSession 2
        mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token -> mostActiveUser
        mockMvc.perform(get("/api/trainings_session/most_active")
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-01-02")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

    @Test
    void testFilterTrainingsSessionSuccess() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();
        ActivityType activityType = addActivityType(token);
        WorkoutPlan workoutPlan = addWorkoutPlan(token);

        TrainingsSession trainingsSession1 = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 1),
                60,
                400,
                activityType,
                Difficulty.MEDIUM,
                workoutPlan
        );
        String json1 = objectMapper.writeValueAsString(trainingsSession1);

        TrainingsSession trainingsSession2 = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 2),
                45,
                300,
                activityType,
                Difficulty.EASY,
                workoutPlan
        );
        String json2 = objectMapper.writeValueAsString(trainingsSession2);

        // Add TrainingsSession 1
        mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Add TrainingsSession 2
        mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token -> filterTrainingsSession
        mockMvc.perform(get("/api/trainings_session/filter")
                .param("minCalories", "200")
                .param("maxCalories", "350")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetTrainingsSessionStreakSuccess() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();
        ActivityType activityType = addActivityType(token);
        WorkoutPlan workoutPlan = addWorkoutPlan(token);

        TrainingsSession trainingsSession1 = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 1),
                60,
                400,
                activityType,
                Difficulty.MEDIUM,
                workoutPlan
        );
        String json1 = objectMapper.writeValueAsString(trainingsSession1);

        TrainingsSession trainingsSession2 = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 2),
                45,
                300,
                activityType,
                Difficulty.EASY,
                workoutPlan
        );
        String json2 = objectMapper.writeValueAsString(trainingsSession2);

        // Add TrainingsSession 1
        mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Add TrainingsSession 2
        mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token -> getTrainingsSessionStreak
        mockMvc.perform(get("/api/trainings_session/streak")
                .param("userId", String.valueOf(user.getUserId()))
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    void testGetDailyTrainingsSessionSummarySuccess() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();
        ActivityType activityType = addActivityType(token);
        WorkoutPlan workoutPlan = addWorkoutPlan(token);

        TrainingsSession trainingsSession1 = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 1),
                60,
                400,
                activityType,
                Difficulty.MEDIUM,
                workoutPlan
        );
        String json1 = objectMapper.writeValueAsString(trainingsSession1);

        TrainingsSession trainingsSession2 = new TrainingsSession(
                0,
                user,
                LocalDate.of(2026, 1, 1),
                45,
                300,
                activityType,
                Difficulty.EASY,
                workoutPlan
        );
        String json2 = objectMapper.writeValueAsString(trainingsSession2);

        // Add TrainingsSession 1
        mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Add TrainingsSession 2
        mockMvc.perform(post("/api/trainings_session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token -> getDailyTrainingsSessionSummary
        mockMvc.perform(get("/api/trainings_session/trainings_session_summary")
                .param("userId", String.valueOf(user.getUserId()))
                .param("date", "2026-01-01")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
package de.fherfurt.FitnessTrackerSystem.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fherfurt.FitnessTrackerSystem.FitnessTrackerApplication;
import de.fherfurt.FitnessTrackerSystem.contoller.request.AddExerciseRequest;
import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import de.fherfurt.FitnessTrackerSystem.models.WorkoutPlan;
import de.fherfurt.FitnessTrackerSystem.repositories.IWorkoutPlanRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FitnessTrackerApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class WorkoutPlanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private IWorkoutPlanRepository workoutPlanRepository;

    private TestHelper testHelper;

    @BeforeEach
    void setUp() {
        testHelper = new TestHelper(mockMvc, objectMapper);
    }

    @Test
    void testAddWorkoutPlanSuccess() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        WorkoutPlan workoutPlan = new WorkoutPlan(0, "Push Day", List.of());
        String json = objectMapper.writeValueAsString(workoutPlan);

        // Add WorkoutPlan
        mockMvc.perform(post("/api/workout_plan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetWorkoutPlanWithoutToken() throws Exception {
        mockMvc.perform(get("/api/workout_plan"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetWorkoutPlanWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        WorkoutPlan workoutPlan = new WorkoutPlan(0, "Push Day", List.of());
        String json = objectMapper.writeValueAsString(workoutPlan);

        // Add WorkoutPlan
        mockMvc.perform(post("/api/workout_plan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token
        mockMvc.perform(get("/api/workout_plan")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllWorkoutPlansWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        WorkoutPlan workoutPlan1 = new WorkoutPlan(0, "Push Day", List.of());
        String json1 = objectMapper.writeValueAsString(workoutPlan1);

        WorkoutPlan workoutPlan2 = new WorkoutPlan(0, "Pull Day", List.of());
        String json2 = objectMapper.writeValueAsString(workoutPlan2);

        // Add WorkoutPlan 1
        mockMvc.perform(post("/api/workout_plan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Add WorkoutPlan 2
        mockMvc.perform(post("/api/workout_plan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token
        mockMvc.perform(get("/api/workout_plan")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetWorkoutPlanByIdWithoutToken() throws Exception {
        mockMvc.perform(get("/api/workout_plan/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetWorkoutPlanByIdWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        WorkoutPlan workoutPlan = new WorkoutPlan(0, "Push Day", List.of());
        String json = objectMapper.writeValueAsString(workoutPlan);

        // Add WorkoutPlan
        String addResponse = mockMvc.perform(post("/api/workout_plan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        WorkoutPlan addedWorkoutPlan = objectMapper.readValue(addResponse, WorkoutPlan.class);

        // change it to an int
        int id = addedWorkoutPlan.getWorkoutPlanId();

        // Request with token
        mockMvc.perform(get("/api/workout_plan/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateWorkoutPlanWithoutToken() throws Exception {
        mockMvc.perform(put("/api/workout_plan"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdateWorkoutPlanWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        WorkoutPlan workoutPlan = new WorkoutPlan(0, "Push Day", List.of());
        String json = objectMapper.writeValueAsString(workoutPlan);

        // Add WorkoutPlan
        String addResponse = mockMvc.perform(post("/api/workout_plan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        WorkoutPlan addedWorkoutPlan = objectMapper.readValue(addResponse, WorkoutPlan.class);

        // update the workout plan name
        addedWorkoutPlan.setName("Leg Day");

        // write the new addedWorkoutPlan
        String updateJson = objectMapper.writeValueAsString(addedWorkoutPlan);

        // Request with Token
        mockMvc.perform(put("/api/workout_plan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteWorkoutPlanByIdWithoutToken() throws Exception {
        mockMvc.perform(delete("/api/workout_plan"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteWorkoutPlanByIdWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        WorkoutPlan workoutPlan = new WorkoutPlan(0, "Push Day", List.of());
        String json = objectMapper.writeValueAsString(workoutPlan);

        // Add WorkoutPlan
        String addResponse = mockMvc.perform(post("/api/workout_plan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        WorkoutPlan addedWorkoutPlan = objectMapper.readValue(addResponse, WorkoutPlan.class);

        // change it to an int
        int id = addedWorkoutPlan.getWorkoutPlanId();

        // Request with Token
        mockMvc.perform(delete("/api/workout_plan/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAddExerciseToWorkoutPlanSuccess() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        // Add workoutPlan to db and read the value
        WorkoutPlan workoutPlan = new WorkoutPlan(0, "Push Day", List.of());
        String wpJson = objectMapper.writeValueAsString(workoutPlan);

        String wpResponse = mockMvc.perform(post("/api/workout_plan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wpJson)
                        .header("Authorization", "Bearer " + token))
                .andReturn().getResponse().getContentAsString();

        WorkoutPlan addedWorkoutPlan = objectMapper.readValue(wpResponse, WorkoutPlan.class);

        // Add exercise to db and read the value
        Exercise exercise = new Exercise(0, "Bench Press", List.of());
        String exJson = objectMapper.writeValueAsString(exercise);

        String exResponse = mockMvc.perform(post("/api/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(exJson)
                        .header("Authorization", "Bearer " + token))
                .andReturn().getResponse().getContentAsString();

        Exercise addedExercise = objectMapper.readValue(exResponse, Exercise.class);

        // build AddExerciseRequest
        AddExerciseRequest request = new AddExerciseRequest();
        request.setWorkoutPlan(addedWorkoutPlan);
        request.setExercise(addedExercise);
        request.setSets(3);
        request.setRepetitions(10);

        String json = objectMapper.writeValueAsString(request);

        // Request with token -> addExerciseToWorkoutPlan
        mockMvc.perform(post("/api/workout_plan/add_exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testRemoveExerciseFromWorkoutPlanSuccess() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        // Add workoutPlan to db and read the value
        WorkoutPlan workoutPlan = new WorkoutPlan(0, "Push Day", List.of());
        String wpJson = objectMapper.writeValueAsString(workoutPlan);

        String wpResponse = mockMvc.perform(post("/api/workout_plan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wpJson)
                        .header("Authorization", "Bearer " + token))
                .andReturn().getResponse().getContentAsString();

        WorkoutPlan addedWorkoutPlan = objectMapper.readValue(wpResponse, WorkoutPlan.class);

        // Add exercise to db and read the value
        Exercise exercise = new Exercise(0, "Bench Press", List.of());
        String exJson = objectMapper.writeValueAsString(exercise);

        String exResponse = mockMvc.perform(post("/api/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(exJson)
                        .header("Authorization", "Bearer " + token))
                .andReturn().getResponse().getContentAsString();

        Exercise addedExercise = objectMapper.readValue(exResponse, Exercise.class);

        // build AddExerciseRequest
        AddExerciseRequest request = new AddExerciseRequest();
        request.setWorkoutPlan(addedWorkoutPlan);
        request.setExercise(addedExercise);
        request.setSets(3);
        request.setRepetitions(10);

        String json = objectMapper.writeValueAsString(request);

        // Request with token -> addExerciseToWorkoutPlan
        mockMvc.perform(post("/api/workout_plan/add_exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        // change the values to  int
        int workoutPlanId = addedWorkoutPlan.getWorkoutPlanId();
        int exerciseId = addedExercise.getExerciseId();

        // Request with token -> removeExerciseFromWorkoutPlan
        mockMvc.perform(delete("/api/workout_plan/" + workoutPlanId + "/exercise/" + exerciseId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
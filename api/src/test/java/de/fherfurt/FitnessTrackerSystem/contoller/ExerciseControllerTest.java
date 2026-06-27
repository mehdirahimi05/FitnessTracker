package de.fherfurt.FitnessTrackerSystem.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fherfurt.FitnessTrackerSystem.FitnessTrackerApplication;
import de.fherfurt.FitnessTrackerSystem.models.Exercise;
import de.fherfurt.FitnessTrackerSystem.repositories.IExerciseRepository;
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
public class ExerciseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private IExerciseRepository exerciseRepository;

    private TestHelper testHelper;

    @BeforeEach
    void setUp() {
        testHelper = new TestHelper(mockMvc, objectMapper);
    }

    @Test
    void testAddExerciseSuccess() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        Exercise exercise = new Exercise(0, "Bench Press", List.of());
        String json = objectMapper.writeValueAsString(exercise);

        // Add Exercise
        mockMvc.perform(post("/api/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetExerciseWithoutToken() throws Exception {
        mockMvc.perform(get("/api/exercise"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetExerciseWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        Exercise exercise = new Exercise(0, "Bench Press", List.of());
        String json = objectMapper.writeValueAsString(exercise);

        // Add Exercise
        mockMvc.perform(post("/api/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token
        mockMvc.perform(get("/api/exercise")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllExercisesWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        Exercise exercise1 = new Exercise(0, "Bench Press", List.of());
        String json1 = objectMapper.writeValueAsString(exercise1);

        Exercise exercise2 = new Exercise(0, "Squat", List.of());
        String json2 = objectMapper.writeValueAsString(exercise2);

        // Add Exercise 1
        mockMvc.perform(post("/api/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Add Exercise 2
        mockMvc.perform(post("/api/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token
        mockMvc.perform(get("/api/exercise")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetExerciseByIdWithoutToken() throws Exception {
        mockMvc.perform(get("/api/exercise/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetExerciseByIdWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        Exercise exercise = new Exercise(0, "Bench Press", List.of());
        String json = objectMapper.writeValueAsString(exercise);

        // Add Exercise
        String addResponse = mockMvc.perform(post("/api/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        Exercise addedExercise = objectMapper.readValue(addResponse, Exercise.class);

        // change it to an int
        int id = addedExercise.getExerciseId();

        // Request with token
        mockMvc.perform(get("/api/exercise/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateExerciseWithoutToken() throws Exception {
        mockMvc.perform(put("/api/exercise"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdateExerciseWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        Exercise exercise = new Exercise(0, "Bench Press", List.of());
        String json = objectMapper.writeValueAsString(exercise);

        // Add Exercise
        String addResponse = mockMvc.perform(post("/api/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        Exercise addedExercise = objectMapper.readValue(addResponse, Exercise.class);

        // update the exercise name
        addedExercise.setExerciseName("Deadlift");

        // write the new addedExercise
        String updateJson = objectMapper.writeValueAsString(addedExercise);

        // Request with Token
        mockMvc.perform(put("/api/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteExerciseByIdWithoutToken() throws Exception {
        mockMvc.perform(delete("/api/exercise"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteExerciseByIdWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        Exercise exercise = new Exercise(0, "Bench Press", List.of());
        String json = objectMapper.writeValueAsString(exercise);

        // Add Exercise
        String addResponse = mockMvc.perform(post("/api/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        Exercise addedExercise = objectMapper.readValue(addResponse, Exercise.class);

        // change it to an int
        int id = addedExercise.getExerciseId();

        // Request with Token
        mockMvc.perform(delete("/api/exercise/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
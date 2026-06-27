package de.fherfurt.FitnessTrackerSystem.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fherfurt.FitnessTrackerSystem.FitnessTrackerApplication;
import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.repositories.IBodyMeasurementRepository;
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
public class BodyMeasurementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private IBodyMeasurementRepository bodyMeasurementRepository;

    private TestHelper testHelper;

    @BeforeEach
    void setUp() {
        testHelper = new TestHelper(mockMvc, objectMapper);
    }

    @Test
    void testAddBodyMeasurementSuccess() throws Exception{
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        BodyMeasurement bodyMeasurement = new BodyMeasurement(
                0,
                user,
                80,
                1.80f,
                22,
                LocalDate.of(2026, 01, 01)
        );
        String json = objectMapper.writeValueAsString(bodyMeasurement);

        // Add BodyMeasurement
        mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetBodyMeasurementWithoutToken() throws Exception {
        mockMvc.perform(get("/api/body_measurement"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetBodyMeasurementWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        BodyMeasurement bodyMeasurement = new BodyMeasurement(
                0,
                user,
                80,
                1.80f,
                22,
                LocalDate.of(2026, 01, 01)
        );
        String json = objectMapper.writeValueAsString(bodyMeasurement);

        // Add BodyMeasurement
        mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token
        mockMvc.perform(get("/api/body_measurement")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllBodyMeasurementWithToken() throws Exception{
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        BodyMeasurement bodyMeasurement1 = new BodyMeasurement(
                0,
                user,
                80,
                1.80f,
                22,
                LocalDate.of(2026, 01, 01)
        );
        String json1 = objectMapper.writeValueAsString(bodyMeasurement1);

        BodyMeasurement bodyMeasurement2 = new BodyMeasurement(
                0,
                user,
                78,
                1.80f,
                21,
                LocalDate.of(2026, 01, 14)
        );
        String json2 = objectMapper.writeValueAsString(bodyMeasurement2);

        //  Add bodyMeasurement1
        mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        //  Add bodyMeasurement2
        mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token
        mockMvc.perform(get("/api/body_measurement")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetBodyMeasurementByIdWithoutToken() throws Exception {
        mockMvc.perform(get("/api/body_measurement/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetBodyMeasurementByIdWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        BodyMeasurement bodyMeasurement = new BodyMeasurement(
                0,
                user,
                80,
                1.80f,
                22,
                LocalDate.of(2026, 01, 01)
        );
        String json = objectMapper.writeValueAsString(bodyMeasurement);

        //  Add ActivityType
        String addResponse = mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        BodyMeasurement addedBodyMeasurement = objectMapper.readValue(addResponse, BodyMeasurement.class);

        // change it to an int
        int id = addedBodyMeasurement.getBodyMeasurementId();

        // Request with token
        mockMvc.perform(get("/api/body_measurement/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateBodyMeasurementWithoutToken() throws Exception {
        mockMvc.perform(put("/api/body_measurement"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdateBodyMeasurementWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        BodyMeasurement bodyMeasurement = new BodyMeasurement(
                0,
                user,
                80,
                1.80f,
                22,
                LocalDate.of(2026, 01, 01)
        );
        String json = objectMapper.writeValueAsString(bodyMeasurement);


        //  Add ActivityType
        String addResponse = mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        BodyMeasurement addedBodyMeasurement = objectMapper.readValue(addResponse, BodyMeasurement.class);

        // update the ActivityType name
        addedBodyMeasurement.setWeightInKg(78);

        // write the new addedActivityType
        String updateJson = objectMapper.writeValueAsString(bodyMeasurement);

        // Request with Token
        mockMvc.perform(put("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteBodyMeasurementByIdWithoutToken() throws Exception {
        mockMvc.perform(delete("/api/body_measurement"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteBodyMeasurementByIdWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        BodyMeasurement bodyMeasurement = new BodyMeasurement(
                0,
                user,
                80,
                1.80f,
                22,
                LocalDate.of(2026, 01, 01)
        );
        String json = objectMapper.writeValueAsString(bodyMeasurement);

        //  Add ActivityType
        String addResponse = mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        BodyMeasurement addedBodyMeasurement = objectMapper.readValue(addResponse, BodyMeasurement.class);

        // change it to an int
        int id = addedBodyMeasurement.getBodyMeasurementId();

        // Request with Token
        mockMvc.perform(delete("/api/activity_type/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCalculateBmiSuccess() throws Exception{
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        BodyMeasurement bodyMeasurement = new BodyMeasurement(
                0,
                user,
                80,
                1.80f,
                22,
                LocalDate.of(2026, 01, 01)
        );
        String json = objectMapper.writeValueAsString(bodyMeasurement);

        //  Add ActivityType
        String addResponse = mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Request with token -> calculate bmi
        mockMvc.perform(get("/api/body_measurement/bmi")
                .param("weight", "80")
                .param("height", "1.80")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testGetLatestBodyMeasurementSuccess() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        BodyMeasurement bodyMeasurement1 = new BodyMeasurement(
                0,
                user,
                80,
                1.80f,
                22,
                LocalDate.of(2026, 01, 01)
        );
        String json1 = objectMapper.writeValueAsString(bodyMeasurement1);

        BodyMeasurement bodyMeasurement2 = new BodyMeasurement(
                0,
                user,
                78,
                1.80f,
                21,
                LocalDate.of(2026, 01, 14)
        );
        String json2 = objectMapper.writeValueAsString(bodyMeasurement2);

        //  Add bodyMeasurement1
        mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        //  Add bodyMeasurement2
        mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token -> latest bm
        mockMvc.perform(get("/api/body_measurement/latest")
                .param("userId", String.valueOf(user.getUserId()))
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testGetLatestBodyMeasurementNotExists() throws Exception{
        // Arrange but no adding bm to the db
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        // Request with token -> latest bm
        mockMvc.perform(get("/api/body_measurement/latest")
                        .param("userId", String.valueOf(user.getUserId()))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetBodyMeasurementHistorySuccess() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        BodyMeasurement bodyMeasurement1 = new BodyMeasurement(
                0,
                user,
                80,
                1.80f,
                22,
                LocalDate.of(2026, 01, 01)
        );
        String json1 = objectMapper.writeValueAsString(bodyMeasurement1);

        BodyMeasurement bodyMeasurement2 = new BodyMeasurement(
                0,
                user,
                78,
                1.80f,
                21,
                LocalDate.of(2026, 01, 14)
        );
        String json2 = objectMapper.writeValueAsString(bodyMeasurement2);

        //  Add bodyMeasurement1
        mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        //  Add bodyMeasurement2
        mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token -> bm history
        mockMvc.perform(get("/api/body_measurement/history")
                        .param("userId", String.valueOf(user.getUserId()))
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-01-14")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testGetWeightProgressSuccess() throws Exception{
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        BodyMeasurement bodyMeasurement1 = new BodyMeasurement(
                0,
                user,
                80,
                1.80f,
                22,
                LocalDate.of(2026, 01, 01)
        );
        String json1 = objectMapper.writeValueAsString(bodyMeasurement1);

        BodyMeasurement bodyMeasurement2 = new BodyMeasurement(
                0,
                user,
                78,
                1.80f,
                21,
                LocalDate.of(2026, 01, 14)
        );
        String json2 = objectMapper.writeValueAsString(bodyMeasurement2);

        //  Add bodyMeasurement1
        mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        //  Add bodyMeasurement2
        mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token -> bm history
        mockMvc.perform(get("/api/body_measurement/weight-progress")
                        .param("userId", String.valueOf(user.getUserId()))
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-01-14")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBodyFatPercentageProgress() throws Exception {
        // Arrange
        String token = testHelper.getToken();
        User user = testHelper.getRegisteredUser();

        BodyMeasurement bodyMeasurement1 = new BodyMeasurement(
                0,
                user,
                80,
                1.80f,
                22,
                LocalDate.of(2026, 01, 01)
        );
        String json1 = objectMapper.writeValueAsString(bodyMeasurement1);

        BodyMeasurement bodyMeasurement2 = new BodyMeasurement(
                0,
                user,
                78,
                1.80f,
                21,
                LocalDate.of(2026, 01, 14)
        );
        String json2 = objectMapper.writeValueAsString(bodyMeasurement2);

        //  Add bodyMeasurement1
        mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        //  Add bodyMeasurement2
        mockMvc.perform(post("/api/body_measurement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token -> bm history
        mockMvc.perform(get("/api/body_measurement/fat-progress")
                        .param("userId", String.valueOf(user.getUserId()))
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-01-14")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}

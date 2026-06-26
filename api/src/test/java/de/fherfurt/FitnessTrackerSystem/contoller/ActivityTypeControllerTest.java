package de.fherfurt.FitnessTrackerSystem.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fherfurt.FitnessTrackerSystem.FitnessTrackerApplication;
import de.fherfurt.FitnessTrackerSystem.models.ActivityType;
import de.fherfurt.FitnessTrackerSystem.repositories.IActivityTypeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FitnessTrackerApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ActivityTypeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private IActivityTypeRepository activityTypeRepository;

    private TestHelper testHelper;

    @BeforeEach
    void setUp() {
        testHelper = new TestHelper(mockMvc, objectMapper);
    }

    @Test
    void testAddActivityTypeSuccess() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        ActivityType activityType = new ActivityType(0, "Kraftsport");
        String json = objectMapper.writeValueAsString(activityType);

        // Act + Assert
        mockMvc.perform(post("/api/activity_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }


    @Test
    void testGetActivityTypeWithoutToken() throws Exception {
        mockMvc.perform(get(("/api/activity_type")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetActivitytypeWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        ActivityType activityType = new ActivityType(0, "Kraftsport");
        String json = objectMapper.writeValueAsString(activityType);

        //  Add ActivityType
        mockMvc.perform(post("/api/activity_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token
        mockMvc.perform(get("/api/activity_type")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllActivityTypesWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        ActivityType activityType1 = new ActivityType(0, "Kraftsport");
        String json1 = objectMapper.writeValueAsString(activityType1);

        ActivityType activityType2 = new ActivityType(0, "Boxen");
        String json2 = objectMapper.writeValueAsString(activityType2);

        //  Add ActivityType 1
        mockMvc.perform(post("/api/activity_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        //  Add ActivityType 2
        mockMvc.perform(post("/api/activity_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());

        // Request with token
        mockMvc.perform(get("/api/activity_type")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetActivityTypeByIdWithoutToken() throws Exception {
        mockMvc.perform(get("/api/activity_type/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetActivityTypeByIdWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        ActivityType activityType = new ActivityType(0, "Kraftsport");
        String json = objectMapper.writeValueAsString(activityType);

        //  Add ActivityType
        String addResponse = mockMvc.perform(post("/api/activity_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        // read the addResponse
        ActivityType addedActivityType = objectMapper.readValue(addResponse, ActivityType.class);

        // change it to an int
        int id = addedActivityType.getActivityTypeId();

        // Request with token
        mockMvc.perform(get("/api/activity_type/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateActivityTypeWithoutToken() throws Exception {
        mockMvc.perform(put("/api/activity_type"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdateActivityTypeWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        ActivityType activityType = new ActivityType(0, "Kraftsport");
        String json = objectMapper.writeValueAsString(activityType);

        //  Add ActivityType
        String addResponse = mockMvc.perform(post("/api/activity_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        ActivityType addedActivityType = objectMapper.readValue(addResponse, ActivityType.class);

        // update the ActivityType name
        addedActivityType.setName("Laufen");

        // write the new addedActivityType
        String updateJson = objectMapper.writeValueAsString(activityType);

        // Request with Token
        mockMvc.perform(put("/api/activity_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteActivityTypeByIdWithoutToken() throws Exception {
        mockMvc.perform(delete("/api/activity_type"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteActivityTypeByIdWithToken() throws Exception {
        // Arrange
        String token = testHelper.getToken();

        ActivityType activityType = new ActivityType(0, "Kraftsport");
        String json = objectMapper.writeValueAsString(activityType);

        //  Add ActivityType
        String addResponse = mockMvc.perform(post("/api/activity_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the addResponse
        ActivityType addedActivityType = objectMapper.readValue(addResponse, ActivityType.class);

        // change it to an int
        int id = addedActivityType.getActivityTypeId();

        // Request with Token
        mockMvc.perform(delete("/api/activity_type/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}

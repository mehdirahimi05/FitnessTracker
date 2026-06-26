package de.fherfurt.FitnessTrackerSystem.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.models.UserRole;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestHelper {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    public TestHelper (MockMvc mockMvc, ObjectMapper objectMapper){
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    // Help methode only for the ControllerTest classes
    public String getToken() throws Exception {
        // Arrange
        User user = new User(0, "mehdi", "pass123", UserRole.USER, null);
        String json = objectMapper.writeValueAsString(user);

        // SignUp
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        // Login → get Token
        String token = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()            // Return the Mvc Object
                .getResponse()          // get the HTTP Request
                .getContentAsString();  // read the Respond Body as a String

        return token;
    }
}

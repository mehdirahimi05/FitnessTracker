package de.fherfurt.FitnessTrackerSystem.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.models.UserRole;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TestHelper {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private User registeredUser;  // saved User after register

    public TestHelper(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    // signup User and get token —
    public String getToken() throws Exception {
        User user = new User(0, "mehdi", "pass123", UserRole.USER, null);
        String json = objectMapper.writeValueAsString(user);

        // Register → User mit ID speichern
        String registerResponse = mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn().getResponse().getContentAsString();

        this.registeredUser = objectMapper.readValue(registerResponse, User.class);

        // Login → Token holen
        return mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn().getResponse().getContentAsString();
    }

    // returns the registeredUser after the signup
    public User getRegisteredUser() {
        return registeredUser;
    }
}

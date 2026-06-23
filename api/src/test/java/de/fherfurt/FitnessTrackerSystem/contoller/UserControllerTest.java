package de.fherfurt.FitnessTrackerSystem.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fherfurt.FitnessTrackerSystem.FitnessTrackerApplication;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.models.UserRole;
import de.fherfurt.FitnessTrackerSystem.repositories.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FitnessTrackerApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private IUserRepository userRepository;


    @Test
    void testSignUpUserSuccess() throws Exception {
        // Arrange
        User user = new User(0, "mehdi", "pass123", UserRole.USER, null);
        String json = objectMapper.writeValueAsString(user);

        // Act + Assert
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("mehdi"));
    }

    @Test
    void testSignUpUserAlreadyExists() throws Exception {
        // Arrange
        User user = new User(0, "mehdi", "pass123", UserRole.USER, null);
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        // Act + Assert
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLogInUserSuccess() throws Exception {
        // Arrange
        User user = new User(0, "mehdi", "pass123", UserRole.USER, null);
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        // Act + Assert
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testLogInUserWrongPassword() throws Exception {
        // Arrange
        User user = new User(0, "mehdi", "pass123", UserRole.USER, null);
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        // Act + Assert
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"mehdi\",\"passWord\":\"wrongPassword\"}"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void testLogInUserWrongUserName() throws Exception {
        // Arrange
        User user = new User(0, "mehdi", "pass123", UserRole.USER, null);
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        // Act + Assert
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"ammar\",\"passWord\":\"pass123\"}"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void testGetUserWithoutToken() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetUserWithToken() throws Exception {
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

        // Request with Token
        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

    @Test
    void testGetAllUsersWithToken() throws Exception {
        // Arrange
        User user1 = new User(0, "mehdi", "pass123", UserRole.USER, null);
        String json1 = objectMapper.writeValueAsString(user1);

        User user2 = new User(0, "ammar", "pass456", UserRole.USER, null);
        String json2 = objectMapper.writeValueAsString(user2);

        // SignUp
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2))
                .andExpect(status().isOk());

        // Login → get Token
        String token = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1))
                .andReturn()            // Return the Mvc Object
                .getResponse()          // get the HTTP Request
                .getContentAsString();  // read the Respond Body as a String

        // Request with Token
        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetUserByIdWithoutToken() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetUserByIdWithToken() throws Exception {
        // Arrange
        User user = new User(0, "mehdi", "pass123", UserRole.USER, null);
        String json = objectMapper.writeValueAsString(user);

        // SignUp -> get id
        String registerResponse = mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the registerResponse
        User registeredUser = objectMapper.readValue(registerResponse, User.class);

        // change it to an int
        int id = registeredUser.getUserId();

        // Login → get Token
        String token = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()            // Return the Mvc Object
                .getResponse()          // get the HTTP Request
                .getContentAsString();  // read the Respond Body as a String

        // Request with Token
        mockMvc.perform(get("/api/users/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateUserWithoutToken() throws Exception {
        mockMvc.perform(put("/api/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdateUserWithToken() throws Exception {
        // Arrange
        User user = new User(0, "mehdi", "pass123", UserRole.USER, null);
        String json = objectMapper.writeValueAsString(user);

        // SignUp -> get id
        String registerResponse = mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the registerResponse
        User registeredUser = objectMapper.readValue(registerResponse, User.class);

        // update for example the username
        registeredUser.setUserName("mehdiRahimi");

        // write the new registeredUser
        String updateJson = objectMapper.writeValueAsString(registeredUser);

        // Login → get Token
        String token = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()            // Return the Mvc Object
                .getResponse()          // get the HTTP Request
                .getContentAsString();  // read the Respond Body as a String

        // Request with Token
        mockMvc.perform(put("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUserByIdWithoutToken() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteUserByIdWithToken() throws Exception {
        // Arrange
        User user = new User(0, "mehdi", "pass123", UserRole.USER, null);
        String json = objectMapper.writeValueAsString(user);

        // SignUp -> get id
        String registerResponse = mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // read the registerResponse
        User registeredUser = objectMapper.readValue(registerResponse, User.class);

        // change it to an int
        int id = registeredUser.getUserId();

        // Login → get Token
        String token = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()            // Return the Mvc Object
                .getResponse()          // get the HTTP Request
                .getContentAsString();  // read the Respond Body as a String

        // Request with Token
        mockMvc.perform(delete("/api/users/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}

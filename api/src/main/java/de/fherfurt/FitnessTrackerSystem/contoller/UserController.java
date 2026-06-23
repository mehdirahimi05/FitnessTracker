package de.fherfurt.FitnessTrackerSystem.contoller;

import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.security.JwtService;
import de.fherfurt.FitnessTrackerSystem.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());  // 200 -> ok
    }

    @PostMapping("/register")
    public ResponseEntity<User> signUpUser(@RequestBody User user) {
        return userService.signUpUser(user)
                .map(ResponseEntity::ok)  // 200 -> ok
                .orElse(ResponseEntity.badRequest().build());  // 400 -> badRequest
    }

    @PostMapping("/login")
    public ResponseEntity<String> logInUser(@RequestBody User user) {
        return userService.logIn(user.getUserName(), user.getPassWord())
                .map(user1 -> ResponseEntity.ok(jwtService.generateToken(user1.getUserName())))
                .orElse(ResponseEntity.status(401).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)  // 200 -> ok
                .orElse(ResponseEntity.notFound().build());  // 404 -> notFound
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") int id) {
        userService.deleteUserByUserId(id);
        return ResponseEntity.noContent().build();
    }
}
package de.fherfurt.FitnessTrackerSystem.services.contoller;

import de.fherfurt.FitnessTrackerSystem.models.TrainingsSession;
import de.fherfurt.FitnessTrackerSystem.models.TrainingsSessionSummary;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.services.TrainingsSessionService;
import de.fherfurt.FitnessTrackerSystem.services.UserService;
import de.fherfurt.FitnessTrackerSystem.services.utils.TrainingsSessionFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/trainings_session")
public class TrainingsSessionController {
    private final TrainingsSessionService trainingsSessionService;
    private final UserService userService;

    public TrainingsSessionController(TrainingsSessionService trainingsSessionService, UserService userService) {
        this.trainingsSessionService = trainingsSessionService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<TrainingsSession>> getAllTrainingsSession() {
        return ResponseEntity.ok(trainingsSessionService.getAllTrainingsSessions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingsSession> getTrainingsSessionById(@PathVariable("id") int id) {
        return trainingsSessionService.getTrainingsSessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> addTrainingsSession(@RequestBody TrainingsSession trainingsSession) {
        trainingsSessionService.addTrainingsSession(trainingsSession);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateTrainingsSession(@RequestBody TrainingsSession trainingsSession) {
        trainingsSessionService.updateTrainingsSession(trainingsSession);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainingsSessionById(@PathVariable("id") int id) {
        trainingsSessionService.deleteTrainingsSessionById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/most-active")
    public ResponseEntity<User> getMostActiveUserByAmountOfTrainingsSessions(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ResponseEntity.ok(
                trainingsSessionService.getMostActiveUserByAmountOfTrainingsSessions(startDate, endDate)
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TrainingsSession>> filterTrainingsSession(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer minCalories,
            @RequestParam(required = false) Integer maxCalories) {

        User user = userId != null ? userService.getUserById(userId).orElse(null) : null;

        TrainingsSessionFilter filter = TrainingsSessionFilter.builder()
                .withStartDate(startDate)
                .withEndDate(endDate)
                .withUser(user)
                .withMinBurnedCalories(minCalories != null ? minCalories : 0)
                .withMaxBurnedCalories(maxCalories != null ? maxCalories : Integer.MAX_VALUE)
                .build();

        return ResponseEntity.ok(trainingsSessionService.filterTrainingsSession(filter));
    }

    @GetMapping("/streak")
    public ResponseEntity<Integer> getTrainingSessionStreak(@RequestParam int userId) {
        User user = userService.getUserById(userId).orElse(null);
        return ResponseEntity.ok(trainingsSessionService.getTrainingSessionStreak(user));
    }

    @GetMapping("/summary")
    public ResponseEntity<TrainingsSessionSummary> getDailyTrainingsSessionSummary(
            @RequestParam int userId,
            @RequestParam LocalDate date) {
        User user = userService.getUserById(userId).orElse(null);
        return ResponseEntity.ok(trainingsSessionService.getDailyTrainingsSessionSummary(user, date));
    }
}

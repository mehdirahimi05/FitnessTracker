package de.fherfurt.FitnessTrackerSystem.services.contoller;

import de.fherfurt.FitnessTrackerSystem.models.BodyMeasurement;
import de.fherfurt.FitnessTrackerSystem.models.User;
import de.fherfurt.FitnessTrackerSystem.services.BodyMeasurementService;
import de.fherfurt.FitnessTrackerSystem.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/body_measurement")
public class BodyMeasurementController {
    private final BodyMeasurementService bodyMeasurementService;
    private final UserService userService;

    public BodyMeasurementController(BodyMeasurementService bodyMeasurementService, UserService userService) {
        this.bodyMeasurementService = bodyMeasurementService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<BodyMeasurement>> getAllBodyMeasurement() {
        return ResponseEntity.ok(bodyMeasurementService.getAllBodyMeasurement());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BodyMeasurement> getBodyMeasurementById(@PathVariable("id") int id) {
        return bodyMeasurementService.getBodyMeasurementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> addBodyMeasurement(@RequestBody BodyMeasurement bodyMeasurement) {
        bodyMeasurementService.addBodyMeasurement(bodyMeasurement);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateBodyMeasurement(@RequestBody BodyMeasurement bodyMeasurement) {
        bodyMeasurementService.updateBodyMeasurement(bodyMeasurement);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBodyMeasurement(@PathVariable("id") int id) {
        bodyMeasurementService.deleteBodyMeasurement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bmi")
    public ResponseEntity<Float> calculateBmi(
            @RequestParam float weight,
            @RequestParam float height) {
        return ResponseEntity.ok(bodyMeasurementService.calculateBmi(weight, height));
    }

    @GetMapping("/latest")
    public ResponseEntity<BodyMeasurement> getLatestBodyMeasurement(@RequestParam int userId) {
        User user = userService.getUserById(userId).orElse(null);
        return bodyMeasurementService.getLatestBodyMeasurement(user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/history")
    public ResponseEntity<List<BodyMeasurement>> getBodyMeasurementHistory(
            @RequestParam int userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        User user = userService.getUserById(userId).orElse(null);
        return ResponseEntity.ok(bodyMeasurementService.getBodyMeasurementHistory(user, startDate, endDate));
    }

    @GetMapping("/weight-progress")
    public ResponseEntity<List<Float>> getWeightProgress(
            @RequestParam int userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        User user = userService.getUserById(userId).orElse(null);
        return ResponseEntity.ok(bodyMeasurementService.getWeightProgress(user, startDate, endDate));
    }

    @GetMapping("/fat-progress")
    public ResponseEntity<List<Integer>> getBodyFatPercentageProgress(
            @RequestParam int userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        User user = userService.getUserById(userId).orElse(null);
        return ResponseEntity.ok(bodyMeasurementService.getBodyFatPercentageProgress(user, startDate, endDate));
    }

}
